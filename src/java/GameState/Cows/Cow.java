package src.java.GameState.Cows;

import src.java.Drawable;
import src.java.Updatable;
import src.java.GameState.AI;
import src.java.GameState.CowSprite;
import src.java.GameState.Duck;
import src.java.GameState.DuckManager;
import src.java.GameState.Entity;
import src.java.GameState.PlayingField;
import src.java.GameState.ProjectileManager;
import src.java.GameState.Sprite;
import src.java.GameState.Projectile;

import java.awt.Graphics;

public class Cow extends Entity implements Drawable, Updatable {
    public enum State {
        IDLE,
        ATTACK
    }

    private int attackSpeed;
    private int timeUntilFirstAttack;
    private int attackTimer = 0;
    private int attackDelay;

    private int health;
    private boolean isTargetable;
    private int cost;

    /**
     * The number of frames between the start of the attack animation and when the
     * projectile gets released.
     */

    private CowSprite sprite;

    private State state;
    private Projectile projectile;
    private DuckManager duckManager;
    private AI ai;
    private Duck target;

    public static final Cow CHEERIO_CATAPULT = new Cow(100,
            70, 20, 12,
            true, 200,
            Sprite.CATAPULT,
            new Projectile(20, 20, 30, 30, 14, 18, 0, true, 0, true, 100000, null), AI.SHOOTER_COW_AI);

    public static final Cow CEREAL_BOX = new CerealBox(300, 200); // make sure HP is divisible by 3

    public static final Cow WHEAT_CROP = new WheatCrop(50, 100);

    public static final Cow CEREAL_BOMB = new CherryBomb(500,
            new Projectile(-PlayingField.Tile.SIZE, -PlayingField.Tile.SIZE, PlayingField.Tile.SIZE * 3,
                    PlayingField.Tile.SIZE * 3, 0, 200, 0, false, 0, true, 2, null));

    public static final Cow CRUSHED_CEREAL = new Cow(100, 40, 0, 2, false, 100, Sprite.SPIKES,
            new Projectile(0, 0, PlayingField.Tile.SIZE, PlayingField.Tile.SIZE, 0, 18, 0, false, 0, true, 20, null),
            AI.MELEE_COW_AI);

    public static final Cow COLD_FRIDGE = new Cow(100,
            70, 20, 12,
            true, 300,
            Sprite.FRIDGE,
            new Projectile(20, 20, 30, 30, 14, 18, 40, true, 1, true, 100000, null), AI.SHOOTER_COW_AI);

    public static final Cow PEA_POD = new StackableCow(true, 25, Sprite.STACK_COW);

    public static final Cow CHEERIO_PITCHER = new Cow(100, 70, 20, 12, true, 400, Sprite.CATAPULT,
            new Projectile(20, 20, PlayingField.WIDTH, 30, 0, 18, 0, false, 0, true, 5, null), AI.SHOOTER_COW_AI);

    /**
     * This constructs a single-tile cow.
     * 
     * @param health               The health points.
     * @param attackSpeed          The time until the attack animation starts.
     * @param timeUntilFirstAttack The time until the first attack starts.
     * @param attackDelay          The time when the projectile gets released during
     *                             the attack animation.
     * @param isTargetable         Whether the cow can be hit by ducks.
     * @param cost                 The cost of this cow.
     * @param sprite               The cow sprite.
     * @param projectile           The projectile this cow attacks with.
     * @param ai                   The AI to use to determine ducks to attack.
     */
    public Cow(int health, int attackSpeed, int timeUntilFirstAttack, int attackDelay,
            boolean isTargetable, int cost, CowSprite sprite, Projectile projectile, AI ai) {
        this(PlayingField.Tile.SIZE, PlayingField.Tile.SIZE, health, attackSpeed, timeUntilFirstAttack, attackDelay,
                isTargetable, cost, sprite, projectile, ai);
    }

    /**
     * This constructs a cow with full customization.
     * 
     * @param width                The width of the cow.
     * @param height               The height of the cow.
     * @param health               The health points.
     * @param attackSpeed          The time until the attack animation starts.
     * @param timeUntilFirstAttack The time until the first attack starts.
     * @param attackDelay          The time when the projectile gets released during
     *                             the attack animation.
     * @param isTargetable         Whether the cow can be hit by ducks.
     * @param cost                 The cost of this cow.
     * @param sprite               The cow sprite.
     * @param projectile           The projectile this cow attacks with.
     * @param ai                   The AI to use to determine ducks to attack.
     */
    public Cow(int width, int height, int health,
            int attackSpeed, int timeUntilFirstAttack, int attackDelay,
            boolean isTargetable, int cost,
            CowSprite sprite, Projectile projectile, AI ai) {
        super(0, 0, width, height);

        // stats
        this.attackSpeed = attackSpeed;
        this.attackTimer = attackSpeed - timeUntilFirstAttack;
        this.timeUntilFirstAttack = timeUntilFirstAttack;
        this.attackDelay = attackDelay;
        this.health = health;
        this.isTargetable = isTargetable;
        this.cost = cost;

        // sprite
        this.sprite = sprite.clone();
        this.sprite.useIdleCycle();

        this.setState(State.IDLE);
        this.projectile = projectile;
        this.ai = ai;
        this.target = null;

    }

