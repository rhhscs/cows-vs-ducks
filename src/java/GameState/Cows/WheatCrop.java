package src.java.GameState.Cows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import java.awt.Image;
import src.java.GameState.AI;
import src.java.GameState.CheerioManager;
import src.java.GameState.Projectile;
import src.java.GameState.PlayingField.Tile;
import src.java.Utilities.Input;

public class WheatCrop extends Cow {
    
    private int wheatStage = 0;
    private int[] wheatPerStage = {25, 100, 200};

    /**
     * This creates a new wheat crop object.
     * 
     * @param health               The health points.
     * @param cost                 The cost of this cow.
     * @param wheatSize            The size/amount of wheat given when it gets
     *                             produced.
     */
    public WheatCrop(int health, int cost) {
        super(health, 
            250, 100, 0,
            true, cost,
            Sprite.WHEAT, Projectile.NULL, AI.WHEAT_CROP_COW_AI);
        //this.wheatSprite = null;
        //this.wheatSpriteFilePath = wheatSpriteFilePath;
    }

    /**
     * This "attack" produces and stores wheat in this "cow".
     */
    @Override
    public void attack() {
        // drop wheat
        if (wheatStage < 2) {
            wheatStage++;
        }
    }

    /**
     * This collects the wheat stored by this "cow".
     * 
     * @return The amount of wheat stored.
     */
    
    
    public int collectWheat() {
        int wheatCollected = this.wheatPerStage[wheatStage];
        this.wheatStage = 0;
        return wheatCollected;
    }

    /**
     * "update" collects the wheat on click
     */
    @Override
    public void update() {
        super.update();
        Input input = Input.globalInput;
        if (input.mouseClicked() && this.containsPoint(input.mouseX(), input.mouseY())) {
            CheerioManager.getGlobalCheerios().addCheerios(collectWheat());
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.idleSprites == null) {
            super.draw(g);
            if (this.wheatStage != 0) {
                g.setColor(new Color(200, 200, 100));
                switch (wheatStage) {
                    /*case 4:
                        g.fillOval(this.getX() + Tile.SIZE / 2 + 5, this.getY() + Tile.SIZE / 2 + 5, Tile.SIZE / 2 - 10,
                                Tile.SIZE / 2 - 10);
                    case 3:
                        g.fillOval(this.getX() + 5, this.getY() + Tile.SIZE / 2 + 5, Tile.SIZE / 2 - 10,
                                Tile.SIZE / 2 - 10);*/
                    case 2:
                        g.fillOval(this.getX() + Tile.SIZE / 2 + 5, this.getY() + 5, Tile.SIZE / 2 - 10,
                                Tile.SIZE / 2 - 10);
                    case 1:
                        g.fillOval(this.getX() + 5, this.getY() + 5, Tile.SIZE / 2 - 10, Tile.SIZE / 2 - 10);

                        break;

                }
            } 
        }else {
            g.drawImage(idleSprites[wheatStage], this.getX(), this.getY()-20, Tile.SIZE, Tile.SIZE, null);
        }
    }

    @Override
    public WheatCrop clone() {
        WheatCrop wheatCrop = new WheatCrop(this.getHealth(), this.getCost());
        wheatCrop.setDuckManager(getDuckManager());
        return wheatCrop;
    }

}
