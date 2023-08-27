package src.java.GameState;

import java.util.ArrayList;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class DuckManager implements Updatable, Drawable{

    private ArrayList<Duck> ducks;

    public DuckManager(){
        this.ducks = new ArrayList<>();
    }

    public ArrayList<Duck> getDucks(){
        return this.ducks;
    }

    @Override
    public void update(){
        for(Duck duck: this.ducks){
            duck.update();
        }
    }
    
    @Override
    public void draw(Graphics g){
        for(Duck duck: this.ducks){
            duck.draw(g);
        }
    }
}
