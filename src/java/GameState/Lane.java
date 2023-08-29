package src.java.GameState;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class Lane extends Entity implements Updatable, Drawable, Iterable<Duck> {
    private ArrayList<Duck> ducks;
    private Duck farthestDuck;

    /**
     * This constructs a new lane object.
     * 
     * @param x      The top-left x coordinate.
     * @param y      The top-left y coordinate.
     * @param width  The lane width.
     * @param height The lane height.
     */
    public Lane(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.ducks = new ArrayList<Duck>();
        this.farthestDuck = null;
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