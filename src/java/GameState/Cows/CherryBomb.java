package src.java.GameState.Cows;

import java.awt.Graphics;

import src.java.GameState.PlayingField;
import src.java.GameState.Projectile;

/**
 * A cherry bomb is untargetable, attacking/exploding as soon as it's placed.
 */
public class CherryBomb extends Cow {
    boolean startAttack = false;
    private int spriteSize = (int) (PlayingField.Tile.SIZE*3.5);

    public CherryBomb(int cost, Projectile projectile) {
        super(1, 0, 30, 14*Sprite.KABOOM.ticksPerFrame, false, cost, 
        Sprite.KABOOM, projectile, null);
        this.setState(State.ATTACK);
        frame = 0;
    }


    @Override
    public void update() {
        this.setAttackTimer(this.getAttackTimer() + 1);

        if (this.getAttackTimer() == this.getTimeUntilFirstAttack()) {
            startAttack = true;
        } else if (this.getAttackTimer() == this.getTimeUntilFirstAttack() + this.getAttackDelay()){
            this.attack();
        } else if (this.getAttackTimer() == this.getTimeUntilFirstAttack() + this.getSprite().getAttackTicks()){
            this.takeDamage(this.getHealth());
        }

        if (startAttack) {
            frame++;
        }
    }

    @Override
    public void draw(Graphics g){
        if (attackSprites != null){
            g.drawImage(attackSprites[Math.min(frame/this.getSprite().ticksPerFrame, 19)], getX()- spriteSize/3 - 10, getY()- spriteSize/3 - 30, spriteSize, spriteSize, null);
        }
    }

    @Override
    public CherryBomb clone() {
        CherryBomb cherryBomb = new CherryBomb(this.getCost(), this.getProjectileClone());
        cherryBomb.setDuckManager(this.getDuckManager());
        return cherryBomb;
    }
}
