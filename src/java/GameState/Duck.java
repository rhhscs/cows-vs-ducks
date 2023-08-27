package src.java.GameState;

import src.java.Drawable;
import src.java.Updatable;

abstract public class Duck extends Entity implements Drawable, Updatable{

    private int state;
    private int health;

    public Duck(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    
}
