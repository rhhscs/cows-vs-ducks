package src.java.GameState;

import java.awt.Graphics;
import java.awt.Image;

import java.awt.Color;
import src.java.Drawable;
import src.java.Updatable;
import src.java.GameState.Cows.Cow;

public class Document extends Entity implements Drawable, Updatable{
    Cow applicant;
    //sprites
    Image fileSprite = null;
    private final float animationTime = 4.0f;
    private final Color color = new Color(222,222,100);
    private final Color borderColor = new Color(80, 50, 20);

    private int xDest = 0;
    private int yDest = 0;
    
    Document(Cow cow){
        super(10, 10, 250, 200);
        xDest = getX();
        yDest = getY();
        applicant = cow.clone();
    }

    @Override
    public void update() {
        if (getX() < xDest){
            setX(getX() + Math.max(1, (int)((xDest-getX())/animationTime)));
        }
        if (getX() > xDest){
            setX(getX() - Math.max(1, (int)((getX()-xDest)/animationTime)));
        }
        if (getY() < yDest){
            setY(getY() + Math.max(1, (int)((yDest-getY())/animationTime)));
        }
        if (getY() > yDest){
            setY(getY() - Math.max(1, (int)((getY()-yDest)/animationTime)));
        }
    }

    @Override
    public void draw(Graphics g) {
        if (fileSprite == null){
            g.setColor(color);
            g.fillRect(getX(), getY(), getWidth(), getHeight());
            g.setColor(borderColor);
            g.drawRect(getX(), getY(), getWidth(), getHeight());
        } 
    }

    // .getCow()
    public Cow hire(){
        
        return applicant;
    }

    public void moveTo(int x, int y){
        xDest = x;
        yDest = y;
    }
}
