package eu.luminis.performance.elasticsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class Elasticsearch {

    private static final Logger logger = LoggerFactory.getLogger(Elasticsearch.class);
    private static final int port = 9300;

    private final Client client;
    private final ObjectMapper objectMapper;

    private Elasticsearch(String clusterName) throws UnknownHostException {
        this.client = getClient(clusterName);
        this.objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static Elasticsearch instance(String clusterName) throws UnknownHostException {
        return new Elasticsearch(clusterName);
    }

    public Elasticsearch ifIndexExists(String indexName, Runnable runnable) {

        boolean indexExists = client
                .admin()
                .indices()
                .exists(new IndicesExistsRequest(indexName))
                .actionGet()
                .isExists();

        if (!indexExists) runnable.run();

        logger.info("Index {} exist {}", indexName, indexExists);

        return this;
    }

    public CreateIndexResponse createIndex(String indexName) {

        logger.info("Creating index {}", indexName);

        return client.admin().indices()
                .prepareCreate(indexName)
                .setSettings(Settings.settingsBuilder().put("number_of_replicas", 0).put("number_of_shards", 1).build())
                .get();
    }

    public DeleteIndexResponse deleteIndex(String indexName) throws ExecutionException, InterruptedException {
        logger.info("Deleting index {}", indexName);
        return client.admin().indices().delete(new DeleteIndexRequest(indexName)).get();
    }

    public <T> BulkResponse bulkIndex(String indexName, List<T> sources) {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

        sources
                .parallelStream()
                .map(src -> {
                    try {
                        return Optional.of(objectMapper.writeValueAsBytes(src));
                    } catch (JsonProcessingException e) {
                        return Optional.empty();
                    }
                })
                .filter(Optional::isPresent)
                .forEach(src -> bulkRequestBuilder.add(new IndexRequest(indexName, indexName).source((byte[]) src.get())));

        logger.debug("Executing bulk request with size {}", sources.size());

        return bulkRequestBuilder.get();
    }

    private Client getClient(String clusterName) throws UnknownHostException {
        final Settings settings = Settings
                .settingsBuilder()
                .put("cluster.name", clusterName).build();

        return TransportClient
                .builder()
                .settings(settings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), port));
    }
}
