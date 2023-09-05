package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

/**
 * This class represents the projectiles
 * 
 * @see ProjectileManager
 */
public class Projectile extends Entity implements Drawable, Updatable {
    private int speed;
    private int damage;
    private int slowTime;
    private boolean singleTarget;
    private int slowEffect;
    private boolean active;
    private int duration;
    private Sprite sprite;

    public static final Projectile NULL = new Projectile(0, 0, 0, 0, 0, 0, 0, true, 0, false, 0, Sprite.NULL);
    
    /**
     * This creates a new Projectile object
     * 
     * @param x           The top-left x coordinate.
     * @param y           The top-left y coordinate.
     * @param width        The number of tiles across horizontally.
     * @param height       The number of tiles across vertically.
     * @param speed        The speed of the projectile
     * @param damage       The amount of damage
     * @param slowTime     The slow duration
     * @param singleTarget If the projectile is single target
     * @param slowEffect   The slow amount
     * @param active       If the projectile is active
     * @param duration     The duration of the projectile
     * @param sprite The sprite.
     */
    public Projectile(int x, int y, int width, int height, int speed, int damage, int slowTime, boolean singleTarget,
            int slowEffect, boolean active, int duration, Sprite sprite) {
        super(x, y, width, height);

        this.speed = speed;
        this.damage = damage;
        this.slowTime = slowTime;
        this.singleTarget = singleTarget;
        this.slowEffect = slowEffect;
        this.active = active;
        this.duration = duration;
        this.sprite = sprite.clone();
        this.sprite.useCycle("projectile");
    }

    @Override
    public void draw(Graphics g) {
        if (this.sprite == null || this.sprite instanceof NullSprite) {
            g.setColor(Color.MAGENTA);
            g.drawOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            this.sprite.draw(g, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void update() {
        this.sprite.update();
        this.setX(this.getX() + this.speed);
        this.duration--;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getSlowTime() {
        return this.slowTime;
    }

    public boolean getSingleTarget() {
        return this.singleTarget;
    }

    public int getSlowEffect() {
        return this.slowEffect;
    }

    public boolean getActive() {
        return this.active;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    protected void setDamage(int damage){
        this.damage = damage;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public Projectile clone() {
        return new Projectile(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.speed, this.damage,
                this.slowTime, this.singleTarget, this.slowEffect, this.active, this.duration, this.sprite);
    }

}
