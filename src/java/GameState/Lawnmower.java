package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.Drawable;

public class Lawnmower extends Entity implements Drawable {
    private Sprite sprite;
    private Projectile projectile;
    private boolean triggered;
    private int laneIndex;
    private static double scale = 1.5;

    public Lawnmower(int laneIndex) {
        super(PlayingField.X - (int) (scale * 64), PlayingField.Y + PlayingField.Tile.SIZE * (laneIndex + 1) - (int) (scale * 93), (int) (scale * 64), (int) (scale * 93));
        this.laneIndex = laneIndex;
        this.triggered = false;
        this.sprite = Sprite.STACK_COW.clone();
        this.sprite.useCycle(CowSprite.IDLE_CYCLE + 1);
        this.projectile = new PiercingProjectile(this.getX(), this.getY(), PlayingField.Tile.SIZE, PlayingField.Tile.SIZE, 8, 10000, 1000, Sprite.CHEERIO);
    }

    @Override
    public void draw(Graphics g) {
        this.sprite.draw(g, getX(), getY(), getWidth(), getHeight());
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
