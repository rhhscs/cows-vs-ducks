package src.java.GameState;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Color;

import src.java.Drawable;
import src.java.Updatable;
import src.java.GameState.Cows.Cow;
import src.java.GameState.Cows.StackableCow;

/**
 * This class represents the lawn in the game. It manages the cows but not the
 * ducks. It requires the position of where it appears on screen.
 * 
 * @see DuckManager
 */
public class PlayingField extends Entity implements Drawable, Updatable {
    public static final int X = 450;
    public static final int Y = 140;

    public static final int NUM_LANES = 5;
    public static final int NUM_COLUMNS = 7;

    public static final int WIDTH = NUM_COLUMNS * Tile.SIZE;
    public static final int HEIGHT = NUM_LANES * Tile.SIZE;

    private Tile[][] grid;

    /**
     * This creates a new PlayingField object.
     */
    public PlayingField() {
        super(X, Y, WIDTH, HEIGHT);
        this.grid = new Tile[NUM_COLUMNS][NUM_LANES];

        for (int cellY = 0; cellY < this.getHeight(); cellY++) {
            for (int cellX = 0; cellX < this.getWidth(); cellX++) {
                int tileX = this.getX() + Tile.SIZE * cellX;
                int tileY = this.getY() + Tile.SIZE * cellY;

                this.grid[cellX][cellY] = new Tile(tileX, tileY);
            }
        }
    }

    /**
     * This gets the tile at the real given coordinate on the screen.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The tile at the coordinate if the coordinate is within bounds, null
     *         otherwise.
     */
    public Tile getTileAt(int x, int y) {
        Point cellPos = this.getCellTileCoordinate(x, y);

        return this.getTileAtByCell(cellPos.x, cellPos.y);
    }

    /**
     * This gets the tile given its cell coordinate in the grid.
     * 
     * @param cellX The cell x coordinate.
     * @param cellY The cell y coordinate.
     * @return The tile at the cell coordinate if the coordinate is within the grid,
     *         null otherwise.
     */
    private Tile getTileAtByCell(int cellX, int cellY) {
        if (cellX < 0 || cellY < 0 || cellX >= this.getWidth() || cellY >= this.getHeight())
            return null;

        return this.grid[cellX][cellY];
    }

    /**
     * This gets the top-left coordinate of the tile that contains the given
     * real coordinate.
     * 
     * @param x The real x coordinate.
     * @param y The real y coordinate.
     * @return The real coordinate of the tile at the given coordinate.
     */
    public Point getRealTileCoordinate(int x, int y) {
        Tile tile = getTileAt(x, y);
        return tile.getPos();
    }

    /**
     * This gets the cell coordinate of a tile given a real coordinate.
     * 
     * @param x The real x coordinate.
     * @param y The real y coordinate.
     * @return The cell coordinate within the grid.
     */
    public Point getCellTileCoordinate(int x, int y) {
        x -= this.getX();
        y -= this.getY();

        return new Point(x / Tile.SIZE, y / Tile.SIZE);
    }

    /**
     * This gets whether the tile at the given coordinate is occupied by a cow or
     * not. For stackable cows (pea pod), it only is occupied if no more cows can be
     * stacked.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if it is occupied, false otherwise.
     */
    public boolean isOccupied(int x, int y) {
        Tile tile = getTileAt(x, y);
        if (tile == null || !this.containsPoint(x, y))
            return true;
        return tile.isOccupied();
    }

