package src.java.GameState;

import src.java.State;
import src.java.GameState.Cows.Cow;
import src.java.PauseState.PauseState;
import src.java.Utilities.Input;
import src.java.Utilities.ResolutionManager;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;

public class GameState extends State {
    private PlayingField lawn;
    private UI gooey;
    private DuckManager ducks;
    private WaveManager waveManager;

    @Override
    public void start() {
        Sprite.init();

        this.lawn = new PlayingField();
        this.ducks = new DuckManager();
        this.waveManager = new WaveManager();
        Cow.init(ducks);
        Duck.init(lawn);
        ProjectileManager.projectileManager.setDuckManager(ducks);
        this.waveManager.init(ducks, lawn);

        // GUI overlay initialization
        this.gooey = new UI();
        this.gooey.docs.init(lawn);
        // GUI button initialization
        this.gooey.pauseButton.setOnClickFunction(() -> {appendState = new PauseState();});

        // resets
        CheerioManager.getGlobalCheerios().reset();
    }

    @Override
    public void update() {
        this.lawn.update();
        this.gooey.update();
        this.ducks.update();
        this.waveManager.update();
        ProjectileManager.projectileManager.update();

        if (Input.globalInput.keyIsTapped(KeyEvent.VK_1)){
            if (lawn.containsPoint(Input.globalInput.mouseX(), Input.globalInput.mouseY())){
                ducks.addDuck(lawn.getCellTileCoordinate(Input.globalInput.mouseX(), Input.globalInput.mouseY()).y, 
                Duck.BASIC_DUCK.clone());
            }
        } else if (Input.globalInput.keyIsTapped(KeyEvent.VK_2)){
            if (lawn.containsPoint(Input.globalInput.mouseX(), Input.globalInput.mouseY())){
                ducks.addDuck(lawn.getCellTileCoordinate(Input.globalInput.mouseX(), Input.globalInput.mouseY()).y, 
                Duck.DUCK_WITH_BREAD.clone());
            }
        } else if (Input.globalInput.keyIsTapped(KeyEvent.VK_4)){
            if (lawn.containsPoint(Input.globalInput.mouseX(), Input.globalInput.mouseY())){
                ducks.addDuck(lawn.getCellTileCoordinate(Input.globalInput.mouseX(), Input.globalInput.mouseY()).y, 
                Duck.RUBBER_DUCK.clone());
            }
        }
        if (Input.globalInput.keyIsTapped(KeyEvent.VK_EQUALS)){
            CheerioManager.getGlobalCheerios().addCheerios(200);
        }
    }

    private Color bgColor = new Color(178, 243, 190);
    @Override
    public void draw(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, ResolutionManager.WIDTH, ResolutionManager.HEIGHT);
        this.lawn.draw(g);
        this.ducks.draw(g);
        ProjectileManager.projectileManager.draw(g);
        this.waveManager.draw(g);
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
