package src.java;

import java.awt.Graphics;

public abstract class State {
    
    public abstract void start();

    public abstract void update();
    
    public abstract void draw(Graphics g);
    
    public abstract void stop();
}
