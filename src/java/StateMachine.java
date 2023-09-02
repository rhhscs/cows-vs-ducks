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
        State runningState = active.peek();
        if (runningState.popState){
            popActiveState();
        }
        if (runningState.nextState != null){
            changeActiveState(runningState.nextState);
        } else if (runningState.appendState != null){
            appendActiveState(runningState.appendState);
        } 
        runningState.update();
    }

    public void draw(Graphics g){
        Iterator<State> stateIterator = active.iterator();
        while (stateIterator.hasNext()){
            stateIterator.next().draw(g);
        }
    }

    public void changeActiveState(State newState){
        System.out.println("Changing State: " + active.peek() + " to " + newState);
        active.pop().stop();
        newState.start();
        active.push(newState);
    }

    public void popActiveState(){
        System.out.println("Killing State: " + active.peek());
        if (active.size() != 1){
            active.pop().stop();
        }
    }
    public void appendActiveState(State newState){
        System.out.println("Sleeping State: " + active.peek());
        System.out.println("Appending State: " + newState);
        newState.start();
        active.push(newState);
    }
}
