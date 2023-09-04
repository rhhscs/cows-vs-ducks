package src.java.GameState.Cows;

import java.awt.Graphics;

import src.java.GameState.CowSprite;
import src.java.GameState.Projectile;

/**
 * A stackable cow is a cow that combines multiple cows into a single tile. It
 * stores all its possible cows ahead of time.
 */
public class StackableCow extends Cow {
    private Cow[] cows;
    private int curSize;

    /**
     * This creates a new stackable cow object.
     * 
     * @param isTargetable whether the cow can be hit by ducks.
     * @param cost         The cost of this cow.
     * @param sprite       The cow sprite.
     */
    public StackableCow(boolean isTargetable, int cost, CowSprite sprite) {
        super(100, 0, 0, 0, isTargetable, cost, sprite, Projectile.NULL, null);
        this.cows = new Cow[0];
        this.curSize = 0;
    }

    /**
     * This sets where and what cows appear when this cow gets stacked.
     * 
     * @param cows An array of the cows that will appear when stacked.
     */
    public void setCows(Cow[] cows) {
        this.cows = cows;
    }

    /**
     * This increases the cows stacked at this tile.
     */
    public void stackCow() {
        this.curSize++; // Math.max(cows.length, curSize + 1) ?
    }

    /**
     * This decreases the cows stacked at this tile.
     */
    public void removeCow() {
        this.curSize--; // Math.min(0, cuSize - 1) ?
    }

    @Override
    public void update() {
        for (int i = 0; i < this.curSize; i++) {
            this.cows[i].update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < this.curSize; i++) {
            this.cows[i].draw(g);
        }
    }

    public boolean isFull() {
        return this.getCurSize() >= this.getMaxSize();
    }

    @Override
    public void move(int dx, int dy) {
        for (Cow cow : this.cows) {
            cow.move(dx, dy);
        }

        super.move(dx, dy);
    }

    @Override
    public void setPos(int x, int y) {
        int dx = x - this.getX();
        int dy = y - this.getY();

        for (Cow cow : this.cows) {
            cow.move(dx, dy);
        }

        super.setPos(x, y);
    }

    @Override
    public void setX(int x) {
        int dx = x - this.getX();

        for (Cow cow : this.cows) {
            cow.move(dx, 0);
        }
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        int dy = y - this.getY();

        for (Cow cow : this.cows) {
            cow.move(0, dy);
        }
        super.setY(y);
    }

    public int getCurSize() {
        return this.curSize;
    }

    public void setCurSize(int size) {
        this.curSize = size;
    }

    public int getMaxSize() {
        return this.cows.length;
    }

    @Override
    public StackableCow clone() {
        StackableCow cow = new StackableCow(this.isTargetable(), this.getCost(), this.getSprite());

        // Copy the peas.
        Cow[] newPeas = new Cow[this.cows.length];
        for (int i = 0; i < newPeas.length; i++) {
            newPeas[i] = this.cows[i].clone();
        }
        cow.setCows(newPeas);

        // Stack the same amount.
        cow.setCurSize(this.getCurSize());

        return cow;
    }

    @Override
    public String toString() {
        return "StackableCow: " + this.getCurSize() + " stacked";
    }
}
