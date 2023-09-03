package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import src.java.Drawable;
import src.java.Updatable;
import src.java.GameState.Cows.Cow;
import src.java.Utilities.Stat;

public class Duck extends Entity implements Drawable, Updatable {
    public enum State {
        WALK,
        IDLE,
        ATTACK,
        DIE
    }

    public static final int WIDTH = PlayingField.Tile.SIZE;
    public static final int HEIGHT = PlayingField.Tile.SIZE;
    public static final int X = PlayingField.X + PlayingField.WIDTH;

    public static final Duck NULL_DUCK = new Duck(0, 0, 0, 0, 0, null, null, 0, null);

    private Stat moveSpeed;
    private Stat damage;
    private Stat attackSpeed;

    // important: runs backwards compared to cow's attack timer
    // this helps make attack speed modifiers work
    private int attackTimer;
    private int attackDuration;

    private int health;
    private String sprite;
    private State state;

    private PlayingField lawn;
    private int laneIndex;
    private Cow target;
    private AI ai;

    /**
     * This creates a new duck object.
     * 
     * @param moveSpeed      The move speed of this duck.
     * @param damage         The damage per attack.
     * @param attackDuration The number of frames the attack lasts for.
     * @param attackSpeed The number of frames between attacks.
     * @param health         The health points.
     * @param sprite         The sprite of this duck.
     * @param lawn           The lawn that this duck should interact with.
     * @param laneIndex      The index of the lane this duck is in.
     */
    public Duck(int moveSpeed, int damage, int attackDuration, int attackSpeed, int health,
            String sprite,
            PlayingField lawn, int laneIndex, AI ai) {
        super(X, PlayingField.Y + PlayingField.Tile.SIZE * laneIndex, WIDTH, HEIGHT);
        this.moveSpeed = new Stat(moveSpeed);
        this.damage = new Stat(damage);
        this.attackSpeed = new Stat(attackSpeed);
        
        this.attackDuration = attackDuration;
        this.attackTimer = attackSpeed + attackDuration;

        this.health = health;
        this.sprite = sprite;
        this.state = State.WALK;
        this.lawn = lawn;
        this.laneIndex = laneIndex;
        this.ai = ai;
        this.target = null;
    }

    @Override
    public void draw(Graphics g) {
        if (this.sprite == null) {
            if (this.state == State.WALK || this.state == State.IDLE) {
                g.setColor(Color.GRAY);
            } else if (this.state == State.ATTACK) {
                g.setColor(Color.RED);
            }

            g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void update() {
        this.moveSpeed.update();
        this.damage.update();
        this.attackSpeed.update();

        if (this.state == State.WALK) {
            this.move(-this.moveSpeed.getValue(), 0);
            this.attackTimer = this.attackDuration;
        } else if (this.state == State.ATTACK || this.attackTimer > this.attackDuration) {
            this.attackTimer--;
        }

        if (this.attackTimer == this.attackDuration) {
            // Update the target if the target dies.
            if (this.target != null && !this.target.isAlive()) {
                this.target = null;
            }
    
            // Find a new target if necessary.
            if (this.target == null) {
                this.target = (Cow) this.ai.findTarget(this.lawn.getCowsInLane(this.laneIndex), this);
            }

            if (this.target != null) {
                // Start attack animation.
                this.setState(State.ATTACK);
                this.attack();
            } else {
                // No target, start walking since attack animation ended.
                this.setState(State.WALK);
            }
        }

        if (this.attackTimer == 0) {
            // Attack animation ends
            if (this.target == null || !this.target.isAlive()) {
                this.setState(State.WALK);
            } else {
                this.setState(State.IDLE);
            }
            this.attackTimer = this.attackSpeed.getValue() + this.attackDuration;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void attack() {
        if (target != null) {
            target.takeDamage(this.getDamage());
        }
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
     * This changes the move speed of this duck.
     * 
     * @param value The amount to change the move speed by.
     * @param duration The duration to apply the effect.
     */
    public void applyMoveSpeedEffect(int value, int duration) {
        this.moveSpeed.addModifier(value, duration);
    }

    /**
     * This changes the attack speed of this duck.
     * 
     * @param value The amount to change the attack speed by.
     * @param duration The duration to apply the effect.
     */
    public void applyAttackSpeedEffect(int value, int duration) {
        this.attackSpeed.addModifier(value, duration);
    }

    /**
     * This changes the attack damage of this duck.
     * 
     * @param value The amount to change the attack damage by.
     * @param duration The duration to apply the effect.
     */
    public void applyDamageEffect(int value, int duration) {
        this.damage.addModifier(value, duration);
    }

    @Override
    protected Duck clone() {
        return new Duck(this.moveSpeed.getBaseValue(), this.damage.getBaseValue(),
                this.attackDuration, this.attackSpeed.getBaseValue(), this.getHealth(), this.sprite, this.lawn, this.laneIndex, this.ai);
    }
}
