package src.java.Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;

import src.java.Drawable;
import src.java.Updatable;
import src.java.Components.ButtonFunction;
import src.java.Utilities.Input;
import src.java.Utilities.ResolutionManager;

public class Button implements Drawable, Updatable {
    private int x, y;
    private int width, height;
    private ButtonFunction func;

    // making default cyan cuz why not :D
    private Color buttonColor = Color.CYAN; 
    private Image buttonSprite;
    private Image hoverSprite;
    private Image clickSprite;

    private boolean focused = false;
    private boolean hovered = false;
    
    
    public Button(int x, int y, int width, int height){
        this.x = x;
        this.y =  y;
        this.width =  width;
        this.height =  height;
    }

    public Button(int x, int y, int width, int height, Color color){
        this(x, y, width, height);
        this.buttonColor = color;
    }
    public Button(int x, int y, int width, int height, String spriteFilepath){
        this(x, y, width, height);
        try {
            BufferedImage spritesheet = ImageIO.read(new File(spriteFilepath));
            int subHeight = spritesheet.getHeight()/3;
            buttonSprite = spritesheet.getSubimage(0, 0, spritesheet.getWidth(), subHeight);
            hoverSprite = spritesheet.getSubimage(0, subHeight, spritesheet.getWidth(), subHeight);
            clickSprite = spritesheet.getSubimage(0, subHeight*2, spritesheet.getWidth(), subHeight);
        } catch (Exception e){
            System.err.println("Button sprite not found: " + spriteFilepath);
        }
    }
    
    public void setOnClickFunction(ButtonFunction func){
        this.func = func;
    }

    public void update(){
        Input input = Input.globalInput;

        if (pointIntersects(input.mouseX(), input.mouseY())) {
            hovered = true;
            if (input.mouseClicked()){
                focused = true;
            }
            if (input.mouseReleased() && focused){
                focused = false;
                func.run();
            }
        } else {
            hovered = false;
            if (input.mouseReleased() && focused){
                focused = false;
            }
        }
        
        
    }

    public void draw (Graphics g){
        if (buttonSprite != null){
            if (focused){
                g.drawImage(clickSprite, x, y, width, height, null);
            } else if (hovered) {
                g.drawImage(hoverSprite, x, y, width, height, null);
            } else {
                g.drawImage(buttonSprite, x, y, width, height, null);
            }
        } else {
            g.setColor(buttonColor);
            g.fillRect(x, y, width, height);
        }
    }

    private boolean pointIntersects(int _x, int _y){
        return (x <= _x && _x <= x+width) && (y <= _y && _y <= y+height);
    }
}
