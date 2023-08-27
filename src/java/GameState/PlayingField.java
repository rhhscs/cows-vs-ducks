package src.java.GameState;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;

import src.java.Drawable;
import src.java.Updatable;

/**
 * This class represents the lawn in the game. It manages the cows but not the
 * ducks. It requires the position of where it appears on screen.
 * 
 * @see DuckManager
 */
public class PlayingField extends Entity implements Drawable, Updatable {
    private Tile[][] grid;

    /**
     * This creates a new PlayingField object.
     * 
     * @param x_     The top-left x coordinate.
     * @param y_     The top-left y coordinate.
     * @param width  The number of tiles across horizontally.
     * @param height The number of tiles across vertically.
     */
    public PlayingField(int x_, int y_, int width, int height) {
        super(x_, y_, 
            width * Tile.SIZE + (width - 1) * Tile.MARGIN.x, 
            height * Tile.SIZE + (height - 1) * Tile.MARGIN.y);
        this.grid = new Tile[width][height];

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                int tileX = this.getX() + Tile.SIZE * x + Tile.MARGIN.x * x;
                int tileY = this.getY() + Tile.SIZE * y + Tile.MARGIN.y * y;

                this.grid[x][y] = new Tile(tileX, tileY);
            }
        }
    }

    /**
     * This gets the tile at the given coordinate on the screen.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The tile at the coordinate if the coordinate is within bounds, null
     *         otherwise.
     */
    public Tile getTileAt(int x, int y) {
        x -= this.getX();
        y -= this.getY();

        if (x < 0 || y < 0 || x >= Tile.SIZE * this.getWidth() || y >= Tile.SIZE * this.getHeight()) {
            return null;
        }
        return grid[x / Tile.SIZE][y / Tile.SIZE];
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
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the cow is removed, false if there is no tile or there is no cow.
     */
    public boolean removeCow(int x, int y) {
        Tile tile = this.getTileAt(x, y);

        if (tile == null) return false;
        if (!tile.isOccupied()) return false;

        tile.removeCow();
        return true;
    }

    /**
     * This gets the number of tiles across this playing field horizontally.
     * 
     * @return The width.
     */
    public int getWidth() {
        return this.grid.length;
    }

    /**
     * This gets the number of tiles across this playing field vertically.
     * 
     * @return The height.
     */
    public int getHeight() {
        return this.grid[0].length;
    }

    /**
     * This class represents a tile on the grid of a playing field. It manages the cow(s) on it.
     */
    public static class Tile extends Entity implements Drawable, Updatable {
        // the padding inside for where the cow sprite gets drawn.
        public static final Point PADDING = new Point(15, 15); 
        // the size of each tile of the lawn.
        public static final int SIZE = 100; 
        // the margin around the tile.
        public static final Point MARGIN = new Point(10, 10);

        private Cow cow;
        private boolean hovered;

        /**
         * This creates a new Tile object.
         * @param x The top-left x coordinate.
         * @param y The top-right y coordinate.
         */
        public Tile(int x, int y) {
            super(x, y, Tile.SIZE, Tile.SIZE);
            this.cow = null;
            this.hovered = false;
        }

        /**
         * This determines whether a cow can be placed at this tile.
         * 
         * @return True if it can be placed, false otherwise.
         */
        public boolean isOccupied() {
            if (cow == null)
                return false;
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
            cow.setPos(this.getX() + Tile.PADDING.x, this.getY() + Tile.PADDING.y);
            this.cow = cow;
        }

        /**
         * This removes the cow at this tile if there is a cow to remove.
         */
        public void removeCow() {
            this.cow = null;
        }

        @Override
        public void update() {
            if (cow != null)
                cow.update();
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(new Color(80, 145, 70));
            g.fillRect(this.getX(), this.getY(), Tile.SIZE, Tile.SIZE);

            if (cow != null)
                cow.draw(g);
        }
    }
}
