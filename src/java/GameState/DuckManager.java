package src.java.GameState;

import java.util.ArrayList;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class DuckManager implements Updatable, Drawable {
    private ArrayList<Lane> lanes;

    /**
     * This constructs a new duck manager object.
     */
    public DuckManager() {
        this.lanes = new ArrayList<Lane>();

        for (int i = 0; i < PlayingField.NUM_LANES; i++) {
            this.lanes.add(new Lane(PlayingField.X, PlayingField.Y + i * PlayingField.Tile.SIZE,
                    PlayingField.Tile.SIZE * PlayingField.NUM_COLUMNS,
                    PlayingField.Tile.SIZE));
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
    }

    public int getNumLanes() {
        return this.lanes.size();
    }

    @Override
    public void update() {
        for (Lane lane : this.lanes) {
            lane.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Lane lane : this.lanes) {
            lane.draw(g);
        }
    }
}
