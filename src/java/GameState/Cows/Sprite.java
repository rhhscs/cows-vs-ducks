package src.java.GameState.Cows;

public class Sprite {
    private final static String SOURCE = "src/img/sprite/";
    private final static int defaultSpriteTileSize = 256;
    public final static Sprite NULL = new Sprite(null, 0, 0, 0, 3);
    // cows
    public final static Sprite CATAPULT =  new Sprite("cow/cow_catapult",  9,  0, 0, 3);
    public final static Sprite BODYGUARD = new Sprite("cow/cereal_box"  ,  0, 12, 0, 4);
    public final static Sprite WHEAT =     new Sprite("cow/crop_wheat"  ,  0, 3, 0, 4);
    public final static Sprite SPIKES =    new Sprite("cow/crushed_chunks",  1,  1, 0, 3);
    public final static Sprite FRIDGE =    new Sprite("cow/cold_fridge" , 16,  0, 0, 3);
    public final static Sprite KABOOM =    new Sprite("cow/cow_kaboom" , 724, 724, 20,  0, 0, 2);


    public String filepath;
    public int attackFrames;
    public int idleFrames;
    public int walkFrames;
    public int ticksPerFrame;
    public int height;
    public int width;
    
    private Sprite(String folder, int atkFrames, int idleFrames, int walkFrames, int ticksPerFrame){
        this(folder, defaultSpriteTileSize, defaultSpriteTileSize, atkFrames, idleFrames, walkFrames, ticksPerFrame);
    }
    private Sprite(String folder, int height, int width, int atkFrames, int idleFrames, int walkFrames, int ticksPerFrame){
        this.filepath = SOURCE + folder;
        this.attackFrames = atkFrames;
        this.idleFrames = idleFrames;
        this.walkFrames = walkFrames;
        this.ticksPerFrame = ticksPerFrame;
        this.width = width;
        this.height = height;
    }
    public int getAttackTicks() {
        return ticksPerFrame * attackFrames;
    }
    public int getIdleTicks() {
        return ticksPerFrame * idleFrames;
    }

}
