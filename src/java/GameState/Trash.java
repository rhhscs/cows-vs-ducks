package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import src.java.Utilities.ResolutionManager;

public class Trash{
    private int yDest;
    private float animationTime = 4.0f;
    private Entity collider;
    private int x = ResolutionManager.WIDTH-280;
    private int y = ResolutionManager.HEIGHT-180;
    private int width = 250;
    private int height = 250;
    private Image closed;
    private Image opened;
    public boolean hovered = false;

    public Trash() {
        collider = new Entity(x, y, width + 100, height + 100);
        yDest = y;
        try{
            closed = ImageIO.read(new File("src/img/sprite/trash_closed.png"));
            opened = ImageIO.read(new File("src/img/sprite/trash_open.png"));
        } catch (Exception e){}
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
        if (closed != null && !hovered){
            g.drawImage(closed, x, y, width, height, null);
        } else if (opened != null && hovered){
            g.drawImage(opened, x, y, width, height, null);
        } else {
            
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    
}
