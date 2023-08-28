package src.java.GameState.Cows;

import src.java.Drawable;
import src.java.Updatable;
import src.java.GameState.AI;
import src.java.GameState.Duck;
import src.java.GameState.DuckManager;
import src.java.GameState.Entity;
import src.java.GameState.PlayingField;
import src.java.GameState.ProjectileManager;
import src.java.GameState.Projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Cow extends Entity implements Drawable, Updatable {
    public enum State {
        IDLE,
        ATTACK
    }

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
    private AI ai;

    public static final Cow CHEERIO_CATAPULT = new Cow(0, 0, PlayingField.Tile.SIZE, PlayingField.Tile.SIZE, 100, 100,
            10, 15, true, null,
            new Projectile(0, 20, 30, 30, 20, 10, 0, true, 0, true, 100000, null), AI.SHOOTER_COW_AI);

    /**
     * This creates a new cow object.
     * 
     * @param x                    The top-left x coordinate.
     * @param y                    They top-left y coordinate.
     * @param width                The width of this cow.
     * @param height               The height of this cow.
     * @param health               The health points.
     * @param attackSpeed          The attack speed.
     * @param timeUntilFirstAttack The time until the first attack.
     * @param attackDuration       The time between the end of an attack and the
     *                             start of a new one.
     * @param isTargetable         Whether this cow can be targeted by ducks.
     * @param spriteFilePath       The sprite sheet file path.
     * @param projectile           The projectile released when attacking.
     * @param ai                   The AI that controls when the cow has a target to
     *                             attack.
     */
    public Cow(int x, int y, int width, int height, int health, int attackSpeed, int timeUntilFirstAttack,
            int attackDuration, boolean isTargetable, String spriteFilePath, Projectile projectile, AI ai) {
        super(x, y, width, height);

        this.attackSpeed = attackSpeed;
        this.timeUntilFirstAttack = timeUntilFirstAttack;
        this.timeUntilNextAttack = timeUntilFirstAttack;
        this.attackDuration = attackDuration;

        this.health = health;
        this.isTargetable = isTargetable;
        this.sprite = null;
        this.spriteFilePath = spriteFilePath;
        this.state = State.IDLE;
        this.projectile = projectile;
        this.ai = ai;
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
        if (this.timeUntilNextAttack > 0) {
            this.timeUntilNextAttack--;
        }
        if (this.timeUntilNextAttack == 0 && this.ai.shouldAttack(new ArrayList<Entity>(), this)) {
            // Attack restarts
            this.timeUntilNextAttack = this.attackSpeed + this.attackDuration;
            // or just attackSpeed if we dont want to count attackDuration
        }

        // the time between the start of attacks is attackSpeed + attackDuration

        if (this.timeUntilNextAttack > this.attackSpeed) {
            // Attack animation begins
            this.setState(State.ATTACK);
        } else if (this.timeUntilNextAttack == this.attackSpeed) {
            // Attack animation ends, launch projectile
            this.setState(State.IDLE);
            this.attack();
        }
    }

    @Override
    public Cow clone() {
        Cow cow = new Cow(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getHealth(),
                this.getAttackSpeed(), this.getTimeUntilNextAttack(), this.getAttackDuration(), this.isTargetable(),
                this.getSpriteFilePath(), (Projectile) this.projectile.clone(), this.getAI());
        cow.setDuckManager(this.duckManager);
        return cow;
    }

    @Override
    public void setX(int x) {
        int dx = x - this.getX();

        this.projectile.setX(dx + this.projectile.getX());
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        int dy = y - this.getY();

        this.projectile.setY(dy + this.projectile.getY());
        super.setY(y);
    }

    @Override
    public void setPos(int x, int y) {
        int dx = x - this.getX();
        int dy = y - this.getY();

        this.projectile.move(dx, dy);
        super.setPos(x, y);
    }

    public void setDuckManager(DuckManager duckManager) {
        this.duckManager = duckManager;
    }

    public void setState(State newState) {
        if (newState == this.state)
            return;

        if (newState == State.ATTACK) {
            // idk change to attack sprite
        } else {
            // idk change to idle sprite
        }
        this.state = newState;
    }

    public void attack() {
        ProjectileManager.projectileManager.addProjectile(this.projectile.clone());
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

    public AI getAI() {
        return ai;
    }
}