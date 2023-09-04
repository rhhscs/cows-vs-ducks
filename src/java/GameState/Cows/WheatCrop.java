package src.java.GameState.Cows;

import java.awt.Color;
import java.awt.Graphics;
import src.java.GameState.AI;
import src.java.GameState.CheerioManager;
import src.java.GameState.Projectile;
import src.java.GameState.Sprite;
import src.java.GameState.PlayingField.Tile;
import src.java.Utilities.Input;

public class WheatCrop extends Cow {
    
    private int wheatStage = 0;
    private int[] wheatPerStage = {0, 25, 100, 200};
    private int wheatTimer = 0;

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
            250, 250, 0,
            true, cost,
            Sprite.WHEAT.clone(), Projectile.NULL, AI.WHEAT_CROP_COW_AI);
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
        getSprite().update();
        wheatTimer++;
        if (wheatStage == 0){
            if (wheatTimer >= getAttackSpeed()/2){
                attack();
                wheatTimer = 0;
            }
        } else {
            if (wheatTimer >= getAttackSpeed()){
                attack();
                wheatTimer = 0;
            }
        }

        Input input = Input.globalInput;
        if (input.mouseClicked() && this.containsPoint(input.mouseX(), input.mouseY())) {
            CheerioManager.getGlobalCheerios().addCheerios(collectWheat());
        }
    }

    @Override
    public void draw(Graphics g) {
        // if (this.wheatStage != 2) {
        //     this.getSprite().update();
        // }
        // this.getSprite().draw(g, this.getX(), this.getY() - 15, this.getWidth(), this.getHeight());
        super.draw(g);
        drawScuffed(g);
        
    }

    @Override
    public WheatCrop clone() {
        WheatCrop wheatCrop = new WheatCrop(this.getHealth(), this.getCost());
        wheatCrop.setDuckManager(getDuckManager());
        return wheatCrop;
    }

    private void drawScuffed(Graphics g){
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
    }

}