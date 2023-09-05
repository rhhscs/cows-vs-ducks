package src.java.LoseState;

import java.awt.Color;
import java.awt.Graphics;

import com.sun.glass.events.KeyEvent;

import src.java.Consts;
import src.java.State;
import src.java.Utilities.Input;
import src.java.Utilities.ResolutionManager;
import src.java.Utilities.ScoreManager;
import src.java.Utilities.Text;
import src.java.Components.Button;
import src.java.MenuState.MenuState;


public class LoseState extends State {
    private String name = "";
    private Button submitButton = new Button(ResolutionManager.WIDTH/2 - 100, ResolutionManager.HEIGHT/2 + 30, 200, 60);

    private Text nameText = new Text("", Consts.SCORES_FONT, Consts.SCORES_COLOR, 0, 0);
    private Text scoreText = new Text("", Consts.SCORES_FONT, Consts.SCORES_COLOR, 0, 0);
    private Text waveText = new Text("", Consts.SCORES_FONT, Consts.SCORES_COLOR, 0, 0);
    private Text titleText = new Text("Game Over", Consts.TITLE_FONT, Consts.SCORES_COLOR, 0, 0);

    @Override
    public void draw(Graphics g) {
        // background
        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(400, 300, ResolutionManager.WIDTH - 800, ResolutionManager.HEIGHT - 600);

        submitButton.draw(g);
        g.setColor(Consts.SCORES_COLOR);

        titleText.draw(g, ResolutionManager.WIDTH / 2, ResolutionManager.HEIGHT / 2 - 200);

        scoreText.setText("Score: " + ScoreManager.scoreManager.getCurPoints());
        scoreText.draw(g, ResolutionManager.WIDTH / 2, ResolutionManager.HEIGHT / 2 - 130);

        waveText.setText("Level: " + ScoreManager.scoreManager.getCurLevel() + "." + ScoreManager.scoreManager.getCurWave());
        waveText.draw(g, ResolutionManager.WIDTH / 2, ResolutionManager.HEIGHT / 2 - 100);

        nameText.setText(name);
        nameText.draw(g, ResolutionManager.WIDTH / 2, ResolutionManager.HEIGHT / 2 - 15);
    }

    @Override
    public void update() {
        submitButton.update();

        if (Input.globalInput.keyIsTapped(KeyEvent.VK_BACKSPACE)) {
            name = name.substring(0, name.length() - 1);
        } else {
            for (int keyCode = KeyEvent.VK_0; keyCode <= KeyEvent.VK_9; keyCode++) {
                if (Input.globalInput.keyIsTapped(keyCode))
                    name += (char) keyCode;
            }

            for (int keyCode = KeyEvent.VK_A; keyCode <= KeyEvent.VK_Z; keyCode++) {
                if (Input.globalInput.keyIsTapped(keyCode))
                    name += (char) keyCode;
            }

            if (Input.globalInput.keyIsTapped(KeyEvent.VK_SPACE)) {
                name += " ";
            }
        }
    }

    @Override
    public void start() {
        submitButton.setOnClickFunction(() -> {
            ScoreManager.scoreManager.setCurName(name);
            ScoreManager.scoreManager.addCurScore();
            ScoreManager.scoreManager.save();
            resetStates = new MenuState();
        });
        this.name = ScoreManager.scoreManager.getCurName();
    }

    @Override
    public void stop() {
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }
    
}
