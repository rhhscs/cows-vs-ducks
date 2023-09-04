package src.java;
import java.awt.Color;
import java.awt.Font;

public final class Consts {
    public static final long FPS = 50;
    public static final long FRAME_DURATION = 1000 / FPS;
    public static final int SCORES_X = 1000;
    public static final int SCORES_Y = 100;
    public static final int SCORES_WIDTH = 600;
    public static final int SCORES_HEIGHT = 800;
    public static final Color SCORES_COLOR = new Color(127, 127, 127);
    public static final Font TITLE_FONT = Font.decode(Font.SANS_SERIF).deriveFont(40, 50);
    public static final Font SCORES_FONT = Font.decode(Font.SANS_SERIF).deriveFont(40, 30);
    
    /**
     * This gets the time a number of frames appears.
     * @param frames The number of frames.
     * @return The duration this many frames lasts.
     */
    public static int framesToMs(int frames) {
        return (int) (frames * FRAME_DURATION);
    }

    /**
     * This gets the number of frames required to appear for a given timespan.
     * @param ms The number of milliseconds.
     * @return The number of frames required to appear for the given timespan.
     */
    public static int msToFrames(double ms) {
        return (int) (ms / FRAME_DURATION);
    }

    private Consts() {}

}
