package src.java;

import java.awt.Graphics;
import src.java.MenuState.MenuState;
import src.java.Utilities.Input;

public class Game {

    private boolean running = false;
    private Renderer renderer;
    private StateMachine stateManager = new StateMachine();
    private Input input = Input.globalInput;

    public void setup(){
        stateManager.init(new MenuState());
        running = true;
    }

    public void run(){
        while(running){
            stateManager.run();
            renderer.repaint();
            input.update();
            try{Thread.sleep(2000);} catch(Exception e){}
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
