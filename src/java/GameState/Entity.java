package src.java.GameState;

import java.awt.Rectangle;

/**
 * This represents the parent class for all game objects. It is a rectangle with a position and dimension.
 */
public class Entity {
    private Rectangle rect;

    /**
     * This creates a new entity object.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Entity(int x, int y, int width, int height) {
        this.rect = new Rectangle(x, y, width, height);
    }

    /**
     * This moves this entity relative to its current position.
     * @param dx The shift in x.
     * @param dy The shift in y.
     */
    public void move(int dx, int dy) {
        this.rect.setLocation(this.getX() + dx, this.getY() + dy);
    }

    /**
     * This sets the entity's current position.
     * @param x The new x position.
     * @param y The new y position.
     */
    public void setPos(int x, int y) {
        this.rect.setLocation(x, y);
    }

    /**
     * This checks if this entity collides with another entity.
     * @param entity
     * @return True if the entities collide, false otherwise.
     */
    public boolean collides(Entity entity) {
        return this.rect.intersects(entity.getRect());
    }

    public Rectangle getRect() {
        return this.rect;
    }

    public int getX() {
        return (int) this.rect.getX();
    }

    public void setX(int x) {
        this.rect.setLocation(x, this.getY());
    }

    public int getY() {
        return (int) this.rect.getY();
    }

    public void setY(int y) {
        this.rect.setLocation(this.getY(), y);
    }

    public int getWidth() {
        return (int) this.rect.getWidth();
    }

    public void setWidth(int width) {
        this.rect.setSize(width, this.getHeight());
    }

    public int getHeight() {
        return (int) this.rect.getHeight();
    }

    public void setHeight(int height) {
        this.rect.setSize(this.getWidth(), height);
    }
}
