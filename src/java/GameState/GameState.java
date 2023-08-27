package src.java.GameState;

import src.java.State;
import java.awt.Graphics;

public class GameState extends State{
    private PlayingField lawn;

    @Override
    public void start() {
        this.lawn = new PlayingField(300, 140, 7, 5);
    }

    @Override
    public void update() {
        this.lawn.update();
    }

    @Override
    public void draw(Graphics g) {
        this.lawn.draw(g);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
}
