package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.Consts;
import src.java.Drawable;
import src.java.Updatable;
import src.java.GameState.Cows.Cow;
import src.java.Utilities.Stat;

public class Duck extends Entity implements Drawable, Updatable {
    public enum State {
        WALK,
        IDLE,
        ATTACK,
    }

    public static final int WIDTH = PlayingField.Tile.SIZE;
    public static final int HEIGHT = PlayingField.Tile.SIZE;
    public static final int X = PlayingField.X + PlayingField.WIDTH;

    public static final Duck NULL_DUCK = new Duck(0, 0, 0, 0, 0, null, 0, null, Sprite.BASIC_DUCK, 0);
    public static final Duck BASIC_DUCK = new Duck(1, 10, 10, 50, 100, null, 0, AI.MELEE_DUCK_AI, Sprite.BASIC_DUCK, Consts.BASIC_DUCK);
    public static final Duck RIVER_DUCK = new Duck(1, 10, 10, 60, 200, null, 0, AI.MELEE_DUCK_AI, Sprite.RIVER_DUCK, Consts.RIVER_DUCK);
    public static final Duck DUCK_WITH_KNIFE = new Duck(1, 20, 10, 40, 150, null, 0, AI.MELEE_DUCK_AI,
            Sprite.KNIFE_DUCK, Consts.KNIFE_DUCK);
    public static final Duck DUCK_WITH_BREAD = new Duck(1, 10, 10, 60, 300, null, 0, AI.MELEE_DUCK_AI,
            Sprite.BREAD_DUCK, Consts.BREAD_DUCK);
    public static final Duck RUBBER_DUCK = new RubberDuck(null, 0, AI.RUBBER_DUCK_AI);
    public static final Duck GARGANTUAR_DUCK = new GargantuanDuck(null, 0, AI.MELEE_DUCK_AI);

    protected Stat moveSpeed;
    protected Stat damage;
    protected Stat attackSpeed;

    // important: runs backwards compared to cow's attack timer
    // this helps make attack speed modifiers work
    private int attackTimer;
    private int attackDuration;

    private DuckSprite sprite;

    private int health;
    private State state;
    private int wasHit;

    private PlayingField lawn;
    private int laneIndex;
    protected Cow target;
    private AI ai;

    private int points;

    /**
     * This creates a new duck object.
     * 
     * @param moveSpeed      The move speed of this duck.
     * @param damage         The damage per attack.
     * @param attackDuration The number of frames the attack lasts for.
     * @param attackSpeed    The number of frames between attacks.
     * @param health         The health points.
     * @param sprite         The sprite of this duck.
     * @param lawn           The lawn that this duck should interact with.
     * @param laneIndex      The index of the lane this duck is in.
     * @param sprite         The duck sprite.
     * @param points         The amount of points given on defeat
     */
    public Duck(int moveSpeed, int damage, int attackDuration, int attackSpeed, int health,
            PlayingField lawn, int laneIndex, AI ai, DuckSprite sprite, int points) {
        super(X, PlayingField.Y + PlayingField.Tile.SIZE * laneIndex, WIDTH, HEIGHT);
        this.moveSpeed = new Stat(moveSpeed);
        this.damage = new Stat(damage);
        this.attackSpeed = new Stat(attackSpeed);

        this.attackDuration = attackDuration;
        this.attackTimer = attackSpeed + attackDuration;

        this.health = health;

        this.sprite = sprite.clone();
        this.sprite.useWalkCycle();

        this.setState(State.WALK);
        this.lawn = lawn;
        this.laneIndex = laneIndex;
        this.ai = ai;
        this.target = null;

        this.points = points;
    }

    public static void init(PlayingField lawn) {
        BASIC_DUCK.setPlayingField(lawn);
        DUCK_WITH_BREAD.setPlayingField(lawn);
        RIVER_DUCK.setPlayingField(lawn);
        DUCK_WITH_KNIFE.setPlayingField(lawn);
        GARGANTUAR_DUCK.setPlayingField(lawn);
        RUBBER_DUCK.setPlayingField(lawn);
    }

    Color color = new Color(80, 80, 80, 180);
    Color color2 = new Color(255, 10, 10, 180);

    @Override
    public void draw(Graphics g) {
        if (this.sprite == null) {
            if (this.state == State.WALK || this.state == State.IDLE) {
                g.setColor(color);
            } else if (this.state == State.ATTACK) {
                g.setColor(color2);
            }

            g.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            this.sprite.draw(g, this.getX() - 50, this.getY(), this.getWidth(), this.getHeight(), this.wasHit > 0);
            if (this.wasHit > 0) {
                this.wasHit--;
            }
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
            this.updateTarget();

            if (this.target != null) {
                // Start attack animation.
                this.onAttackStart();
            } else {
                // No target, start walking since attack animation ended.
                this.setState(State.WALK);
            }
        }

        if (this.attackTimer == 0) {
            this.onAttackEnd();
            this.attackTimer = this.attackSpeed.getValue() + this.attackDuration;
        }
    }

    public void updateTarget() {
        // Update the target if the target dies.
        if (this.target != null && !this.target.isAlive()) {
            this.target = null;
        }

        // Find a new target if necessary.
        if (this.target == null) {
            this.target = (Cow) this.ai.findTarget(this.lawn.getCowsInLane(this.laneIndex), this);
        }
    }

    public void onAttackStart() {
        this.setState(State.ATTACK);
        this.attack();
    }

    public void onAttackEnd() {
        if (this.target == null || !this.target.isAlive()) {
            this.setState(State.WALK);
        } else {
            this.setState(State.IDLE);
        }
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setLaneIndex(int index) {
        this.laneIndex = index;
    }

    public void setPlayingField(PlayingField playingField) {
        this.lawn = playingField;
    }

    public int getDamage() {
        return this.damage.getValue();
    }

    public State getState() {
        return state;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public PlayingField getPlayingField() {
        return lawn;
    }

    public AI getAI() {
        return ai;
    }

    public int getLane() {
        return laneIndex;
    }

    public int getPoints() {
        return this.points;
    }

    public void setState(State newState) {
        if (newState == this.state)
            return;

        if (newState == State.ATTACK) {
            this.sprite.useAttackCycle();
        } else if (newState == State.IDLE) {
            this.sprite.useIdleCycle();
        } else {
            this.sprite.useWalkCycle();
        }
        this.state = newState;
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
        this.wasHit = 15;
    }

    /**
     * This changes the move speed of this duck.
     * 
     * @param value    The amount to decrease the move speed by.
     * @param duration The duration to apply the effect.
     */
    public void applyMoveSpeedEffect(int value, int duration) {
        this.moveSpeed.addModifier(-value, duration);
    }

    /**
     * This changes the attack speed of this duck.
     * 
     * @param value    The amount to change the attack speed by.
     * @param duration The duration to apply the effect.
     */
    public void applyAttackSpeedEffect(int value, int duration) {
        this.attackSpeed.addModifier(value, duration);
    }

    /**
     * This changes the attack damage of this duck.
     * 
     * @param value    The amount to change the attack damage by.
     * @param duration The duration to apply the effect.
     */
    public void applyDamageEffect(int value, int duration) {
        this.damage.addModifier(value, duration);
    }

    @Override
    protected Duck clone() {
        return new Duck(this.moveSpeed.getBaseValue(), this.damage.getBaseValue(),
                this.attackDuration, this.attackSpeed.getBaseValue(), this.getHealth(), this.lawn, this.laneIndex,
                this.ai, this.sprite);
    }
}
