package src.java;

import java.awt.*;
import javax.swing.*;

public class Renderer {
    JFrame frame;
    GraphicsPanel canvas;
    Game game;
    //Input input
    boolean fullscreen = false;


    public Renderer(){
        frame = new JFrame("Agar.java");
        canvas = new GraphicsPanel();
    }

    public void init(){
        frame.setSize(Consts.WINDOWWIDTH,Consts.WINDOWHEIGHT);
        frame.setMinimumSize(new Dimension(Consts.WINDOWWIDTH,Consts.WINDOWHEIGHT));
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //TODO: input management
        //canvas.addKeyListener(input);
        //canvas.addMouseListener(input);
        //canvas.addMouseMotionListener(input);
        //canvas.addMouseWheelListener(input);

        frame.add(canvas); 
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    public void close(){
        frame.dispose();
    }

    public void toggleFullscreen(){
        fullscreen = !fullscreen;
        if (fullscreen){
            frame.dispose();
            //ScreenConsts.FULLSCREEN_DIMS();
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            frame.dispose();
            //Consts.WINDOWED_DIMS();
            frame.setUndecorated(false);
            frame.setVisible(true);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setSize(Consts.WINDOWWIDTH,Consts.WINDOWHEIGHT);
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
            game.draw(g);
        }
    }
}
