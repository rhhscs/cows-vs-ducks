package src.java.GameState;

import java.awt.Graphics;

import src.java.Consts;

public class RubberDuck extends Duck{
    private float movementVariableA = 8;
    private int movementVariableB = 0;

    public RubberDuck(PlayingField lawn, int laneIndex, AI ai) {
        super(1, 0, 1, 1000, 500, lawn, laneIndex, ai, Sprite.RUBBER_DUCK, Consts.RUBBER_DUCK);
    }

    @Override
    public void draw(Graphics g){
        getSprite().update();
        getSprite().draw(g, this.getX(), this.getY()-20, this.getWidth(), this.getHeight());
    }
    
    @Override
    public void update(){
        this.moveSpeed.update();
        this.damage.update();
        this.attackSpeed.update();

        if (this.getState() == State.WALK) {
            if (movementVariableB == 0) {
                this.move(Math.min((int)(-this.moveSpeed.getValue() * movementVariableA), 0), 0);
                movementVariableA-=0.2;
                if (movementVariableA < 0){
                    movementVariableB = 50;
                    movementVariableA = 5.2f;
                }
            } else {
                movementVariableB--;
            }
        }

        if (this.target != null) {
            if (this.target.getAI() == AI.MELEE_COW_AI){
                if (this.containsPoint(target.getX() + (int)(target.getWidth()*(0.6)), getY()+10)){
                    this.target.takeDamage(10);
                    this.takeDamage(600);
                }
            } else if (this.target.isTargetable()){
                this.setState(State.IDLE);
            }
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
