package src.java.GameState;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

/**
 * This class represents the projectiles
 * 
 * @see ProjectileManager
 */
public class Projectile extends Entity implements Drawable, Updatable{

    private int speed;
    private int damage;
    private int stunTime;
    private int slowTime;
    private boolean singleTarget;
    private int slowEffect;
    private boolean active;
    private String filePath;

    /**
     * This creates a new Projectile object
     * 
     * @param x_           The top-left x coordinate.
     * @param y_           The top-left y coordinate.
     * @param width        The number of tiles across horizontally.
     * @param height       The number of tiles across vertically.
     * @param speed        The speed of the projectile
     * @param damage       The amount of damage
     * @param stunTime     The stun duration
     * @param slowTime     The slow duration
     * @param singleTarget If the projectile is single target
     * @param slowEffect   The slow percentage
     * @param active       If the projectile is active
     * @param filePath     File path to the sprite
     */
    public Projectile(int x, int y, int width, int height, int speed, int damage, int stunTime, int slowTime, boolean singleTarget, int slowEffect, boolean active, String filePath){
        super(x, y, width, height);

        this.speed = speed;
        this.damage = damage;
        this.stunTime = stunTime;
        this.slowTime = slowTime;
        this.singleTarget = singleTarget;
        this.slowEffect = slowEffect;
        this.active = active;
        this.filePath = filePath;
    }

    @Override
    public void draw(Graphics g){
        if(this.filePath == null){
            g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void update(){
        this.setX(this.getX() + this.speed);
    }

    public int getDamage(){
        return this.damage;
    }

    public int getStunTime(){
        return this.stunTime;
    }

    public int getSlowTime(){
        return this.slowTime;
    }

    public boolean getSingleTarget(){
        return this.singleTarget;
    }

    public int getSlowEffect(){
        return this.slowEffect;
    }

    public boolean getActive(){
        return this.active;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Projectile(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.speed, this.damage, this.stunTime,
            this.slowTime, this.singleTarget, this.slowEffect, this.active, this.filePath);
    }
    
}
