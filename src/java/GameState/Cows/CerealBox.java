package src.java.GameState.Cows;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import src.java.GameState.AI;
import src.java.GameState.Projectile;

public class CerealBox extends Cow {
    private final int stagesOfOuch = 3;
    private final int healthPerOuch = this.getHealth()/stagesOfOuch;
    private int maxHealth;

    public CerealBox(int health, int cost) {
        super(health, -1, -1, -1, 
        true, 150, Sprites.BODYGUARD, 0, 12, null, AI.SHIELD_COW_AI);
        this.maxHealth = health;
        this.ticksPerFrame = 4;
        this.idleAnimationDuration = ticksPerFrame * (12/stagesOfOuch);
    }

    @Override
    public void update(){
        frame++;
        if (frame >= this.idleAnimationDuration){
            frame = 0;
        }
    }
    
    @Override
    public void draw(Graphics g){
        if (this.getHealth() > 0)
        g.drawImage(idleSprites[(((maxHealth-this.getHealth()+1)/healthPerOuch)*this.getIdleAnimationFrames()) + (frame/ticksPerFrame)], this.getX(), this.getY() - 25, this.getWidth() + 10, this.getHeight() + 10, null);
    }

    @Override
    public CerealBox clone(){
        return new CerealBox(this.getHealth(), this.getCost());
    }
    
}
