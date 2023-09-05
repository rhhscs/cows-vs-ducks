package src.java.GameState;

import java.awt.Graphics;

import src.java.Consts;

public class GargantuanDuck extends Duck{

    public GargantuanDuck(PlayingField lawn, int laneIndex, AI ai) {
        super(1, 100, 20, 120, 600, lawn, laneIndex, ai, Sprite.BASIC_DUCK, Consts.BUFF_DUCK);
    }

    @Override
    public void draw(Graphics g){
        getSprite().draw(g, this.getX(), this.getY()-this.getWidth(), this.getWidth()*2, this.getHeight()*2);
    }

    @Override
    public GargantuanDuck clone(){
        return new GargantuanDuck(getPlayingField(), getLane(), getAI());
    }

    @Override
    protected void move(){
        this.move(Math.min(-this.moveSpeed.getValue(), 0), 0);
    }
}
