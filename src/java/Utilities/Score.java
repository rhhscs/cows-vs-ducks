package src.java.Utilities;

import java.util.Comparator;

/**
 * This represents a record of a score.
 */
public class Score implements Comparable<Score> {
    private String name;
    private int points;
    private int level;
    private int wave;
    
    /**
     * This creates a new Score object.
     * @param name The scorer's name.
     * @param points The score.
     * @param level The level reached.
     * @param wave The wave reached.
     */
    public Score(String name, int points, int level, int wave) {
        this.name = name;
        this.points = points;
        this.level = level;
        this.wave = wave;
    }

    /**
     * Gets the name of the scorer.
     * @return The scorer's name.
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the score.
     * @return The score.
     */
    public int getPoints() {
        return this.points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void setPoints(int score) {
        this.points = score;
    }

    @Override
    public int compareTo(Score score) {
        return this.getPoints() - score.getPoints();
    }

    /**
     * This sorts in descending order.
     */
    public static final Comparator<Score> SCORE_COMPARATOR = new Comparator<Score>() {
        @Override
        public int compare(Score score1, Score score2) {
            return score2.compareTo(score1);
        }
        
    };

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }
}
