package src.java.GameState;

import java.awt.Graphics;

import src.java.Components.Button;
import src.java.Utilities.ResolutionManager;

public class UI {
    DocumentManager docs = new DocumentManager();
    CheerioManager cheerios = CheerioManager.getGlobalCheerios();
    WaveManager waves = new WaveManager();
    Button pauseButton = new Button(ResolutionManager.WIDTH-120, 20, 80, 80);
    Button bookButton = new Button(ResolutionManager.WIDTH-220, 20, 80, 80);

    public void update(){
        docs.update();

        
    }

    public void draw(Graphics g){
        docs.draw(g);
        cheerios.draw(g);
        waves.draw(g);
        pauseButton.draw(g);
        bookButton.draw(g);
    }
}
