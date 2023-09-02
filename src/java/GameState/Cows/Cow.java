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
import java.io.File;

import javax.imageio.ImageIO;

import java.awt.Image;

public class Cow extends Entity implements Drawable, Updatable {
    public enum State {
        IDLE,
        ATTACK
    }

    private int attackSpeed;
    private int timeUntilFirstAttack;
    private int attackTimer;

    private int attackDuration; // needed for playing attack animation?
    private int health;
    private boolean isTargetable; // for spike weed and cherry bomb

    private Image[] attackSprites;
    private String spriteFilePath;
    private final int spriteTileSize = 256;
    private float frame = 0.0f;

    private State state;
    private Projectile projectile;
    private DuckManager duckManager;
    private AI ai;
    private Duck target;

    public static final Cow CHEERIO_CATAPULT = new Cow(0, 0, PlayingField.Tile.SIZE, PlayingField.Tile.SIZE, 100, 70,
            20, true, "src/img/sprite/cow_catapult",
            new Projectile(0, 20, 30, 30, 14, 18, 0, true, 0, true, 100000, null), AI.SHOOTER_COW_AI);

    public static final Cow WHEAT_CROP = new WheatCrop(0, 0, PlayingField.Tile.SIZE, PlayingField.Tile.SIZE, 100, 200,
            200, null, 50);
    
    public static final Cow CHERRY_BOMB = new CherryBomb(0, 0, PlayingField.Tile.SIZE, PlayingField.Tile.SIZE, 100, 0,
            80,
            new Projectile(-PlayingField.Tile.SIZE,-PlayingField.Tile.SIZE, PlayingField.Tile.SIZE*3, PlayingField.Tile.SIZE*3, 0, 200, 0, false, 0, true, 30, null));

    /**
     * This creates a new cow object.
     * 
     * @param x                    The top-left x coordinate.
     * @param y                    They top-left y coordinate.
     * @param width                The width of this cow.
     * @param height               The height of this cow.
     * @param health               The health points.
     * @param attackSpeed          The number of frames between attacks.
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
            boolean isTargetable, String spritesFilePath, Projectile projectile, AI ai) {
        super(x, y, width, height);

        this.attackSpeed = attackSpeed;
        this.attackTimer = 0;
        this.timeUntilFirstAttack = timeUntilFirstAttack;

        this.health = health;
        this.isTargetable = isTargetable;
        
        BufferedImage tempSpriteSheet;
        try {
            tempSpriteSheet = ImageIO.read(new File(spritesFilePath + "/attack.png"));
            attackSprites = new Image[(tempSpriteSheet.getWidth()/spriteTileSize)*(tempSpriteSheet.getHeight()/spriteTileSize)];
            int i = 0;
            for (int yPos = 0; yPos < tempSpriteSheet.getWidth(); yPos += spriteTileSize){
                for (int xPos = 0; xPos < tempSpriteSheet.getWidth(); xPos += spriteTileSize){
                    attackSprites[i] = tempSpriteSheet.getSubimage(xPos, yPos, spriteTileSize, spriteTileSize);
                    i++;
                }
            }
            attackDuration = i*4;
        } catch (Exception e){
            this.attackSprites = null;
        }
        //this.sprite = null;
        this.spriteFilePath = spritesFilePath;

        this.state = State.IDLE;
        this.projectile = projectile;
        this.ai = ai;
        this.target = null;




    }

    /**
     * This sets the duck manager of the constant static cows.
     * 
     * @param duckManager The duck manager to use.
     */
    public static void setStaticDuckManager(DuckManager duckManager) {
        CHEERIO_CATAPULT.setDuckManager(duckManager);
        WHEAT_CROP.setDuckManager(duckManager);
        CHERRY_BOMB.setDuckManager(duckManager);
    }

    @Override
    public void draw(Graphics g) {
        if (this.state == State.ATTACK){
            if (attackSprites != null){
                g.drawImage(attackSprites[((int)frame) % attackSprites.length], this.getX(), this.getY() - 15, this.getWidth(), this.getHeight(), null);
            } else {
                g.setColor(new Color(150, 100, 100));
                g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            }
            frame+=0.25;
        } else {
            if (attackSprites != null) {
                g.drawImage(attackSprites[0], this.getX(), this.getY() - 15, this.getWidth(), this.getHeight(), null);
            } else {
                g.setColor(new Color(50, 50, 50));
                g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            }

        }
    }

    @Override
    public void update() {

        if (this.state == State.ATTACK || this.attackTimer < this.attackSpeed) {
            this.attackTimer++;
        }

        if (this.attackTimer == this.attackSpeed) {
            // Update the target if the target dies.
            if (this.target != null && !this.target.isAlive()) {
                this.target = null;
            }

            // Find a new target if necessary.
            if (this.target == null) {
                this.target = (Duck) this.ai.findTarget(this.duckManager.getCollidingLanes(this), this);
            }

            // Start attack animation.
            if (this.target != null) {
                this.setState(State.ATTACK);
            }
        }

        if (this.attackTimer == this.attackSpeed + this.attackDuration) {
            // Attack animation ends, launch projectile
            this.setState(State.IDLE);
            this.attack();
            this.attackTimer = 0;
        }
    }

    @Override
    public Cow clone() {
        Cow cow = new Cow(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getHealth(),
                this.getAttackSpeed(), this.getAttackTimer(), this.isTargetable(),
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

        if (this.projectile != null){
            this.projectile.move(dx, dy);
        }
        super.setPos(x, y);
    }

    public void setDuckManager(DuckManager duckManager) {
        this.duckManager = duckManager;
    }
    protected DuckManager getDuckManager(){
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
            // idk change to attack sprite
        } else {
            // idk change to idle sprite
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

    public void setAttackTimer(int attackTimer){
        this.attackTimer = attackTimer;
    }

    public int getAttackDuration() {
        return attackDuration;
    }

    public int getHealth() {
        return health;
    }

    public Projectile getProjectileClone(){
        return (Projectile) this.projectile.clone();
    }

    public boolean isTargetable() {
        return isTargetable;
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

    public boolean isAlive() {
        return this.health > 0;
    }
}