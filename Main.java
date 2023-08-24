import src.java.Game;
import src.java.Renderer;

public class Main {
    public static void main(String[] args){
        Game game = new Game();
        Renderer renderer = new Renderer();
        game.attachScreen(renderer);
        renderer.init();
        game.setup();
        game.run();
    }
}