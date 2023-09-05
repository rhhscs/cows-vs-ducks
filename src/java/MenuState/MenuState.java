package src.java.MenuState;

import src.java.Consts;
import src.java.State;
import src.java.Components.Button;
import src.java.LoadingState.LoadingState;
import src.java.Utilities.ResolutionManager;
import src.java.Utilities.Score;
import src.java.Utilities.ScoreManager;
import src.java.Utilities.Text;

import java.awt.Graphics;
import java.util.List;

public class MenuState extends State {
    // Buttons
    private Button startButton = new Button(100, ResolutionManager.HEIGHT / 2 - 100, 700, 80);
    private Button scoresButton = new Button(100, ResolutionManager.HEIGHT / 2, 700, 80);

    // Scores Banner
    private boolean showScores = false;
    private Text scoresTitle = new Text("HIGH SCORES", Consts.TITLE_FONT, Consts.SCORES_COLOR,
            Consts.SCORES_X + (Consts.SCORES_WIDTH / 2), Consts.SCORES_Y + 25);
    private Text score = new Text("", Consts.SCORES_FONT, Consts.SCORES_COLOR,
            Consts.SCORES_X + (Consts.SCORES_WIDTH / 2), Consts.SCORES_Y + 25);

    @Override
    public void start() {
        ScoreManager.scoreManager.reset();
        ScoreManager.scoreManager.load();
        startButton.setOnClickFunction(() -> {
            nextState = new LoadingState();
        });
        scoresButton.setOnClickFunction(() -> {
            showScores = !showScores;
        });
    }

    @Override
    public void update() {
        startButton.update();
        scoresButton.update();
    }

    @Override
    public void draw(Graphics g) {
        startButton.draw(g);
        scoresButton.draw(g);
        if (showScores) {
            g.fillRect(Consts.SCORES_X, Consts.SCORES_Y, Consts.SCORES_WIDTH, Consts.SCORES_HEIGHT);
            g.setColor(Consts.SCORES_COLOR);
            scoresTitle.draw(g);
            List<Score> topScores = ScoreManager.scoreManager.getTopScores(10);
            for (int i = 0; i < topScores.size(); i++) {
                score.setText((i + 1) + ". " + topScores.get(i).getName() + ": " + topScores.get(i).getScore());
                score.draw(g, Consts.SCORES_X + 125, Consts.SCORES_Y + 100 + (75 * i));
            }
        }
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void stop() {
    }
}
