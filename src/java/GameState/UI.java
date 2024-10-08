package src.java.GameState;

import java.awt.Graphics;

import src.java.Components.Button;
import src.java.Utilities.ResolutionManager;

public class UI {
    DocumentManager docs = new DocumentManager();
    CheerioManager cheerios = CheerioManager.getGlobalCheerios();
    Button pauseButton = new Button(ResolutionManager.WIDTH-120, 20, 80, 80, "src/img/button/pause.png");

    public void update(){
        docs.update();
        pauseButton.update();
        
    }

    public void draw(Graphics g){
        docs.draw(g);
        cheerios.draw(g);
        pauseButton.draw(g);
    }
}
