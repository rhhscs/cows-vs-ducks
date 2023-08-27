package src.java.Utilities;

import java.awt.Toolkit;

public class ResolutionManager {
    public static ResolutionManager res = new ResolutionManager();

    private ResolutionManager(){}

    private final int LARGESCREEN_WIDTH = 1680;
    private final int LARGESCREEN_HEIGHT = 1050;
    private final float LARGESCREEN_SCALE = 1.0f;

    private final int MEDIUMSCREEN_WIDTH = 1280;
    private final int MEDIUMSCREEN_HEIGHT = 800;
    private final float MEDIUMSCREEN_SCALE = 1280.0f/1680.0f;

    private final int SMALLSCREEN_WIDTH = 800;
    private final int SMALLSCREEN_HEIGHT = 500;
    private final float SMALLSCREEN_SCALE = 800.0f/1680.0f;

    private final int FULLSCREEN_WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
    private final int FULLSCREEN_HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    private final float FULLSCREEN_SCALE = Math.min((float)FULLSCREEN_WIDTH/SMALLSCREEN_WIDTH, (float)FULLSCREEN_HEIGHT/SMALLSCREEN_HEIGHT);

    // 1:1 ratio scale window dimensions
    public static final int WIDTH = 1680;
    public static final int HEIGHT = 1050;

    // current active window dimensions
    public int WINDOWWIDTH = 1280;
    public int WINDOWHEIGHT = 800;

    // current active window scale (proportional to WIDTH and HEIGHT)
    public float SCALE = MEDIUMSCREEN_SCALE;

    // screen offset
    public int OFFSET_X = 0;
    public int OFFSET_Y = 0;


    // scaling function for lazy people
    public int s(int val) {
        return (int) (val*SCALE);
    }

    public static ResolutionManager getGlobalResolutionManager() {
        return res;
    }

    
    public void SMALL_DIMS(){
        WINDOWWIDTH = SMALLSCREEN_WIDTH;
        WINDOWHEIGHT = SMALLSCREEN_HEIGHT;
        SCALE = SMALLSCREEN_SCALE;
        OFFSET_X = 0;
        OFFSET_Y = 0;
    }

    public void MED_DIMS(){
        WINDOWWIDTH = MEDIUMSCREEN_WIDTH;
        WINDOWHEIGHT = MEDIUMSCREEN_HEIGHT;
        SCALE = MEDIUMSCREEN_SCALE;
        OFFSET_X = 0;
        OFFSET_Y = 0;
    }

    public void LARGE_DIMS(){
        WINDOWWIDTH = LARGESCREEN_WIDTH;
        WINDOWHEIGHT = LARGESCREEN_HEIGHT;
        SCALE = LARGESCREEN_SCALE;
        OFFSET_X = 0;
        OFFSET_Y = 0;
    }

    public void FULL_DIMS(){
        WINDOWWIDTH = FULLSCREEN_WIDTH;
        WINDOWHEIGHT = FULLSCREEN_HEIGHT;
        SCALE = FULLSCREEN_SCALE;
        OFFSET_X = (int)(FULLSCREEN_WIDTH - (SMALLSCREEN_WIDTH*FULLSCREEN_SCALE));
        OFFSET_Y = (int)(FULLSCREEN_HEIGHT - (SMALLSCREEN_HEIGHT*FULLSCREEN_SCALE));
    }
}