    /**
     * This initializes the static cows.
     * 
     * @param duckManager The duck manager to use.
     */
    public static void init(DuckManager duckManager) {
        CHEERIO_CATAPULT.setDuckManager(duckManager);
        WHEAT_CROP.setDuckManager(duckManager);
        CEREAL_BOMB.setDuckManager(duckManager);
        CRUSHED_CEREAL.setDuckManager(duckManager);
        COLD_FRIDGE.setDuckManager(duckManager);
        CHEERIO_PITCHER.setDuckManager(duckManager);

        PEA_POD.setDuckManager(duckManager);
        Cow[] peas = { CHEERIO_CATAPULT.clone(), CHEERIO_CATAPULT.clone(), CHEERIO_CATAPULT.clone() };
        peas[0].setPos(50, 25);
        peas[1].setPos(0, 25);
        peas[2].setPos(25, 0);

        ((StackableCow) PEA_POD).setCows(peas);
    }

    @Override
    public void draw(Graphics g) {
        this.sprite.draw(g, getX(), getY() - 15, getWidth(), getHeight());
    }

    @Override
    public void update() {
        this.sprite.update();

        if (this.state == State.ATTACK || this.attackTimer < this.attackSpeed) {
            this.attackTimer++;
        }

        if (this.attackTimer == this.attackSpeed) {
            this.updateTarget();

            // Start attack animation.
            if (this.target != null) {
                this.onAttackStart();
            }
        }

        if (this.attackTimer == this.attackSpeed + this.attackDelay) {
            // launch projectile
            this.onAttackLaunch();
        }

        if (this.attackTimer == this.attackSpeed + sprite.getCycleTicks(CowSprite.ATTACK_CYCLE)) {
            onAttackEnd();
            this.attackTimer = 0;
        }
    }

    public void updateTarget() {
            // Update the target if the target dies.
            if (this.target != null && !this.target.isAlive()) {
                this.target = null;
            }

            // Find a new target if necessary.
            if (this.target == null) {
                this.target = (Duck) this.ai.findTarget(this.duckManager.getCollidingLanes(this), this);
            }
    }

    /**
     * This runs code that should occur when the attack animation starts.
     */
    public void onAttackStart() {
        this.setState(State.ATTACK);
    }

    /**
     * This runs code that occurs when the attack comes out.
     */
    public void onAttackLaunch() {
        this.attack();
    }

    /**
     * This runs code when the attack animation ends.
     */
    public void onAttackEnd() {
        this.setState(State.IDLE);
    }

    @Override
    public Cow clone() {
        Cow cow = new Cow(this.getWidth(), this.getHeight(), this.getHealth(),
                this.getAttackSpeed(), this.getAttackTimer(), this.getAttackDelay(),
                this.isTargetable(), this.getCost(),
                this.getSprite(),
                this.getProjectileClone(), this.getAI());
        cow.setDuckManager(this.duckManager);
        cow.setPos(this.getX(), this.getY());

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

        if (this.projectile != null) {
            this.projectile.move(dx, dy);
        }

        super.setPos(x, y);
    }

    public void setTarget(Duck duck) {
        this.target = duck;
    }

    public void setDuckManager(DuckManager duckManager) {
        this.duckManager = duckManager;
    }

    protected DuckManager getDuckManager() {
        return duckManager;
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        this.projectile.move(dx, dy);
    }

    public void setState(State newState) {
        if (newState == this.state)
            return;

        if (newState == State.ATTACK) {
            this.sprite.useAttackCycle();
        } else {
            this.sprite.useIdleCycle();
        }
        this.state = newState;
    }

    public void attack() {
        ProjectileManager.projectileManager.addProjectile(this.projectile.clone());
    }

    /**
     * This deals damage to this cow.
     * 
     * @param damage Amount of damage to do.
     */
    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getTimeUntilFirstAttack() {
        return timeUntilFirstAttack;
    }

    public int getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(int attackTimer) {
        this.attackTimer = attackTimer;
    }

    public int getAttackDelay() {
        return attackDelay;
    }

    public int getHealth() {
        return health;
    }

    public int getCost() {
        return cost;
    }

    public Projectile getProjectileClone() {
        return this.projectile.clone();
    }

    public boolean isTargetable() {
        return isTargetable;
    }

    public CowSprite getSprite() {
        return sprite;
    }

    public State getState() {
        return state;
    }

    public AI getAI() {
        return ai;
    }

    public boolean isAlive() {
        return this.health > 0;
    }
}