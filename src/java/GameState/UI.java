package src.java.GameState;

import java.awt.Graphics;

import src.java.Components.Button;
import src.java.Utilities.ResolutionManager;

public class UI {
    DocumentManager docs = new DocumentManager();
    Button pauseButton = new Button(ResolutionManager.WIDTH-120, 20, 80, 80);
    Button bookButton = new Button(ResolutionManager.WIDTH-220, 20, 80, 80);
    
    public void init(){
        
    }

    public void update(){
        docs.update();
    }

    public void draw(Graphics g){
        docs.draw(g);
        pauseButton.draw(g);
        bookButton.draw(g);
    }
}
