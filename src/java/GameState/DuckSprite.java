package src.java.GameState;

public class DuckSprite extends Sprite {
    public static final String ATTACK_CYCLE = "attack";
    public static final String IDLE_CYCLE = "idle";
    public static final String WALK_CYCLE = "walk";
    public static final String EXTENSION = ".png";

    public DuckSprite(int width, int height, int ticksPerFrame) {
        super(width, height, ticksPerFrame);
    }

    public DuckSprite(String folder, int attackFrames, int idleFrames, int walkFrames, int ticksPerFrame) {
        this(folder, DEFAULT_SPRITE_TILE_SIZE, DEFAULT_SPRITE_TILE_SIZE, attackFrames, idleFrames, walkFrames,
                ticksPerFrame);
    }

    public DuckSprite(String folder, int width, int height, int attackFrames, int idleFrames, int walkFrames,
            int ticksPerFrame) {
        super(width, height, ticksPerFrame);

        this.setCycle(ATTACK_CYCLE, folder + ATTACK_CYCLE + EXTENSION, attackFrames);
        this.setCycle(IDLE_CYCLE, folder + IDLE_CYCLE + EXTENSION, idleFrames);
        this.setCycle(WALK_CYCLE, folder + WALK_CYCLE + EXTENSION, walkFrames);

        this.useWalkCycle();
    }

    public void useAttackCycle() {
        this.useCycle(ATTACK_CYCLE);
    }

    public void useIdleCycle() {
        this.useCycle(IDLE_CYCLE);
    }

    public void useWalkCycle() {
        this.useCycle(WALK_CYCLE);
    }

    @Override
    public DuckSprite clone() {
        DuckSprite sprite = new DuckSprite(this.getWidth(), this.getHeight(), this.getTicksPerFrame());

        for (String name : this.getThumbnails().keySet()) {
            sprite.setThumbnail(name, this.getThumbnails().get(name));
        }

        for (String name : this.getCycles().keySet()) {
            sprite.setCycle(name, this.getCycles().get(name).getFrames());
        }

        return sprite;
    }
}
