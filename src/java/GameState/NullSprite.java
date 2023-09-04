package src.java.GameState;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class NullSprite extends Sprite {
    public NullSprite() {
        super(0, 0, 0);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.CYAN);
        ((Graphics2D) g).setStroke(new BasicStroke(4));
        g.drawRect(x, y, width, height);
    }
}
