package src.java.GameState.Cows;

import src.java.GameState.Projectile;

/**
 * A cherry bomb is untargetable, attacking/exploding as soon as it's placed.
 */
public class CherryBomb extends Cow {
    public CherryBomb(int cost, int attackDelay, Projectile projectile) {
        super(1, 0, 0, attackDelay, false, cost, 
        Sprite.NULL, projectile, null);
        this.setState(State.ATTACK);
    }

    /**
     * Upon attacking, this unit automatically dies.
     */
    @Override
    public void attack() {
        this.takeDamage(this.getHealth());
        super.attack();
    }

    /**
     * Starts attack animation immediately.
     */
    @Override
    public void update() {
        if (this.getAttackTimer() == this.getAttackDelay()) {
            // Attack animation ends, launch projectile
            this.attack();
        } else {
            this.setAttackTimer(this.getAttackTimer() + 1);
        }
    }

    @Override
    public CherryBomb clone() {
        CherryBomb cherryBomb = new CherryBomb(this.getCost(), this.getAttackDelay(), this.getProjectileClone());
        cherryBomb.setDuckManager(this.getDuckManager());
        return cherryBomb;
    }
}