    @Override
    public void update() {
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                this.grid[x][y].update();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                this.grid[x][y].draw(g);
            }
        }
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                this.grid[x][y].drawCow(g);
            }
        }
    }

    /**
     * This places a cow at the tile at a coordinate on the screen.
     * 
     * @param cow The cow to place.
     * @param x   The x coordinate.
     * @param y   The y coordinate.
     * @return True if the cow can be placed, false if there is no tile or the cow
     *         can't be placed.
     */
    public boolean placeCow(Cow cow, int x, int y) {
        Tile tile = this.getTileAt(x, y);

        if (tile == null)
            return false;
        if (tile.isOccupied())
            return false;

        tile.placeCow(cow);
        return true;
    }

    /**
     * This removes the cow from the tile at a coordinate on the screen.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the cow is removed, false if there is no tile or there is no
     *         cow.
     */
    public boolean removeCow(int x, int y) {
        Tile tile = this.getTileAt(x, y);

        if (tile == null)
            return false;
        if (!tile.isOccupied())
            return false;

        tile.removeCow();
        return true;
    }

    /**
     * This gets a list of the cows in the specified lane.
     * 
     * @param laneIndex The index of the lane to get.
     * @return A non-null list containing the cows in the lane.
     */
    public ArrayList<Entity> getCowsInLane(int laneIndex) {
        ArrayList<Entity> cows = new ArrayList<Entity>();

        for (int x = 0; x < this.getWidth(); x++) {
            if (this.grid[x][laneIndex].isOccupied()) {
                cows.add(this.grid[x][laneIndex].getCow());
            }
        }

        return cows;
    }

    /**
     * This gets the number of tiles across this playing field horizontally.
     * 
     * @return The width.
     */
    @Override
    public int getWidth() {
        return NUM_COLUMNS;
    }

    /**
     * This gets the number of tiles across this playing field vertically.
     * 
     * @return The height.
     */
    @Override
    public int getHeight() {
        return NUM_LANES;
    }

    /**
     * This class represents a tile on the grid of a playing field. It manages the
     * cow(s) on it.
     */
    public static class Tile extends Entity implements Drawable, Updatable {
        // the size of each tile of the lawn.
        public static final int SIZE = 150;

        private Cow cow;
        private boolean hovered;
        private BufferedImage sprite;

        /**
         * This creates a new Tile object without a sprite.
         * 
         * @param x The top-left x coordinate.
         * @param y The top-right y coordinate.
         */
        public Tile(int x, int y) {
            super(x, y, Tile.SIZE, Tile.SIZE);
            this.cow = null;
            this.hovered = false;
            this.sprite = null;
        }

        /**
         * This creates a new Tile object with a sprite.
         * 
         * @param x              The top-left x coordinate.
         * @param y              The top-right y coordinate.
         * @param spriteFilePath The file path of the sprite.
         */
        public Tile(int x, int y, String spriteFilePath) {
            super(x, y, Tile.SIZE, Tile.SIZE);
            this.cow = null;
            this.hovered = false;
            this.sprite = null; // TODO create sprites
        }

        /**
         * This determines whether a cow can be placed at this tile.
         * 
         * @return True if it can be placed, false otherwise.
         */
        public boolean isOccupied() {
            if (this.cow == null)
                return false;
            if (this.cow instanceof StackableCow)
                return ((StackableCow) this.cow).isFull();

            return true;
        }

        /**
         * This gets if the mouse is hovered or clicked on this tile.
         * 
         * @return True if it's hovered, false otherwise.
         */
        public boolean isHovered() {
            return this.hovered;
        }

        public void setIsHovered(boolean hovered) {
            this.hovered = hovered;
        }

        /**
         * This places a cow in this tile.
         * 
         * @param cow The cow to place in this tile.
         */
        public void placeCow(Cow cow) {
            if (cow instanceof StackableCow && this.cow instanceof StackableCow) {
                // there is already a stacked cow so stack cow.
                ((StackableCow) this.cow).stackCow();
            } 
            else {
                cow.move(this.getX(), this.getY());
                this.cow = cow;
            }
        }

        /**
         * This removes the cow at this tile if there is a cow to remove.
         */
        public void removeCow() {
            this.cow = null;
        }

        /**
         * This gets the cow stored at this tile.
         * 
         * @return The cowe if there is one stored, null otherwise.
         */
        public Cow getCow() {
            return this.cow;
        }

        @Override
        public void update() {
            if (this.cow != null) {
                this.cow.update();
                
                if (!this.cow.isAlive()) {
                    this.cow = null;
                }
            }
        }

        @Override
        public void draw(Graphics g) {
            if (sprite == null) {
                g.setColor(new Color(50, 125, 50));
                g.fillRect(this.getX(), this.getY(), Tile.SIZE, Tile.SIZE);
                g.setColor(new Color(80, 145, 70));
                g.fillRect(this.getX() + 5, this.getY() + 5, Tile.SIZE - 10, Tile.SIZE - 10);
            }
        }
        public void drawCow(Graphics g){
            if (this.cow != null)
                this.cow.draw(g);
        }
    }
}
