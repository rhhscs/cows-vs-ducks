package src.java;

public class Game {

    private boolean running = false;
    private Renderer renderer;
    // private StateMachine stateManager = new StateMachine();

    public void setup(){
        // TODO: initialize and setup states
        running = true;
    }

    public void run(){
        while(running){
            
            // TODO: make the game!!
            
            //renderer.repaint();
            try{Thread.sleep(20);} catch(Exception e){}
        }
        renderer.close();
    }

    public void attachScreen(Renderer _renderer){
        renderer = _renderer;
    }

    public void stop(){
        running = false;
    }
}
