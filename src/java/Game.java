package src.java;

import java.awt.Graphics;
import src.java.MenuState.MenuState;

public class Game {

    private boolean running = false;
    private Renderer renderer;
    private StateMachine stateManager = new StateMachine();

    public void setup(){
        stateManager.init(new MenuState());
        running = true;
    }

    public void run(){
        while(running){
            stateManager.run();
            renderer.repaint();
            try{Thread.sleep(20);} catch(Exception e){}
        }
        renderer.close();
    }

    public void draw(Graphics g){
        stateManager.draw(g);
    }

    public void attachScreen(Renderer _renderer){
        renderer = _renderer;
        renderer.setGame(this);
    }

    public void stop(){
        running = false;
    }
}
