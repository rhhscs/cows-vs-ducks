package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.Drawable;

public class Lawnmower extends Entity implements Drawable {
    private Sprite sprite;
    private Projectile projectile;
    private boolean triggered;
    private int laneIndex;

    public Lawnmower(int laneIndex) {
        super(PlayingField.X - 30, PlayingField.Y + PlayingField.Tile.SIZE * laneIndex, 30, PlayingField.Tile.SIZE);
        this.laneIndex = laneIndex;
        this.triggered = false;
        this.sprite = Sprite.NULL;
        this.projectile = new PiercingProjectile(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, 10000, 1000, Sprite.NULL);
    }

    @Override
    public void draw(Graphics g) {
        this.sprite.draw(g, getX(), getY(), getWidth(), getHeight());
        
        g.setColor(Color.MAGENTA);
        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }

    public Projectile trigger() {
        if (!this.triggered) {
            this.triggered = true;
            return this.projectile;
        }

        return Projectile.NULL;
    }

    public boolean isTriggered() {
        return this.triggered;
    }

    public int getLaneIndex() {
        return this.laneIndex;
    }

    @Override
    public Lawnmower clone() {
        return new Lawnmower(this.getLaneIndex());
    }    
}
