package src.java.GameState.Cows;

import src.java.GameState.AI;
import src.java.GameState.CowSprite;
import src.java.GameState.Projectile;
import src.java.GameState.Sprite;

public class CerealBox extends Cow {
    private final int stagesOfOuch = 3;
    private final int healthPerOuch;

    public CerealBox(int health, int cost) {
        super(health, -1, -1, -1, 
        true, cost, Sprite.BODYGUARD, Projectile.NULL, AI.SHIELD_COW_AI);
        this.healthPerOuch = this.getHealth()/stagesOfOuch;
    }

    @Override
    public void update(){
        int stageOfOuch = (int) Math.ceil((double) this.getHealth() / this.healthPerOuch);
        this.getSprite().useCycle(CowSprite.IDLE_CYCLE + stageOfOuch);
    }
    
    @Override
    public CerealBox clone(){
        return new CerealBox(this.getHealth(), this.getCost());
    }
    
}
