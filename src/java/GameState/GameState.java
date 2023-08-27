package src.java.GameState;

import src.java.State;
import java.awt.Graphics;

public class GameState extends State{
    private PlayingField lawn;
    private UI gooey;

    @Override
    public void start() {
        this.lawn = new PlayingField(300, 140, 7, 5);
        gooey = new UI();
        gooey.init();
        gooey.docs.init(lawn);
    }

    @Override
    public void update() {
        this.lawn.update();
        this.gooey.update();
    }

    @Override
    public void draw(Graphics g) {
        this.lawn.draw(g);
        this.gooey.draw(g);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
}
