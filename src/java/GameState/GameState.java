package src.java.GameState;

import src.java.State;
import src.java.GameState.Cows.Cow;

import java.awt.Graphics;

public class GameState extends State {
    private PlayingField lawn;
    private UI gooey;
    private DuckManager ducks;

    @Override
    public void start() {
        this.lawn = new PlayingField();
        this.ducks = new DuckManager();
        Cow.setStaticDuckManager(ducks);
        ProjectileManager.projectileManager.setDuckManager(ducks);

        this.gooey = new UI();
        this.gooey.init();
        this.gooey.docs.init(lawn);

        this.ducks.addDuck(0, new Duck(1, 10, 10, 50, 100, null, lawn, 0, AI.MELEE_DUCK_AI));
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
