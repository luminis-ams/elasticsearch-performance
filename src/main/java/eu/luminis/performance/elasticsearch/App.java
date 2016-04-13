package eu.luminis.performance.elasticsearch;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class App {

    private static final Random random = new Random(24);

    private DataGenerator dataGenerator = new DataGenerator();

    public static void main(String[] args) throws IOException, ScriptException {
        Random random = new Random(10);

        System.out.println(random.nextInt() + "\n" + random.nextInt() + "\n" + random.nextInt());

        File f = new File(App.class.getResource("/noun.animal").getPath());
        List<WordNetFileReader.Row> rows = WordNetFileReader.getRows(f, 10);
        System.out.println(rows);
    }
}
