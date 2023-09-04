package src.java.GameState;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class Lane extends Entity implements Updatable, Drawable, Iterable<Duck> {
    private ArrayList<Duck> ducks;
    private Duck farthestDuck;
    private Lawnmower mower;

    /**
     * This constructs a new lane object.
     * 
     * @param laneIndex The index of the lane.
     */
    public Lane(int laneIndex) {
        super(PlayingField.X, PlayingField.Y + PlayingField.Tile.SIZE * laneIndex, PlayingField.WIDTH, PlayingField.Tile.SIZE);
        this.ducks = new ArrayList<Duck>();
        this.farthestDuck = null;
        this.mower = new Lawnmower(laneIndex);
    }

    /**
     * This adds a duck to this lane.
     * 
     * @param duck The duck to add.
     */
    public void add(Duck duck) {
        duck.setPos(this.getX() + this.getWidth(), this.getY());
        this.ducks.add(duck);

        this.updateFarthestDuck(duck);
    }

    /**
     * This returns the rightmost duck in this lane.
     * 
     * @return The rightmost duck if there is a duck in this lane, null otherwise.
     */
    public Duck getFarthestDuck() {
        return this.farthestDuck;
    }

    @Override
    public void draw(Graphics g) {
        if (!this.mower.isTriggered()) 
            this.mower.draw(g);
        
        for (Duck duck : this.ducks) {
            duck.draw(g);
        }
    }

    @Override
    public void update() {
        // Update ducks.
        for (Duck duck : this.ducks) {
            duck.update();
        }

        // Remove dead ducks.
        Iterator<Duck> iterator = this.iterator();
        while (iterator.hasNext()) {
            Duck duck = iterator.next();

            if (!duck.isAlive()) {
                iterator.remove();
            } else {
                this.updateFarthestDuck(duck);
            }
        }

        // Check if they triggered the lawnmower
        for (Duck duck: this.ducks) {
            if (this.mower.collides(duck)) {
                Projectile projectile = this.mower.trigger();
                ProjectileManager.projectileManager.addProjectile(projectile);
            }
        }

        if (this.isEmpty()) {
            this.farthestDuck = null;
        }    
    }    

    @Override
    public Iterator<Duck> iterator() {
        return this.ducks.iterator();
    }

    public boolean isEmpty() {
        return this.ducks.size() == 0;
    }
    
    public ArrayList<Duck> getDucks() {
        return this.ducks;
    }

    /**
     * This updates the farthest duck if the given duck is farther right. 
     * 
     * @param duck The duck to check.
     */
    private void updateFarthestDuck(Duck duck) {
        if (this.farthestDuck == null || duck.getX() > this.farthestDuck.getX()) {
            this.farthestDuck = duck;
        }
    }
}