package src.java.GameState.Cows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import src.java.GameState.AI;
import src.java.GameState.CheerioManager;
import src.java.GameState.PlayingField.Tile;
import src.java.Utilities.Input;

public class WheatCrop extends Cow {
    private int wheat;
    private int wheatSize;

    /**
     * The max amount of wheat the wheat crop can hold
     */
    private int maxWheat = 200;
    private BufferedImage wheatSprite;
    private String wheatSpriteFilePath;

    /**
     * This creates a new wheat crop object.
     * 
     * @param health               The health points.
     * @param cost                 The cost of this cow.
     * @param wheatSize            The size/amount of wheat given when it gets
     *                             produced.
     */
    public WheatCrop(int health, int cost, int wheatSize) {
        super(health,
                200, 200, 0,
                true, cost,
                null, 0, 0, null, AI.WHEAT_CROP_COW_AI);
        this.wheat = 0;
        this.wheatSize = wheatSize;
        this.wheatSprite = null;
        this.wheatSpriteFilePath = wheatSpriteFilePath;
    }

    /**
     * This "attack" produces and stores wheat in this "cow".
     */
    @Override
    public void attack() {
        // drop wheat
        if (wheat < maxWheat) {
            this.wheat += this.wheatSize;
            System.out.println("Wheat produced");
        }
    }

    /**
     * This collects the wheat stored by this "cow".
     * 
     * @return The amount of wheat stored.
     */
    public int collectWheat() {
        int wheatCollected = this.wheat;
        this.wheat = 0;
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
        super.draw(g);

        if (this.wheat > 0) {
            if (this.wheatSprite == null) {
                g.setColor(new Color(200, 200, 100));
                switch (wheat / wheatSize) {
                    case 0:
                        break;
                    case 4:
                        g.fillOval(this.getX() + Tile.SIZE / 2 + 5, this.getY() + Tile.SIZE / 2 + 5, Tile.SIZE / 2 - 10,
                                Tile.SIZE / 2 - 10);
                    case 3:
                        g.fillOval(this.getX() + 5, this.getY() + Tile.SIZE / 2 + 5, Tile.SIZE / 2 - 10,
                                Tile.SIZE / 2 - 10);
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

    @Override
    public WheatCrop clone() {
        WheatCrop wheatCrop = new WheatCrop(this.getHealth(), this.getCost(), this.getWheatSize());
        wheatCrop.setDuckManager(getDuckManager());
        return wheatCrop;
    }

    public int getWheatSize() {
        return wheatSize;
    }

    public String getWheatSpriteFilePath() {
        return wheatSpriteFilePath;
    }
}
