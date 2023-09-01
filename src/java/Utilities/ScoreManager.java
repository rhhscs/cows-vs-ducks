package src.java.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class manages scores.
 */
public class ScoreManager implements Iterable<Score> {
    private ScoreFile scoreFile;
    private ArrayList<Score> scores;
    private String filePath;

    /**
     * Creates a new Score Manager object.
     * @param filePath The file to save/load to.
     */
    public ScoreManager(String filePath) {
        this.scoreFile = new CsvScoreFile(); // can be changed to other format later
        this.scores = new ArrayList<Score>();
        this.filePath = filePath;
    }

    /**
     * Saves the scores to the file.
     */
    public void save() {
        try {
            this.scoreFile.save(this);
        } catch(IOException ex) {
            System.err.println("Error: could not save score file " + this.getFilePath());
        } 
    }

    /**
     * Loads the scores from the file.
     */
    public void load() {
        try {
            this.scoreFile.load(this);
        } catch(IOException ex) {
            System.err.println("Error: could not save score file " + this.getFilePath());
        } 
    }

    /**
     * This gets the score file's file path.
     * @return The file path.
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Adds a new score.
     * @param name The name of the scorer.
     * @param score The score.
     */
    public void add(String name, int score) {
        Score newScore = new Score(name, score);

        for (int i = 0; i < this.scores.size(); i++) {
            if (this.scores.get(i).getScore() < score) {
                this.scores.add(i, newScore);
                return;
            }
        }

        this.scores.add(newScore);
    }

    /**
     * This empties the score list.
     */
    public void clear() {
        this.scores.clear();
    }

    /**
     * Get the top scores.
     * @param numScores The number of scores to get.
     * @return An arraylist of scores, capped at the number of scores in the file.
     */
    public ArrayList<Score> getTopScores(int numScores) {
        this.scores.sort(Score.SCORE_COMPARATOR);
        int toIndex = Math.min(numScores, this.scores.size());
        return (ArrayList<Score>) this.scores.subList(0, toIndex);
    }

    @Override
    public Iterator<Score> iterator() {
        return this.scores.iterator();
    }
}
