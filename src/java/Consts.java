package src.java;
import java.awt.Color;
import java.awt.Font;

public final class Consts {
    public static final long FPS = 50;
    public static final long FRAME_DURATION = 1000 / FPS;

    // Score screen constants
    public static final int SCORES_X = 1000;
    public static final int SCORES_Y = 100;
    public static final int SCORES_WIDTH = 600;
    public static final int SCORES_HEIGHT = 800;
    public static final Color SCORES_COLOR = new Color(127, 127, 127);
    public static final Font TITLE_FONT = Font.decode(Font.SANS_SERIF).deriveFont(40, 50);
    public static final Font SCORES_FONT = Font.decode(Font.SANS_SERIF).deriveFont(40, 30);
    

    // Point value constants
    public static final int HUNDRED_CHEERIOS_COLLECTED = 10;
    public static final int LAWNMOWER_TRIGGERED = -100;
    
    public static final int BASIC_DUCK = 100;
    public static final int RIVER_DUCK = 125;
    public static final int KNIFE_DUCK = 125;
    public static final int RUBBER_DUCK = 125;
    public static final int BREAD_DUCK = 150;
    public static final int MOTHER_DUCK = 125;
    public static final int DUCKLING_DUCK = 75;
    public static final int BUFF_DUCK = 300;

    public static final int[] WAVE = {
        100, 200, 300, 400, 500, 600, 1000, 1000, 1000, 1000
    };
    public static final int LEVEL = 1000;


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
