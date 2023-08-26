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
        //startButton.setOnClickFunction();
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(0, 0, 0, 0);
        g.drawRect(20, 20, 50, 50);
        g.drawRect(70, 70, 50, 50);
        g.drawRect(130, 130, 50, 50);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
    
}
