package src.java.Utilities;

import java.awt.event.*;
import java.util.HashMap;

public class Input {
    public static Input globalInput = new Input();
    private Mouse mouse = new Mouse();
    private Keyboard keyboard = new Keyboard();

    private Input(){}

    public void update(){
        // reset stuff
        keyboard.tappedCode = -1;
        keyboard.tappedChar = 0;

        mouse.tapped = false;
        mouse.released = false;
    }
    
    public char charTapped(){
        return keyboard.tappedChar;
    }
    
    public boolean keyIsTapped(int key){
        return key == keyboard.tappedCode;
    }

    public boolean keyIsDown(int key){
        if (!keyboard.down.containsKey(key)){
            return false;
        }
        return keyboard.down.get(key);
    }

    public boolean mouseIsDown(){
        return mouse.down;
    }

    public boolean mouseClicked(){
        return mouse.tapped;
    }

    public boolean mouseReleased(){
        return mouse.released;
    }

    public int mouseX(){
        return mouse.x;
    }
    public int mouseY(){
        return mouse.y;
    }


    private class Keyboard implements KeyListener {

        int tappedCode = -1;
        char tappedChar = 0;
        HashMap<Integer, Boolean> down = new HashMap<Integer, Boolean>();


        @Override
        public void keyTyped(KeyEvent e) {
            tappedChar = e.getKeyChar();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            down.put(e.getKeyCode(), true);
            tappedCode = e.getKeyCode();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            down.put(e.getKeyCode(), false);
        }

    }

    private class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{
        int x = 0, y = 0;
        double scroll = 0;

        boolean tapped = false;
        boolean released = false;
        boolean down = false;

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                down = true;
                tapped = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == 1) {
                down = false;
                released = true;
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            scroll = e.getPreciseWheelRotation();
        }

        @Override
        public void mouseDragged(MouseEvent e) {}

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }
        @Override
        public void mouseExited(MouseEvent e) {}

    }
    
    public Keyboard getKeyboard(){
        return keyboard;
    }
    public Mouse getMouse(){
        return mouse;
    }
}
