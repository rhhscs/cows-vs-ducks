package src.java.GameState;

import java.awt.Point;

import src.java.Drawable;
import src.java.Updatable;

abstract public class Cow extends Entity implements Drawable, Updatable {
    private int health;
    
    public Cow(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

}
