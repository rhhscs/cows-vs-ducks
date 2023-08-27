package src.java.Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
    public Button(int x, int y, int width, int height, String SpriteFilepath){
        this(x, y, width, height);
    }
    
    public void setOnClickFunction(ButtonFunction func){
        this.func = func;
    }

    public void update(){
        Input input = Input.globalInput;

        if (pointIntersects(input.mouseX(), input.mouseY())) {
            if (input.mouseClicked()){
                focused = true;
            }
            if (input.mouseReleased() && focused){
                focused = false;
                func.run();
            }
        }
        
        
    }

    public void draw (Graphics g){
        ResolutionManager res = ResolutionManager.getGlobalResolutionManager();
        if (buttonSprite != null){

        } else {
            g.setColor(buttonColor);
            g.fillRect(res.s(x), res.s(y), res.s(width), res.s(height));
        }
    }

    private boolean pointIntersects(int _x, int _y){
        ResolutionManager res = ResolutionManager.getGlobalResolutionManager();
        return (res.s(x) <= _x && _x <= res.s(x+width)) && (res.s(y) <= _y && _y <= res.s(y+height));
    }
}
