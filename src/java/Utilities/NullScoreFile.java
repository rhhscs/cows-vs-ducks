package src.java.Utilities;

import java.io.IOException;

/**
 * This does not actually save the score file.
 */
public class NullScoreFile implements ScoreFile {
    @Override
    public void load(ScoreManager manager) throws IOException {
        return;
    }

    @Override
    public void save(ScoreManager manager) throws IOException {
        return;
    }
}
