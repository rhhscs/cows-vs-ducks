package src.java.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class manages scores. It also maintains the current score.
 */
public class ScoreManager implements Iterable<Score> {
    private ScoreFile scoreFile;
    private ArrayList<Score> scores;
    private String filePath;

    private Score curScore;

    public static final ScoreManager scoreManager = new ScoreManager("src/saves/scores.txt");

    /**
     * Creates a new Score Manager object.
     * @param filePath The file to save/load to.
     */
    public ScoreManager(String filePath) {
        this.scoreFile = new CsvScoreFile(); // can be changed to other format later
        this.scores = new ArrayList<Score>();
        this.filePath = filePath;
        this.curScore = new Score("Player", 0, 0, 0);
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
     * This emtpies the score manager.
     */
    public void reset() {
        this.scores.clear();
        this.curScore = new Score("Player", 0, 0, 0);
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
     * @param points The score.
     * @param level The level reached.
     * @param wave The wave reached.
     */
    public void add(String name, int points, int level, int wave) {
        Score newScore = new Score(name, points, level, wave);

        this.add(newScore);
    }

    /**
     * Adds a new score.
     * @param score
     */
    public void add(Score score) {
        for (int i = 0; i < this.scores.size(); i++) {
            if (this.scores.get(i).getPoints() < score.getPoints()) {
                this.scores.add(i, score);
                return;
            }
        }

        this.scores.add(score);
    }

    /**
     * Adds the current score to the file.
     */
    public void addCurScore() {
        this.add(this.curScore);
    }

    public void setCurName(String name) {
        this.curScore.setName(name);
    }

    public String getCurName() {
        return this.curScore.getName();
    }

    public void addCurPoints(int points) {
        this.curScore.addPoints(points);
    }

    public int getCurPoints() {
        return this.curScore.getPoints();
    }

    public int getCurLevel() {
        return this.curScore.getLevel();
    }

    public void setCurLevel(int level) {
        this.curScore.setLevel(level);
    }

    public int getCurWave() {
        return this.curScore.getWave();
    }

    public void setCurWave(int wave) {
        this.curScore.setWave(wave);
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
    public List<Score> getTopScores(int numScores) {
        this.scores.sort(Score.SCORE_COMPARATOR);
        int toIndex = Math.min(numScores, this.scores.size());
        return this.scores.subList(0, toIndex);
    }

    @Override
    public Iterator<Score> iterator() {
        return this.scores.iterator();
    }
}
