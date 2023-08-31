package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.Utilities.ResolutionManager;

public class Trash{
    private int yDest;
    private float animationTime = 4.0f;
    private Entity collider;
    private int x = ResolutionManager.WIDTH-320;
    private int y = ResolutionManager.HEIGHT-150;
    private int width = 320;
    private int height = 400;

    public Trash() {
        collider = new Entity(x, y, width + 100, height + 100);
        yDest = y;
    }
    
    public void moveTo(int y){
        yDest = y;
    }

    public boolean containsPoint(int x, int y){
        return collider.containsPoint(x, y);
    }

    public void update(){
        if (y != yDest){
            if (y < yDest){
                y = (y + Math.max(1, (int)((yDest-y)/animationTime)));
            }
            if (y > yDest){
                y = (y - Math.max(1, (int)((y-yDest)/animationTime)));
            }
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    
}
