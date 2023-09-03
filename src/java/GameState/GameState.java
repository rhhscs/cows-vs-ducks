package src.java.GameState;

import src.java.State;
import src.java.GameState.Cows.Cow;
import src.java.PauseState.PauseState;
import src.java.Utilities.Input;

import java.awt.Graphics;

import java.awt.event.KeyEvent;

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

        // GUI overlay initialization
        this.gooey = new UI();
        this.gooey.docs.init(lawn);
        // GUI button initialization
        this.gooey.pauseButton.setOnClickFunction(() -> {appendState = new PauseState();});

        this.ducks.addDuck(0, new Duck(1, 10, 10, 50, 100, null, lawn, 0, AI.MELEE_DUCK_AI));
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
                    new Duck(1, 10, 10, 50, 100, null, lawn, lawn.getCellTileCoordinate(Input.globalInput.mouseX(), Input.globalInput.mouseY()).y, AI.MELEE_DUCK_AI));
            }
        }
        if (Input.globalInput.keyIsTapped(KeyEvent.VK_EQUALS)){
            CheerioManager.getGlobalCheerios().addCheerios(200);
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
    public void pause() {}

    @Override
    public void stop() {
    }

    @Override
    public String toString() {
        return "GameState:" + Integer.toHexString(hashCode());
    }
}
