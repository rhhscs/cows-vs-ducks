package src.java.MenuState;

import src.java.State;
import src.java.Components.Button;
import java.awt.Graphics;

public class MenuState extends State{
    private Button startButton = new Button();
    private Button bookButton;
    private Button scoresButton;

    @Override
    public void start() {
        startButton.setOnClickFunction();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void draw(Graphics g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
    
}
