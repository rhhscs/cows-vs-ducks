package src.java;

public abstract class State implements Drawable, Updatable {

    public State nextState = null;
    public State appendState = null;
    public State resetStates = null;
    public boolean popState = false;
    
    public abstract void start();
    
    public abstract void stop();

    public abstract void pause();
}
