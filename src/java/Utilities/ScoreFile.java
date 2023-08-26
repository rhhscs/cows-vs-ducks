package src.java.Utilities;

import java.io.IOException;

/**
 * This interface represents a way to save and load scores with a file.
 */
public interface ScoreFile {
    /**
     * Loads the score file.
     * @param manager The score manager.
     */
    public void load(ScoreManager manager) throws IOException;
    /**
     * Saves the score file.
     * @param manager The score manager.
     */
    public void save(ScoreManager manager) throws IOException;
}
