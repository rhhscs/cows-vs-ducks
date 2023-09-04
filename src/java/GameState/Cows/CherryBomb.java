package src.java.GameState.Cows;

import java.awt.Graphics;

import src.java.GameState.Duck;
import src.java.GameState.PlayingField;
import src.java.GameState.Projectile;
import src.java.GameState.Sprite;

/**
 * A cherry bomb is untargetable, attacking/exploding as soon as it's placed.
 */
public class CherryBomb extends Cow {
    private final int spriteSize = (int) (PlayingField.Tile.SIZE * 3.5);

    public CherryBomb(int cost, Projectile projectile) {
        super(1, 0, 30, 14*Sprite.KABOOM.getTicksPerFrame(), false, cost, 
        Sprite.KABOOM, projectile, null);
    }

    @Override
    public void updateTarget() {
        this.setTarget(Duck.NULL_DUCK);
    }

    @Override
    public void onAttackEnd() {
        this.takeDamage(this.getHealth());
    }

    @Override
    public void draw(Graphics g) {
        this.getSprite().draw(g, getX() - spriteSize/3 - 10, getY() - spriteSize/3 - 30, spriteSize, spriteSize);
    }

    @Override
    public CherryBomb clone() {
        CherryBomb cherryBomb = new CherryBomb(this.getCost(), this.getProjectileClone());
        cherryBomb.setDuckManager(this.getDuckManager());
        return cherryBomb;
    }
}
