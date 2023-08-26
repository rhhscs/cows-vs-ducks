package src.java;

import java.awt.*;
import javax.swing.*;

public class Renderer {
    JFrame frame;
    GraphicsPanel canvas;
    Game game;
    //Input input
    int fullscreen = 2;


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
                    frame.setSize(Consts.WINDOWWIDTH,Consts.WINDOWHEIGHT);
                
                case 2:
                    frame.setSize(Consts.WINDOWWIDTH,Consts.WINDOWHEIGHT);
                
                case 3:
                    frame.setSize(Consts.WINDOWWIDTH,Consts.WINDOWHEIGHT);
            }
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
            g.translate(0, 0);
            game.draw(g);
            super.paintComponent(g);
        }
    }
}
