package src.java.GameState;

import src.java.Consts;
import src.java.State;
import src.java.GameState.Cows.Cow;
import src.java.PauseState.PauseState;
import src.java.Utilities.Input;

import java.awt.Graphics;


import java.awt.event.KeyEvent;

public class GameState extends State{
    private PlayingField lawn;
    private UI gooey;
    private DuckManager ducks;

    @Override
    public void start() {
        // game object initialization
        this.lawn = new PlayingField(450, 140, Consts.NUM_COLUMNS, Consts.NUM_LANES);
        this.ducks = new DuckManager(450, 140, Consts.NUM_COLUMNS, Consts.NUM_LANES);
        Cow.setStaticDuckManager(ducks);
        ProjectileManager.projectileManager.setDuckManager(ducks);

        // GUI overlay initialization
        this.gooey = new UI();
        this.gooey.docs.init(lawn);
        // GUI button initialization
        this.gooey.bookButton.setOnClickFunction(() -> {appendState = new PauseState();});
        
        this.ducks.addDuck(0, new Duck(0, 30, 60, 60, 1, 100, 10, null, lawn, 0));

        // resets
        CheerioManager.getGlobalCheerios().reset();
    }

    @Override
    public void update() {
        this.lawn.update();
        this.gooey.update();
        this.ducks.update();
        ProjectileManager.projectileManager.update();


        if (Input.globalInput.keyIsTapped(KeyEvent.VK_1)){
            if (lawn.containsPoint(Input.globalInput.mouseX(), Input.globalInput.mouseY())){
                ducks.addDuck(lawn.getCellTileCoordinate(Input.globalInput.mouseX(), Input.globalInput.mouseY()).y, 
                    new Duck(0, 0, 60, 60, 1, 100, 10, null, lawn, lawn.getCellTileCoordinate(Input.globalInput.mouseX(), Input.globalInput.mouseY()).y
                ));
            }
        }
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
        return "GameState:" + this.hashCode();
    }
}
