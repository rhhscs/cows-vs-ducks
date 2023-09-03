package src.java.GameState.Cows;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import src.java.GameState.AI;
import src.java.GameState.Projectile;

public class CerealBox extends Cow {
    private final int stagesOfOuch = 3;
    private final int healthPerOuch;
    private int idleTicks;

    public CerealBox(int health, int cost) {
        super(health, -1, -1, -1, 
        true, cost, Sprite.BODYGUARD, Projectile.NULL, AI.SHIELD_COW_AI);
        this.healthPerOuch = this.getHealth()/stagesOfOuch;
        this.idleTicks = getSprite().getIdleTicks()/stagesOfOuch;
    }

    @Override
    public void update(){
        frame++;
        if (frame >= this.idleTicks){
            frame = 0;
        }
    }
    
    @Override
    public void draw(Graphics g){
        int a = 0;
        if (this.getHealth() > healthPerOuch*2){
            a = 0;
        } else if (this.getHealth() > healthPerOuch){
            a = 4;
        } else {
            a = 8;

        }
        g.drawImage(idleSprites[a + (frame/getSprite().ticksPerFrame)], this.getX()-10, this.getY() - 25, this.getWidth() + 15, this.getHeight() + 15, null);
        
    }

    @Override
    public CerealBox clone(){
        return new CerealBox(this.getHealth(), this.getCost());
    }
    
}
