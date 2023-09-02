package src.java;

public final class Consts {
    public static final long FPS = 50;
    public static final long FRAME_DURATION = 1000 / FPS;
    
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
