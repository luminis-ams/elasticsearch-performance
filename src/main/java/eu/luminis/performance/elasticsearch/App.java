package eu.luminis.performance.elasticsearch;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

public class App {

    private RandomProductGenerator randomProductGenerator = new RandomProductGenerator(10);

    public static void main(String[] args) throws IOException, ScriptException {

        new App().fillIndexes();
    }

    private App fillIndexes() throws IOException {
        randomProductGenerator.addDataSource(new File(this.getClass().getResource("/noun.food").getPath()), 10);
        randomProductGenerator.addDataSource(new File(this.getClass().getResource("/noun.animal").getPath()), 10);

        System.out.println(randomProductGenerator.getProduct());

        return this;
    }
}
