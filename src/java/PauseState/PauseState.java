package src.java.PauseState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.State;
import src.java.Components.Button;
import src.java.GameState.GameState;
import src.java.LoadingState.LoadingState;
import src.java.Utilities.ResolutionManager;

public class PauseState extends State{
    Button resumeButton = new Button(ResolutionManager.WIDTH/2 - 200, ResolutionManager.HEIGHT/2 - 150, 400, 80);
    Button restartButton = new Button(ResolutionManager.WIDTH/2 - 200, ResolutionManager.HEIGHT/2 - 50, 400, 80, Color.ORANGE);
    Button endGameButton = new Button(ResolutionManager.WIDTH/2 - 200, ResolutionManager.HEIGHT/2 - 50, 400, 80, Color.RED);
    @Override
    public void start() {
        resumeButton.setOnClickFunction(()->{popState = true;});
        restartButton.setOnClickFunction(()->{resetStates = new LoadingState();});
        endGameButton.setOnClickFunction(()->{nextState = null /* TODO: new game over state here */;});
    }

    @Override
    public void update() {
        resumeButton.update();
        restartButton.update();
        endGameButton.update();
    }

    @Override public void pause(){};

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(50, 50, 70, 100));
        g.fillRect(0, 0, ResolutionManager.WIDTH, ResolutionManager.HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRoundRect(ResolutionManager.WIDTH/2 - 280, ResolutionManager.HEIGHT/2 - 300, 560, 600, 10, 10);
        resumeButton.draw(g);
        restartButton.draw(g);
        endGameButton.draw(g);
    }

    @Override
    public void stop() {

    }

    @Override
    public String toString(){
        return "PauseState:" + Integer.toHexString(hashCode());
    }
    
}
