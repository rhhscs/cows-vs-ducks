package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.Drawable;

public class WaveManager implements Drawable {
    private int nextWaveTimer = 500;
    private int nextWave = 500; // i need better variable names ;;
    private DuckManager duckManager;

    public void setDuckManager(DuckManager _duckManager) {
        duckManager = _duckManager;
    }

    public void spawn(){
        if (duckManager == null){
            return;
        }

        if (Math.random() < 0.015){
            duckManager.addDuck((int)(Math.random()*5), null);
        }
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(390, 20, 400, 80);
    }
}
