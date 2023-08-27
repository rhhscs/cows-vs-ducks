package src.java.GameState;

import java.awt.Graphics;

public class UI {
    DocumentManager docs = new DocumentManager();
    
    public void init(){
        
    }

    public void update(){
        docs.update();
    }

    public void draw(Graphics g){
        docs.draw(g);
    }
}
