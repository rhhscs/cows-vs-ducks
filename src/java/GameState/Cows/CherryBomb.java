package src.java.GameState.Cows;

import src.java.GameState.Projectile;

public class CherryBomb extends Cow {

    /**
     * This creates a new wheat crop object.
     * 
     * @param x                    The top-left x coordinate.
     * @param y                    They top-left y coordinate.
     * @param width                The width of this wheat crop.
     * @param height               The height of this wheat crop.
     * @param health               The health points.
     * @param attackSpeed          The wheat production speed.
     * @param timeUntilFirstAttack The time until the first wheat is produced.
     * @param attackDuration       The time between wheat produced and the start of
     *                             the next wheat produced.
     * @param spriteFilePath       The sprite sheet file path.
     */
    public CherryBomb(int width, int height, int health, int attackSpeed, int timeUntilFirstAttack,
            Projectile projectile) {
        super(width, height, health, 
            attackSpeed, timeUntilFirstAttack, 100,
            true, 500,
            null, 0, projectile, null);
            this.setState(State.ATTACK);
    }

    /**
     * This attack also kills this unit
     */
    @Override
    public void attack() {
        this.takeDamage(this.getHealth());
        super.attack();
    }
    /**
     * This unit attacks automatically
     */
    @Override
    public void update(){

        if (this.getAttackTimer() == this.getAttackDelay()) {
            // Attack animation ends, launch projectile
            this.attack();

        } else{
            this.setAttackTimer(this.getAttackTimer()+1);
        }

    }

    @Override
    public CherryBomb clone() {
        CherryBomb cherryBomb = new CherryBomb(this.getWidth(), this.getHeight(),
                this.getHealth(), this.getAttackSpeed(), this.getTimeUntilFirstAttack(),
                this.getProjectileClone());
        cherryBomb.setDuckManager(getDuckManager());
        return cherryBomb;
    }
}
