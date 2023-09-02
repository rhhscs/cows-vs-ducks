package src.java.PauseState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.State;
import src.java.Components.Button;
import src.java.Utilities.ResolutionManager;

public class PauseState extends State{
    Button resumeButton = new Button(ResolutionManager.WIDTH/2 - 200, ResolutionManager.HEIGHT/2 - 150, 400, 80);
    //Button restartButton = new Button();
    //Button endGameButton = new Button();
    @Override
    public void start() {
        resumeButton.setOnClickFunction(()->{popState = true;});
    }

    @Override
    public void update() {
        resumeButton.update();
    }

    @Override public void pause(){};

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(50, 50, 70, 100));
        g.fillRect(0, 0, ResolutionManager.WIDTH, ResolutionManager.HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRoundRect(ResolutionManager.WIDTH/2 - 280, ResolutionManager.HEIGHT/2 - 300, 560, 600, 10, 10);
        resumeButton.draw(g);
    }

    @Override
    public void stop() {

    }
    
}
