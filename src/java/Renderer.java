package src.java;

import java.awt.*;
import javax.swing.*;

import src.java.Utilities.ResolutionManager;
import src.java.Utilities.Input;

public class Renderer {
    JFrame frame;
    GraphicsPanel canvas;
    Game game;
    Input input;
    int fullscreen = 2;
    ResolutionManager resolution = ResolutionManager.getGlobalResolutionManager();


    public Renderer(){
        frame = new JFrame("Cows Vs. Ducks");
        canvas = new GraphicsPanel();
        input = Input.globalInput;
    }

    public void init(){
        frame.setSize(resolution.WINDOWWIDTH,resolution.WINDOWHEIGHT);
        //frame.setMinimumSize(new Dimension(resolution.WINDOWWIDTH,resolution.WINDOWHEIGHT));
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas.addKeyListener(input.getKeyboard());
        canvas.addMouseListener(input.getMouse());
        canvas.addMouseMotionListener(input.getMouse());
        canvas.addMouseWheelListener(input.getMouse());

        frame.add(canvas); 
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    public void close(){
        frame.dispose();
    }

    public void toggleFullscreen(){
        fullscreen = (fullscreen+1)%4;
                
        if (fullscreen == 0){
            frame.dispose();
            //ScreenConsts.FULLSCREEN_DIMS();
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            frame.dispose();
            frame.setUndecorated(false);
            frame.setVisible(true);
            frame.setExtendedState(JFrame.NORMAL);
            switch (fullscreen){
                case 1:
                    resolution.SMALL_DIMS();
                break; case 2:
                    resolution.MED_DIMS();
                break; case 3:
                    resolution.LARGE_DIMS();
                break; 
            }
            frame.setSize(resolution.WINDOWWIDTH,resolution.WINDOWHEIGHT);
            frame.setLocationRelativeTo(null);
        }
    }

    public void repaint(){
        canvas.repaint();
    }

    public void setGame(Game _game){
        game = _game;
    }

    private class GraphicsPanel extends JPanel {
        GraphicsPanel(){
            setFocusable(true);
            requestFocusInWindow();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.translate(0, 0);
            game.draw(g);
        }
    }
}
