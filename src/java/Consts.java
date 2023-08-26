package src.java;

import java.awt.Toolkit;

public class Consts {

    private final static int LARGESCREEN_WIDTH = 1680;
    private final static int LARGESCREEN_HEIGHT = 1050;
    private final static float LARGESCREEN_SCALE = 2.1f;

    private final static int MEDIUMSCREEN_WIDTH = 1280;
    private final static int MEDIUMSCREEN_HEIGHT = 800;
    private final static float MEDIUMSCREEN_SCALE = 1.6f;

    private final static int SMALLSCREEN_WIDTH = 800;
    private final static int SMALLSCREEN_HEIGHT = 500;
    private final static float SMALLSCREEN_SCALE = 1.0f;

    private final static int FULLSCREEN_WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
    private final static int FULLSCREEN_HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    private final static float FULLSCREEN_SCALE = Math.min((float)FULLSCREEN_WIDTH/SMALLSCREEN_WIDTH, (float)FULLSCREEN_HEIGHT/SMALLSCREEN_HEIGHT);

    public static int WINDOWWIDTH = 1280;
    public static int WINDOWHEIGHT = 800;
    public static float WINDOWSCALE = 1.6f;

    public static void SMALL_DIMS(){
        WINDOWWIDTH = SMALLSCREEN_WIDTH;
        WINDOWHEIGHT = SMALLSCREEN_HEIGHT;
        WINDOWSCALE = SMALLSCREEN_SCALE;
    }

    public static void MED_DIMS(){
        WINDOWWIDTH = MEDIUMSCREEN_WIDTH;
        WINDOWHEIGHT = MEDIUMSCREEN_HEIGHT;
        WINDOWSCALE = MEDIUMSCREEN_SCALE;
    }

    public static void LARGE_DIMS(){
        WINDOWWIDTH = LARGESCREEN_WIDTH;
        WINDOWHEIGHT = LARGESCREEN_HEIGHT;
        WINDOWSCALE = LARGESCREEN_SCALE;
    }

    public static void FULL_DIMS(){
        
    }
}
