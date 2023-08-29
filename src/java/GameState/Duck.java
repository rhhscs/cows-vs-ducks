package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;

public class Duck extends Entity implements Drawable, Updatable {
    public enum State {
        WALK,
        IDLE,
        ATTACK,
        DIE
    }

    private int speed;
    private int health;
    private int damage;
    private String sprite;
    private State state;
    private PlayingField lawn;
    private int laneIndex;

    public Duck(int x, int y, int width, int height, int speed, int health, int damage, String sprite,
            PlayingField lawn, int laneIndex) {
        super(x, y, width, height);
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.sprite = sprite;
        this.state = State.WALK;
        this.lawn = lawn;
        this.laneIndex = laneIndex;
        // TODO handle slow effects
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void update() {
        this.move(-this.speed, 0);
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return this.damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * This deals damage to this duck.
     * 
     * @param damage Amount of damage to do.
     */
    public void takeDamage(int damage) {
        this.health -= damage;
    }
}
