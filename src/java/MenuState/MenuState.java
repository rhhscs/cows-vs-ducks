package src.java.MenuState;

import src.java.State;
import src.java.Components.Button;
import src.java.GameState.GameState;
import src.java.Utilities.ResolutionManager;

import java.awt.Graphics;

public class MenuState extends State{
    private Button startButton = new Button(100, ResolutionManager.HEIGHT/2 - 100, 700, 80);
    private Button scoresButton = new Button(100, ResolutionManager.HEIGHT/2, 600, 80);
    private Button bookButton = new Button(100 + 620, ResolutionManager.HEIGHT/2, 80, 80);

    @Override
    public void start() {
        startButton.setOnClickFunction(() -> {nextState = new GameState();});
    }

    @Override
    public void update() {
        startButton.update();
    }

    @Override
    public void draw(Graphics g) {
        startButton.draw(g);
        scoresButton.draw(g);
        bookButton.draw(g);
    }


    @Override
    public void stop() {}
    
}
