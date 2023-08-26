package src.java.Utilities;

import java.awt.Toolkit;

public class ResolutionManager {
    private final int LARGESCREEN_WIDTH = 1680;
    private final int LARGESCREEN_HEIGHT = 1050;
    private final float LARGESCREEN_SCALE = 2.1f;

    private final int MEDIUMSCREEN_WIDTH = 1280;
    private final int MEDIUMSCREEN_HEIGHT = 800;
    private final float MEDIUMSCREEN_SCALE = 1.6f;

    private final int SMALLSCREEN_WIDTH = 800;
    private final int SMALLSCREEN_HEIGHT = 500;
    private final float SMALLSCREEN_SCALE = 1.0f;

    private final int FULLSCREEN_WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
    private final int FULLSCREEN_HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    private final float FULLSCREEN_SCALE = Math.min((float)FULLSCREEN_WIDTH/SMALLSCREEN_WIDTH, (float)FULLSCREEN_HEIGHT/SMALLSCREEN_HEIGHT);

    public int WINDOWWIDTH = 1280;
    public int WINDOWHEIGHT = 800;
    public float WINDOWSCALE = 1.6f;
    public int OFFSET_X = 0;
    public int OFFSET_Y = 0;

    public void SMALL_DIMS(){
        WINDOWWIDTH = SMALLSCREEN_WIDTH;
        WINDOWHEIGHT = SMALLSCREEN_HEIGHT;
        WINDOWSCALE = SMALLSCREEN_SCALE;
    }

    public void MED_DIMS(){
        WINDOWWIDTH = MEDIUMSCREEN_WIDTH;
        WINDOWHEIGHT = MEDIUMSCREEN_HEIGHT;
        WINDOWSCALE = MEDIUMSCREEN_SCALE;
    }

    public void LARGE_DIMS(){
        WINDOWWIDTH = LARGESCREEN_WIDTH;
        WINDOWHEIGHT = LARGESCREEN_HEIGHT;
        WINDOWSCALE = LARGESCREEN_SCALE;
    }

    public void FULL_DIMS(){
        WINDOWWIDTH = FULLSCREEN_WIDTH;
        WINDOWHEIGHT = FULLSCREEN_HEIGHT;
        WINDOWSCALE = FULLSCREEN_SCALE;
        OFFSET_X = (int)(FULLSCREEN_WIDTH - (SMALLSCREEN_WIDTH*FULLSCREEN_SCALE));
        OFFSET_Y = (int)(FULLSCREEN_HEIGHT - (SMALLSCREEN_HEIGHT*FULLSCREEN_SCALE));
    }
}
