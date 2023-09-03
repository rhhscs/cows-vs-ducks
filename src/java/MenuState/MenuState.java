package src.java.MenuState;

import src.java.Consts;
import src.java.State;
import src.java.Components.Button;
import src.java.GameState.GameState;
import src.java.Utilities.ResolutionManager;
import src.java.Utilities.Score;
import src.java.Utilities.ScoreManager;

import java.awt.Graphics;
import java.util.List;

public class MenuState extends State{
    // Buttons
    private Button startButton = new Button(100, ResolutionManager.HEIGHT/2 - 100, 700, 80);
    private Button scoresButton = new Button(100, ResolutionManager.HEIGHT/2, 600, 80);
    private Button bookButton = new Button(100 + 620, ResolutionManager.HEIGHT/2, 80, 80);

    // Scores Banner
    private boolean showScores = true;
    private ScoreManager scoreManager = new ScoreManager(null);
    @Override
    public void start() {
        startButton.setOnClickFunction(() -> {nextState = new GameState();});
        scoresButton.setOnClickFunction(() -> {showScores = !showScores;});
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
        bookButton.draw(g);

        /*if(showScores){
            g.fillRect(Consts.SCORES_X, Consts.SCORES_Y, Consts.SCORES_WIDTH, Consts.SCORES_HEIGHT);
            List<Score> topScores = scoreManager.getTopScores(5);
            for(int i = 0; i < topScores.size(); i++){
                // Fix this part later. I dont like that i have to call getScores() a lot. If you guys are ok with importing Score.java into this file that would be nice.
                g.drawString(topScores.get(i).getName() + ": " + topScores.get(i).getScore(), Consts.SCORES_X + 100, Consts.SCORES_Y + 100 + (75*i));
            }
        }*/
    }
    
    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }


    @Override
    public void stop() {}

    
}
