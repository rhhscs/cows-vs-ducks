package src.java;

import java.util.Iterator;
import java.util.Stack;
import java.awt.Graphics;

//TODO: implement
public class StateMachine {

    private Stack<State> active = new Stack<State>();
    
    public void init(State initialState){
        initialState.start();
        active.push(initialState);
    }

    public void run(){
        active.peek().update();
    }

    public void draw(Graphics g){
        Iterator<State> stateIterator = active.iterator();
        while (stateIterator.hasNext()){
            stateIterator.next().draw(g);
        }
    }

    public void changeActiveState(State newState){
        active.pop().stop();
        newState.start();
        active.push(newState);
    }

    public void popActiveState(){
        if (active.size() != 1){
            active.pop().stop();
        }
    }
}
