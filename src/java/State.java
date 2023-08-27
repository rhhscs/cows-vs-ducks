package src.java;

import java.awt.Graphics;

public abstract class State {

    public State nextState = null;
    public State appendState = null;
    public boolean popState = false;
    
    public abstract void start();

    public abstract void update();
    
    public abstract void draw(Graphics g);
    
    public abstract void stop();

}
