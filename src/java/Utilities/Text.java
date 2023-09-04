
package src.java.Utilities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.font.FontRenderContext;

public class Text {
    private String text;
    private Font font;
    private Color color;
    private int fontSize;
    private int centerX;
    private int x;
    private int y;
    private int width;
    private int height;
    public Text(String text, Font font, Color color, int centerX, int y) {
        this.text = text;
        this.font = font;
        this.color = color;
        this.fontSize = this.font.getSize();
        this.centerX = centerX;
        this.y = y;
        this.width = this.getTextWidth(); 
        this.x = (this.centerX - (this.width / 2));
        this.height = this.getTextHeight(); 
    }
//-----------------------------------------------------------------------------
// Getters and Setters
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public String getText(){
        return this.text;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setCenterX(int centerX){
        this.centerX = centerX;
        this.x = centerX - this.width/2;
    }
    public void setText(String newText){
        this.text = newText;
        this.width = this.getTextWidth(); 
        this.x = (this.centerX - (this.width / 2));
    }
 //------------------------------------------------------------------------------
 // Methods
    // Using AffineTransform and FontRenderContext to get the dimensions of the font
    private int getTextWidth() {
        AffineTransform affineTransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affineTransform,true,true);     
        int textWidth = (int)(this.font.getStringBounds(this.text, frc).getWidth());
        return textWidth;
    }
    private int getTextHeight() {
        AffineTransform affineTransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affineTransform,true,true);     
        int textHeight = (int)(this.font.getStringBounds(this.text, frc).getHeight());
        return textHeight;
    }
    public void draw(Graphics graphics) {
        ((Graphics2D)graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setFont(this.font); 
        graphics.setColor(this.color);
        graphics.drawString(this.text, this.x, this.y + this.fontSize);
    }
    public void draw(Graphics graphics, int centerX, int y) {
        ((Graphics2D)graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setFont(this.font); 
        graphics.setColor(this.color);
        graphics.drawString(this.text, centerX - this.width/2, y + this.fontSize);
    }
}
