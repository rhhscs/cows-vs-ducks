package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;
import src.java.Utilities.Stat;

public class Duck extends Entity implements Drawable, Updatable {
    public enum State {
        WALK,
        IDLE,
        ATTACK,
        DIE
    }

    private Stat<Integer> moveSpeed;
    private Stat<Integer> damage;
    private Stat<Integer> attackSpeed;

    private int health;
    private String sprite;
    private State state;
    private PlayingField lawn;
    private int laneIndex;

    /**
     * This creates a new duck object.
     * @param x The top-left x coordinate.
     * @param y The top-left y coordinate.
     * @param width The width of this duck.
     * @param height The height of this duck.
     * @param moveSpeed The move speed of this duck.
     * @param damage The damage per attack.
     * @param attackSpeed The number of frames between attacks.
     * @param health The health points.
     * @param sprite The sprite of this duck.
     * @param lawn The lawn that this duck should interact with.
     * @param laneIndex The index of the lane this duck is in.
     */
    public Duck(int x, int y, int width, int height, int moveSpeed, int damage, int attackSpeed, int health, String sprite,
            PlayingField lawn, int laneIndex) {
        super(x, y, width, height);
        this.moveSpeed = new Stat<Integer>(moveSpeed);
        this.damage = new Stat<Integer>(damage);
        this.attackSpeed = new Stat<Integer>(attackSpeed);

        this.health = health;
        this.sprite = sprite;
        this.state = State.WALK;
        this.lawn = lawn;
        this.laneIndex = laneIndex;
    }

    @Override
    public void draw(Graphics g) {
        if (this.state == State.WALK || this.state == State.IDLE) {
            g.setColor(Color.GRAY);
        } else if (this.state == State.ATTACK) {
            g.setColor(Color.RED);
        }
        
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void update() {
        this.moveSpeed.update();
        this.damage.update();
        this.attackSpeed.update();

        if (this.state == State.WALK) {
            this.move(-this.moveSpeed.getValue(), 0);
        } else if (this.state == State.ATTACK) {

        } else {

        }
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return this.damage.getValue();
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

    /**
     * This applies a move speed multiplier to this duck.
     * @param multiplier The multiplier on move speed.
     * @param duration The duration to apply the effect.
     */
    public void applyMoveSpeedMultiplier(double multiplier, int duration) {
        int modifiedValue = (int) (this.moveSpeed.getDefaultValue() * multiplier);
        this.moveSpeed.applyModifier(modifiedValue, duration);
    }

    /**
     * This changes the move speed of this duck.
     * @param quantity The amount to change the move speed by.
     * @param duration The duration to apply the effect.
     */
    public void applyMoveSpeedEffect(int quantity, int duration) {
        int modifiedValue = this.moveSpeed.getDefaultValue() + quantity;
        this.moveSpeed.applyModifier(modifiedValue, duration);
    }

    /**
     * This applies an attack speed multiplier to this duck.
     * @param multiplier The multiplier on the time between duck attacks.
     * @param duration The duration to apply the effect.
     */
    public void applyAttackSpeedMultiplier(double multiplier, int duration) {
        int modifiedValue = (int) (this.attackSpeed.getDefaultValue() * multiplier);
        this.attackSpeed.applyModifier(modifiedValue, duration);
    }

        /**
     * This changes the attack speed of this duck.
     * @param quantity The amount to change the attack speed by.
     * @param duration The duration to apply the effect.
     */
    public void applyAttackSpeedEffect(int quantity, int duration) {
        int modifiedValue = this.attackSpeed.getDefaultValue() + quantity;
        this.attackSpeed.applyModifier(modifiedValue, duration);
    }

    /**
     * This applies a damage multiplier to this duck.
     * @param multiplier The multiplier on theis duck's attack damage.
     * @param duration The duration to apply the effect.
     */
    public void applyDamageMultiplier(double multiplier, int duration) {
        int modifiedValue = (int) (this.damage.getDefaultValue() * multiplier);
        this.damage.applyModifier(modifiedValue, duration);
    }

        /**
     * This changes the attack damage of this duck.
     * @param quantity The amount to change the attack damage by.
     * @param duration The duration to apply the effect.
     */
    public void applyDamageEffect(int quantity, int duration) {
        int modifiedValue = this.damage.getDefaultValue() + quantity;
        this.damage.applyModifier(modifiedValue, duration);
    }
}
