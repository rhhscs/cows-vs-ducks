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
    public CherryBomb(int x, int y, int width, int height, int health, int attackSpeed, int timeUntilFirstAttack,
            Projectile projectile) {
        super(x, y, width, height, health, attackSpeed, timeUntilFirstAttack, true,
                null,
                projectile, null);
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

        if (this.getAttackTimer() == this.getAttackDuration()) {
            // Attack animation ends, launch projectile
            this.attack();

        } else{
            this.setAttackTimer(this.getAttackTimer()+1);
        }

    }

    @Override
    public CherryBomb clone() {
        CherryBomb cherryBomb = new CherryBomb(this.getX(), this.getY(), this.getWidth(), this.getHeight(),
                this.getHealth(), this.getAttackSpeed(), this.getTimeUntilFirstAttack(),
                this.getProjectileClone());
        cherryBomb.setDuckManager(getDuckManager());
        return cherryBomb;
    }
}
