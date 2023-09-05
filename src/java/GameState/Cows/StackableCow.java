package src.java.GameState.Cows;

import java.awt.Graphics;

import src.java.GameState.AI;
import src.java.GameState.CowSprite;
import src.java.GameState.PlayingField;
import src.java.GameState.Projectile;
import src.java.GameState.Sprite;

/**
 * A stackable cow is a cow that fires multiple projectiles at once.
 */
public class StackableCow extends Cow {
    private int curSize;
    private final int attackDelays = 12;
    public static final int MAX = 3;
    
    private Projectile firstShot = new Projectile(50, 90, 40, 40, 16, 12, 0, true, 0, true, 10000, Sprite.CHEERIO);
    private Projectile secondShot = new Projectile(50, 10, 40, 40, 16, 15, 0, true, 0, true, 10000, Sprite.CHEERIO);
    private Projectile thirdShot = new Projectile(50, 50, 40, 40, 16, 18, 0, true, 0, true, 10000, Sprite.CHEERIO);

    /**
     * This creates a new stackable cow object.
     * 
     * @param isTargetable whether the cow can be hit by ducks.
     * @param cost         The cost of this cow.
     * @param sprite       The cow sprite.
     */
    public StackableCow(int cost, CowSprite sprite) {
        super(100, 70, 20, 8*3, true, cost, sprite, Projectile.NULL, AI.SHOOTER_COW_AI);
        getSprite().useCycle(CowSprite.IDLE_CYCLE + 1);
        this.curSize = 0;
    }


    public void stackCow() {
        this.curSize++; // Math.max(cows.length, curSize + 1) ?
        
        if (getState() == State.IDLE){
            getSprite().useCycle(CowSprite.IDLE_CYCLE + this.curSize);
        }  else if (getState() == State.ATTACK){
            getSprite().useCycleSilent(CowSprite.ATTACK_CYCLE + this.curSize);
        }
    }
    public void removeCow() {
        this.curSize--; // Math.min(0, cuSize - 1) ?
    }

    @Override
    public void update() {
        
        if (this.getState() == State.ATTACK || this.getAttackTimer() < this.getAttackSpeed()) {
            setAttackTimer(getAttackTimer()+1);
        }

        if (this.getAttackTimer() == this.getAttackSpeed()) {
            this.updateTarget();

            // Start attack animation.
            if (this.target != null) {
                if (getState() != State.ATTACK){
                    this.setRawState(State.ATTACK);
                    getSprite().useCycle(CowSprite.ATTACK_CYCLE + this.curSize);
                }
            }
        }

        if (this.getAttackTimer() == this.getAttackSpeed() + this.getAttackDelay()) {
            this.attackWith(firstShot);
        }
        if (this.curSize >= 2 && this.getAttackTimer() == this.getAttackSpeed() + this.getAttackDelay() + attackDelays) {
            this.attackWith(secondShot);
        }
        if (this.curSize >= 3 && this.getAttackTimer() == this.getAttackSpeed() + this.getAttackDelay() + attackDelays*2) {
            this.attackWith(thirdShot);
        }

        if (this.getAttackTimer() == this.getAttackSpeed() + getSprite().getCycleTicks(CowSprite.ATTACK_CYCLE + this.curSize)) {
            if (getState() != State.IDLE){
                this.setRawState(State.IDLE);
                getSprite().useCycle(CowSprite.IDLE_CYCLE + this.curSize);
            }
            setAttackTimer(0);
        }
    }


    @Override
    public void draw(Graphics g) {
        getSprite().draw(g, getX() + 10, getY() - (PlayingField.Tile.SIZE/2) + 14, getWidth() - 10, (int)(getHeight()*1.4) - 14);
    }

    public boolean isFull() {
        System.out.println(curSize + " " + MAX);
        return this.getCurSize() >= MAX;
    }


    public int getCurSize() {
        return this.curSize;
    }

    @Override
    public void move(int dx, int dy){
        super.move(dx, dy);
        firstShot.move(dx, dy);
        secondShot.move(dx, dy);
        thirdShot.move(dx, dy);
    }

    @Override
    public StackableCow clone() {
        StackableCow clone = new StackableCow(this.getCost(), this.getSprite());
        clone.setDuckManager(getDuckManager());
        return clone;
    }

    @Override
    public String toString() {
        return "StackableCow: " + this.getCurSize() + " stacked";
    }
}
