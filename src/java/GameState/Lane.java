package src.java.GameState;

import java.util.ArrayList;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class Lane extends Entity implements Updatable, Drawable{

    private ArrayList<Duck> ducks;

    public Lane(int x, int y, int width, int height){
        super(x, y, width, height);
        this.ducks = new ArrayList<>();
    }

    public ArrayList<Duck> getDucks(){
        return this.ducks;
    }

    @Override
    public void draw(Graphics g){
        for(Duck nextDuck: this.ducks){
            nextDuck.draw(g);
        }
    }

    @Override
    public void update(){
        for(Duck nextDuck: this.ducks){
            nextDuck.update();
        }
    }

}