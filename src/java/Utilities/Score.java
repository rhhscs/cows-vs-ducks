package src.java.Utilities;

import java.util.Comparator;

/**
 * This represents a record of a score.
 */
public class Score implements Comparable<Score> {
    private String name;
    private int score;

    /**
     * This creates a new Score object.
     * @param name
     * @param score
     */
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets the name of the scorer.
     * @return The scorer's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the score.
     * @return The score.
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(Score score) {
        return this.getScore() - score.getScore();
    }

    public static final Comparator<Score> SCORE_COMPARATOR = new Comparator<Score>() {
        @Override
        public int compare(Score score1, Score score2) {
            return score1.compareTo(score2);
        }
        
    };
}