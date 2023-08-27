package src.java.GameState.Cows;

import src.java.Drawable;
import src.java.Updatable;
import src.java.GameState.DuckManager;
import src.java.GameState.Entity;
import src.java.GameState.ProjectileManager;
import src.java.GameState.Projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Cow extends Entity implements Drawable, Updatable {
    private int attackSpeed;
    private int timeUntilFirstAttack;
    private int timeUntilNextAttack;
    private int attackDuration; // needed for playing attack animation?
    private int health;
    private boolean isTargetable; // for spike weed and cherry bomb

    private BufferedImage sprite;
    private String spriteFilePath;

    private State state;
    private Projectile projectile;
    private DuckManager duckManager;

    public Cow(int x, int y, int width, int height, int health, int attackSpeed, int timeUntilFirstAttack,
            int attackDuration, boolean isTargetable, String spriteFilePath, Projectile projectile) {
        super(x, y, width, height);

        this.attackSpeed = attackSpeed;
        this.timeUntilFirstAttack = timeUntilFirstAttack;
        this.timeUntilNextAttack = timeUntilFirstAttack;
        this.attackDuration = attackDuration;

        this.health = health;
        this.isTargetable = isTargetable;
        this.sprite = null;
        this.state = State.IDLE;
        this.projectile = projectile;
    }

    public void setDuckManager(DuckManager duckManager) {
        this.duckManager = duckManager;
    }

    public void setState(State newState) {
        if (newState != this.state) {
            if (newState == State.ATTACK) {
                // idk change to attack sprite
            } else {
                // idk change to idle sprite
            }
        }
        this.state = newState;
    }

    public void attack() {
        ProjectileManager.projectileManager.addProjectile(this.projectile);
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getTimeUntilFirstAttack() {
        return timeUntilFirstAttack;
    }

    public int getTimeUntilNextAttack() {
        return timeUntilNextAttack;
    }

    public int getAttackDuration() {
        return attackDuration;
    }

    public int getHealth() {
        return health;
    }

    public boolean isTargetable() {
        return isTargetable;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public String getSpriteFilePath() {
        return spriteFilePath;
    }

    public State getState() {
        return state;
    }

    @Override
    public void draw(Graphics g) {
        if (sprite == null) {
            if (this.state == State.ATTACK) {
                g.setColor(new Color(150, 100, 100));
            } else {
                g.setColor(new Color(50, 50, 50));
            }
            g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void update() {
        this.timeUntilNextAttack--;

        // currently, the total time between the start of each attack is attackSpeed +
        // attackDuration

        // Attack begins
        if (this.timeUntilNextAttack > this.attackSpeed - this.attackDuration) {
            this.setState(State.ATTACK);
        }

        // Attack ends
        if (this.timeUntilNextAttack == 0) {
            this.setState(State.IDLE);
            this.timeUntilNextAttack = this.attackSpeed + this.attackDuration; // or just attackSpeed if we dont want to
                                                                               // count attackDuration
            // ProjectileManager.projectileManager.addWindup(this.windup);
        }

    }

    @Override
    public Cow clone() throws CloneNotSupportedException {
        Cow cow = new Cow(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getHealth(),
                this.getAttackSpeed(), this.getTimeUntilNextAttack(), this.getAttackDuration(), this.isTargetable(),
                this.getSpriteFilePath(), (Projectile) this.projectile.clone());
        cow.setDuckManager(this.duckManager);
        return cow;
    }

    public enum State {
        IDLE,
        ATTACK
    }
}