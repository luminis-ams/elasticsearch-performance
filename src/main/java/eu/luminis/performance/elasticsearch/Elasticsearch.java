package eu.luminis.performance.elasticsearch;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Elasticsearch {

    private static final int port = 9300;

    private final Client client;

    private Elasticsearch(String clusterName) throws UnknownHostException {
        this.client = getClient(clusterName);
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

        if(indexExists) runnable.run();

        return this;
    }

    public Elasticsearch createIndex(String indexName) {

        client.admin().indices()
                .prepareCreate(indexName)
                .get();

        return this;
    }

    private Client getClient(String clusterName) throws UnknownHostException {
        final Settings settings = Settings
                .settingsBuilder()
                .put("cluster.name", clusterName).build();

        return TransportClient
                .builder()
                .settings(settings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost(), port));
    }
}
