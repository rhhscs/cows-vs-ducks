package src.java.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class saves and loads scores with a CSV format.
 */
public class CsvScoreFile implements ScoreFile {
    @Override
    public void load(ScoreManager manager) throws IOException {
        File scoreFile = new File(manager.getFilePath());
        Scanner reader = new Scanner(scoreFile);

        reader.nextLine();

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(",");
            String name = parts[0];
            int score = Integer.parseInt(parts[1]);
            
            manager.add(name, score);
        }

        reader.close();
    }

    @Override
    public void save(ScoreManager manager) throws IOException {
        File scoreFile = new File(manager.getFilePath());
        FileWriter writer = new FileWriter(scoreFile, false);

        writer.write("Name,Score\n");
        for (Score score: manager) {
            writer.write(score.getName() + "," + score.getScore() + "\n");
        }

        writer.close();
    }
}