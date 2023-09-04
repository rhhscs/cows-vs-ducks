package src.java;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;

public final class Consts {
    public static final long FPS = 50;
    public static final long FRAME_DURATION = 1000 / FPS;

    // Fonts
    private static final String MONDAY_FEELINGS_FONT_FILE = "src/saves/TheOctopus-VPry.ttf";

    // Score screen constants
    public static final int SCORES_X = 1000;
    public static final int SCORES_Y = 100;
    public static final int SCORES_WIDTH = 600;
    public static final int SCORES_HEIGHT = 800;
    public static final Color SCORES_COLOR = new Color(127, 127, 127);
    public static final Font TITLE_FONT = loadFont(MONDAY_FEELINGS_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 50);
    public static final Font SCORES_FONT = loadFont(MONDAY_FEELINGS_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 30);
    

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

    private static Font loadFont(String fontFileName, int fontType, int fontStyle, int fontSize){
        Font errorFont = new Font("Bree Serif", fontStyle, fontSize);
        Font font;
        try{
            Font actualFont = Font.createFont(fontType, new File(fontFileName));
            font = actualFont.deriveFont((float)fontSize);
        } catch (IOException ex) {
            System.out.println("Font Files could not be read");
            font = errorFont;
        } catch (FontFormatException ex){
            System.out.println("Font Files are invalid");  
            font = errorFont;
        }
        return font;
    }

    private Consts() {}

}
