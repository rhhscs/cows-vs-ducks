package src.java.GameState;

import java.util.ArrayList;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class DuckManager implements Updatable, Drawable{

    private ArrayList<Lane> lanes;

    public DuckManager(){
        this.lanes = new ArrayList<>();
    }

    public ArrayList<Lane> getCollidingLanes(Entity entity){
        ArrayList<Lane> ret = new ArrayList<>();
        for(Lane nextLane: this.lanes){
            if(nextLane.collides(entity)){
                ret.add(nextLane);
            }
        }
        return ret;
    }

    public void addDuck(int lane, Duck duck){
        this.lanes.get(lane).getDucks().add(duck);
    }

    @Override
    public void update(){
        for(Lane nextLane: this.lanes){
            nextLane.update();
        }
    }
    
    @Override
    public void draw(Graphics g){
        for(Lane nextLane: this.lanes){
            nextLane.draw(g);
        }
    }

}
