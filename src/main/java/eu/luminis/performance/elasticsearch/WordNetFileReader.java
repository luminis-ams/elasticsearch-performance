package eu.luminis.performance.elasticsearch;

import org.apache.commons.io.FileUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WordNetFileReader {

    private static final ScriptEngine scriptEngine = new ScriptEngineManager()
            .getEngineByName("javascript");

    public static List<Row> getRows(File dataSet, int startingLine) throws IOException {
        List<String> lines = FileUtils.readLines(dataSet);

        return lines.subList(startingLine, lines.size())
                .stream()
                .map(WordNetFileReader::convertLineToRow)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<Row> convertLineToRow(String line) {

        try {

            return Optional.ofNullable((Map<Integer, String>) scriptEngine.eval(line
                    .replace("{ ", "[\'")
                    .replace("}", "\']")
                    .replace(",", "\',\'")))
                    .map(map -> {
                        String[] array = map.values().toArray(new String[map.values().size()]);
                        return new Row(array[0], array[array.length - 1]);
                    });

        } catch (ScriptException e) {
            return Optional.empty();
        }
    }

    public static class Row {

        private String word;
        private String sentence;

        public Row(String word, String sentence) {
            this.word = word;
            this.sentence = sentence;
        }

        public String getWord() {
            return word;
        }

        public String getSentence() {
            return sentence;
        }

        @Override
        public String toString() {
            return "Row{" +
                    "word='" + word + '\'' +
                    ", sentence='" + sentence + '\'' +
                    '}';
        }
    }
}
