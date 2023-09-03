package src.java.LoadingState;

import java.awt.Font;
import java.awt.Graphics;

import src.java.State;
import src.java.GameState.GameState;
import src.java.Utilities.ResolutionManager;

public class LoadingState extends State{

    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 100);

    @Override
    public void update() {}

    @Override
    public void start() {
        nextState = new GameState();
    }

    @Override
    public void stop() {}

    @Override
    public void pause() {}
    
        @Override
    public void draw(Graphics g) {
        g.setFont(font);
        g.drawString("Loading...", ResolutionManager.WIDTH/2 - 300, ResolutionManager.HEIGHT/2 - 60);
        
    }
}
