package src.java.GameState;

import java.awt.Graphics;

public class RubberDuck extends Duck{
    private float movementVariableA = 5;
    private int movementVariableB = 0;

    public RubberDuck(PlayingField lawn, int laneIndex, AI ai) {
        super(1, 0, 1, 1000, 500, lawn, laneIndex, ai, Sprite.RUBBER_DUCK);
    }

    @Override
    public void draw(Graphics g){
        getSprite().draw(g, this.getX(), this.getY()-20, this.getWidth(), this.getHeight());
    }
    
    @Override
    public void update(){
        this.moveSpeed.update();
        this.damage.update();
        this.attackSpeed.update();

        if (this.getState() == State.WALK) {
            if (movementVariableB == 0) {
                this.move((int)(-this.moveSpeed.getValue() * movementVariableA), 0);
                movementVariableA-=0.2;
                if (movementVariableA < 0){
                    movementVariableB = 50;
                    movementVariableA = 5;
                }
            } else {
                movementVariableB--;
            }
        }

        if (this.target != null) {
            if (this.target.getAI() == AI.MELEE_COW_AI){
                this.target.takeDamage(10);
                this.takeDamage(600);
            }
            this.setState(State.IDLE);
        } else {
            this.updateTarget();
            this.setState(State.WALK);
        }

    }

    @Override
    public RubberDuck clone(){
        return new RubberDuck(getPlayingField(), getLane(), getAI());
    }

}
