package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.Utilities.ResolutionManager;

public class LaserProjectile extends Projectile {
    Sprite trail = Sprite.RAINBOW_TRAIL;
    private int cheerioSize;
    private int tickCounter = 9;
    boolean fullWidth = false;
    public LaserProjectile(int x, int y, int width, int height, int speed, int damage, Sprite sprite) {
        super(x, y, width, height, speed, damage, 0, false, 0, true, 100, sprite);
        cheerioSize = height;
    }
    @Override
    public void update() {
        getSprite().update();
        if (tickCounter < 7){
            this.setX(getX() + 1);
            if (this.getX() +  this.getWidth() < ResolutionManager.WIDTH){
                this.setWidth(getWidth() + this.getSpeed());
            } else {
                fullWidth = true;
            }
            if (getHeight() < 20 && fullWidth){
                setDuration(0);
            }
        } else {
            this.setWidth(getWidth() + 20);
        }
        if (tickCounter == 0) {
            this.setHeight(this.getHeight() - 3);
        } else {
            tickCounter--;
        }
    }

    @Override
    public LaserProjectile clone() {
        return new LaserProjectile(getX(), getY(), getWidth(), getHeight(), getSpeed(), getDamage(), getSprite());
    }

    @Override
    public void draw(Graphics g) {
        if (getSprite() == null || getSprite() instanceof NullSprite) {
            g.setColor(Color.MAGENTA);
            g.drawOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            if (tickCounter < 7)
                trail.draw(g, this.getX(), this.getY() + ((cheerioSize - getHeight())/ 2), this.getWidth() - (cheerioSize/2), this.getHeight() - 10);
            getSprite().draw(g, this.getX() +  this.getWidth() - cheerioSize, this.getY(), cheerioSize, cheerioSize);
        }
    }
}
