package src.java.GameState;

import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class Duck extends Entity implements Drawable, Updatable{

    private int speed;
    private int health;
    private int damage;
    private String sprite;
    private boolean walk;
    private boolean idle;
    private boolean attack;
    private boolean die;
    private PlayingField playingField;

    public Duck(int x, int y, int width, int height, PlayingField playingField, int speed, int health, int damage, String sprite, boolean walk, boolean idle, boolean attack, boolean die){
        super(x, y, width, height);
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.sprite = sprite;
        this.walk = walk;
        this.idle = idle;
        this.attack = attack;
        this.die = die;
        this.playingField = playingField;
    }

    @Override
    public void draw(Graphics g){
        g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void update(){
        
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getDamage(){
        return this.damage;
    }
    
}
