package src.java.GameState;

import java.awt.image.BufferedImage;

import java.awt.Image;

public class CowSprite extends Sprite {
    public static final String ATTACK_CYCLE = "attack";
    public static final String IDLE_CYCLE = "idle";
    public static final String FILE_THUMBNAIL = "file";
    
    public CowSprite(int ticksPerFrame) {
        this(DEFAULT_SPRITE_TILE_SIZE, DEFAULT_SPRITE_TILE_SIZE, ticksPerFrame);
    }

    public CowSprite(int width, int height, int ticksPerFrame) {
        super(width, height, ticksPerFrame);
    }

    public CowSprite(String folder, int attackFrames, int idleFrames, int ticksPerFrame) {
        this(folder, DEFAULT_SPRITE_TILE_SIZE, DEFAULT_SPRITE_TILE_SIZE, attackFrames, idleFrames, ticksPerFrame);
    }

    public CowSprite(String folder, int width, int height, int attackFrames, int idleFrames, int ticksPerFrame) {
        super(width, height, ticksPerFrame);
        this.setCycle(ATTACK_CYCLE, folder + ATTACK_CYCLE + Sprite.EXTENSION, attackFrames);
        this.setCycle(IDLE_CYCLE, folder + IDLE_CYCLE + Sprite.EXTENSION, idleFrames);

        this.setThumbnail(FILE_THUMBNAIL, folder + FILE_THUMBNAIL + Sprite.EXTENSION);

        this.useIdleCycle();
    }

    public Image getFileThumbnail() {
        return this.getThumbnail(FILE_THUMBNAIL);
    }

    public void useAttackCycle() {
        this.useCycle(ATTACK_CYCLE);
    }

    public void useIdleCycle() {
        this.useCycle(IDLE_CYCLE);
    }

    @Override
    public CowSprite clone() {
        CowSprite sprite = new CowSprite(this.getWidth(), this.getHeight(), this.getTicksPerFrame());
        
        for (String name: this.getThumbnails().keySet()) {
            sprite.setThumbnail(name, this.getThumbnails().get(name));
        }
        
        for (String name: this.getCycles().keySet()) {
            sprite.setCycle(name, this.getCycles().get(name).getFrames());
        }

        return sprite;
    }
}
