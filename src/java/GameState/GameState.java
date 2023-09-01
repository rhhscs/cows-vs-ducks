package src.java.GameState;

import src.java.Consts;
import src.java.State;
import src.java.GameState.Cows.Cow;

import java.awt.Graphics;

public class GameState extends State{
    private PlayingField lawn;
    private UI gooey;
    private DuckManager ducks;

    @Override
    public void start() {
        this.lawn = new PlayingField(450, 140, Consts.NUM_COLUMNS, Consts.NUM_LANES);
        this.ducks = new DuckManager(450, 140, Consts.NUM_COLUMNS, Consts.NUM_LANES);
        Cow.setStaticDuckManager(ducks);
        ProjectileManager.projectileManager.setDuckManager(ducks);

        this.gooey = new UI();
        this.gooey.init();
        this.gooey.docs.init(lawn);
        
        this.ducks.addDuck(0, new Duck(0, 30, 60, 60, 1, 10, 50, 100, null, lawn, 0));
    }

    @Override
    public void update() {
        this.lawn.update();
        this.gooey.update();
        this.ducks.update();
        ProjectileManager.projectileManager.update();
    }

    @Override
    public void draw(Graphics g) {
        this.lawn.draw(g);
        this.ducks.draw(g);
        ProjectileManager.projectileManager.draw(g);
        this.gooey.draw(g);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

    @Override
    public String toString() {
        return "GameState";
    }
}
