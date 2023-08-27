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
    private final Color color = new Color(222,222,100);
    private final Color borderColor = new Color(80, 50, 20);
    
    Document(Cow cow){
        super(10, 10, 250, 200);
        applicant = cow;
    }

    @Override
    public void update() {
        
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
}
