package src.java.GameState;

import java.util.ArrayList;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class DuckManager implements Updatable, Drawable {
    private boolean reachedEnd;
    private ArrayList<Lane> lanes;
    private Entity endZone;

    /**
     * This constructs a new duck manager object.
     */
    public DuckManager() {
        this.lanes = new ArrayList<Lane>();
        this.reachedEnd = false;

        this.endZone = new Entity(PlayingField.X - 60, PlayingField.Y, 30, PlayingField.HEIGHT);

        for (int i = 0; i < PlayingField.NUM_LANES; i++) {
            this.lanes.add(new Lane(i));
        }
    }

    /**
     * This returns a list of the lanes that an entity collides with.
     * 
     * @param entity The entity to check.
     * @return A non-null list guaranteed to contain only lanes.
     */
    public ArrayList<Entity> getCollidingLanes(Entity entity) {
        ArrayList<Entity> collidingLanes = new ArrayList<Entity>();
        for (Lane lane : this.lanes) {
            if (lane.collides(entity)) {
                collidingLanes.add(lane);
            }
        }
        return collidingLanes;
    }

    /**
     * This adds a duck to the end of a lane.
     * 
     * @param laneIndex The index of the lane to add to.
     * @param duck      The duck to add.
     */
    public void addDuck(int laneIndex, Duck duck) {
        this.lanes.get(laneIndex).add(duck);
        duck.setLaneIndex(laneIndex);
    }

    public int getNumLanes() {
        return this.lanes.size();
    }

    @Override
    public void update() {
        for (Lane lane : this.lanes) {
            lane.update();
        }

        for (Lane lane: this.lanes) {
            for (Duck duck: lane) {
                if (this.endZone.collides(duck)) {
                    this.reachedEnd = true;
                    break;
                }
            }
            if (this.reachedEnd) break;
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Lane lane : this.lanes) {
            lane.draw(g);
        }
    }

    public boolean isGameOver() {
        return this.reachedEnd;
    }
}
