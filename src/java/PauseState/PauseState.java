package src.java.PauseState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.State;
import src.java.Components.Button;
import src.java.GameState.GameState;
import src.java.LoadingState.LoadingState;
import src.java.Utilities.ResolutionManager;
import src.java.LoseState.LoseState;

public class PauseState extends State{
    Button resumeButton = new Button(ResolutionManager.WIDTH/2 - 200, ResolutionManager.HEIGHT/2 - 200, 400, 120, "src/img/button/resume.png");
    Button restartButton = new Button(ResolutionManager.WIDTH/2 - 200, ResolutionManager.HEIGHT/2 - 60, 400, 120, "src/img/button/restart.png");
    Button endGameButton = new Button(ResolutionManager.WIDTH/2 - 200, ResolutionManager.HEIGHT/2 + 80, 400, 120, "src/img/button/end_game.png");
    @Override
    public void start() {
        resumeButton.setOnClickFunction(()->{popState = true;});
        restartButton.setOnClickFunction(()->{resetStates = new LoadingState();});
        endGameButton.setOnClickFunction(()->{nextState = new LoseState();});
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
