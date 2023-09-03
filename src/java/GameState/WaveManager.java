package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;

import src.java.Drawable;
import src.java.Updatable;

public class WaveManager implements Drawable, Updatable{

    public static final int WAVE_DURATION = 2000;
    private final int barWidth = 1000;

    /**
     * Each level, X, has ceil(X/1.5) waves
     * Waves are equally long
     * Difficulty scales on both level and wave
     */

    private int currentLevel; // current level #
    private int currentWave; // current wave #
    private int waveCount; // number of waves in this level
    private int waveSize; // number of ducks in this wave
    private int duckCount; // number of ducks spawned for this wave so far
    private int counter; // progression through the wave
    private int delay; // delay between ducks spawned on the wave
    
    private DuckManager duckManager;
    private PlayingField lawn;

    public WaveManager(){
        this.currentLevel = 1;
        this.waveCount = 1;
        this.currentWave = 0;
    }

    public void init(DuckManager _duckManager, PlayingField lawn) {
        duckManager = _duckManager;
        this.lawn = lawn;
    }

    @Override
    public void update(){

        // if all waves are done, create a new level
        if(this.currentWave > this.waveCount){
            this.currentLevel ++;
            this.currentWave = 0;
            this.waveCount = (int)Math.ceil((double)this.currentLevel / 1.5);
        }
        
        // if it is time for a new wave
        if(this.counter == WaveManager.WAVE_DURATION){
            this.counter = 0;
            this.currentWave ++;
            this.waveSize =  this.currentLevel * 3 + this.currentWave;
        }

        // spawn the ducks from the wave
        if(this.duckCount < this.waveSize && this.delay == 0){
            int laneIndex = (int) (Math.random() * 5);
            this.duckManager.addDuck(laneIndex, new Duck(1, 10, 10, 50, 100, null, lawn, laneIndex, AI.MELEE_DUCK_AI));
            this.delay = (int)(Math.random() * WaveManager.WAVE_DURATION / this.waveSize / 2);
            this.duckCount ++;
        }else if(this.delay > 0){
            this.delay --;
        }

        // spawn the passively spawning ducks
        if(this.counter % (WaveManager.WAVE_DURATION / (this.currentLevel * 3 + this.currentWave)) == 0){
            int laneIndex = (int) (Math.random() * 5);
            this.duckManager.addDuck(laneIndex, new Duck(1, 10, 10, 50, 100, null, lawn, laneIndex, AI.MELEE_DUCK_AI));
        }

        this.counter ++;

    }

    @Override
    public void draw(Graphics g){

        // draws progress through the level

        int blockWidth = (int)((double)barWidth / (this.waveCount+1));
        for(int i=0; i<=this.waveCount; i++){
            if(i < this.currentWave){
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(390 + blockWidth * i, 20, blockWidth, 80);
            }else if(i == this.currentWave){
                int parition = (int)((double)this.counter / WaveManager.WAVE_DURATION * blockWidth);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(390 + blockWidth * i, 20, parition, 80);
                g.setColor(Color.DARK_GRAY);
                g.fillRect(390 + blockWidth * i + parition, 20, blockWidth - parition, 80);
            }else{
                g.setColor(Color.DARK_GRAY);
                g.fillRect(390 + blockWidth * i, 20, blockWidth, 80);
            }
            if(i != 0){
                g.setColor(Color.RED);
                g.fillRect(390 + blockWidth * i, 20, 5, 80);
            }
        }

    }

}
