package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

import src.java.Drawable;
import src.java.Updatable;

public class WaveManager implements Drawable, Updatable{

    public static final int WAVE_DURATION = 2000;
    private final int barWidth = 1000;
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 60);

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


    private final Duck[] ducks = {Duck.BASIC_DUCK, Duck.DUCK_WITH_BREAD, Duck.DUCK_WITH_KNIFE, Duck.DUCK_WITH_CEREAL, Duck.GARGANTUAR_DUCK};
    private final int[][] spawnWeights = {
        {100, 0, 0, 0, 0},
        {75, 25, 0, 0, 0},
        {50, 25, 25, 0, 0},
        {35, 25, 25, 15, 0},
        {25, 25, 25, 20, 5}
    };
    
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

    public Duck generateDuck(int laneIndex){
        int rng = (int)(Math.random() * 100) + 1;
        int startWeight = 0;
        for(int i=0; i<this.ducks.length; i++){
            int currentWeight = this.spawnWeights[Math.min(4, this.currentLevel-1)][i];
            if(startWeight <= rng && rng <= startWeight + currentWeight){
                this.ducks[i].setLaneIndex(laneIndex);
                return this.ducks[i].clone();
            }
            startWeight += currentWeight;
        }
        return this.ducks[0];
    }

    @Override
    public void update(){

        // if all waves are done, create a new level
        if(this.currentWave > this.waveCount){
            this.currentLevel ++;
            this.currentWave = 0;
            this.waveCount = Math.min((int)Math.ceil((double)this.currentLevel / 1.5), 4);
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
            this.duckManager.addDuck(laneIndex, generateDuck(laneIndex));
            this.delay = (int)(Math.random() * 100);
            this.duckCount ++;
        }else if(this.delay > 0){
            this.delay --;
        }

        // spawn the passively spawning ducks
        if(this.counter % Math.max(1, WaveManager.WAVE_DURATION / (this.currentLevel * 3)) == 0){
            int laneIndex = (int) (Math.random() * 5);
            this.duckManager.addDuck(laneIndex, generateDuck(laneIndex));
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

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(this.currentLevel + "." + this.currentWave , 390, 80);

    }

}
