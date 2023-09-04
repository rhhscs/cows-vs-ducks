package src.java.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import src.java.Drawable;
import src.java.Utilities.ScoreManager;

public class CheerioManager implements Drawable{
    private static CheerioManager globalCheerioManager = new CheerioManager();
    public static CheerioManager getGlobalCheerios() {
        return globalCheerioManager;
    }

    private int value = 400;
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 60);
    private int cheerioPointCounter = 0;

    private CheerioManager(){}

    public void addCheerios(int num) {
        value += num;
    }
    public void addCheerios(){
        value++;
    }

    public void spendCheerios(int num) {
        value -= num;
        cheerioPointCounter += num;
        
        while (cheerioPointCounter > 100) {
            cheerioPointCounter -= 100;
            ScoreManager.scoreManager.addCurPoints(10);
        }
    }

    public int getCheerios(){
        return value;
    }
    
    public void reset(){
        value = 400;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(20, 20, 250, 80);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(value +  "", 80, 85);
    }
    
}
