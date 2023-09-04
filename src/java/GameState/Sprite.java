package src.java.GameState;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.image.RescaleOp;

import javax.imageio.ImageIO;

import src.java.Updatable;
import src.java.GameState.Cows.CerealBox;
import src.java.GameState.Cows.WheatCrop;

public class Sprite implements Updatable {
    protected final static String SOURCE = "src/img/sprite/";
    protected static final String EXTENSION = ".png";
    protected final static int DEFAULT_SPRITE_TILE_SIZE = 256;
    protected final static int PROJECTILE_SPRITE_TILE_SIZE = 128;

    
    public final static Sprite NULL = new NullSprite();

    // cows
    public final static CowSprite CATAPULT = new CowSprite("cow/cow_catapult/", 9, 1, 3);
    public final static CowSprite BODYGUARD = new CowSprite(4);
    public final static CowSprite WHEAT = new CowSprite(250);
    public final static CowSprite SPIKES = new CowSprite("cow/crushed_chunks/", 1, 1, 10);
    public final static CowSprite FRIDGE = new CowSprite("cow/cold_fridge/", 16, 1, 3);
    public final static CowSprite KABOOM = new CowSprite("cow/cow_kaboom/", 724, 724, 20, 1, 3);
    public final static CowSprite STACK_COW = new CowSprite(3);
    public final static CowSprite LASER = new CowSprite("cow/cool_lazer/", 6, 1, 3);

    // ducks
    public final static DuckSprite BASIC_DUCK = new DuckSprite("duck/duck/", 6, 1, 6, 3);
    public final static DuckSprite BREAD_DUCK = new DuckSprite("duck/bread/", 6, 1, 6, 3);
    public final static DuckSprite RUBBER_DUCK = new DuckSprite("duck/rubber/", 1, 1, 1, 3);
    public final static DuckSprite KNIFE_DUCK = new DuckSprite("duck/knife/", 6, 1, 6, 3);
    public final static DuckSprite RIVER_DUCK = new DuckSprite("duck/river/", 6, 1, 6, 3);
    
    // projectiles
    public final static Sprite CHEERIO = new Sprite(PROJECTILE_SPRITE_TILE_SIZE, PROJECTILE_SPRITE_TILE_SIZE, 1);
    public final static Sprite FROZEN_CHEERIO = new Sprite(PROJECTILE_SPRITE_TILE_SIZE, PROJECTILE_SPRITE_TILE_SIZE, 1);
    public final static Sprite RAINBOW = new Sprite(PROJECTILE_SPRITE_TILE_SIZE, PROJECTILE_SPRITE_TILE_SIZE, 1);

    public static void init() {
        String folder = "cow/cereal_box/";

        try {
            BufferedImage idleCycleSheet = ImageIO
                    .read(new File(SOURCE + folder + CowSprite.IDLE_CYCLE + EXTENSION));
            for (int i = 0; i < CerealBox.stagesOfOuch; i++) {
                BufferedImage tempSpriteSheet = idleCycleSheet.getSubimage(0, DEFAULT_SPRITE_TILE_SIZE * i, idleCycleSheet.getWidth(), DEFAULT_SPRITE_TILE_SIZE);
                BODYGUARD.setCycle(CowSprite.IDLE_CYCLE + i, tempSpriteSheet, 4);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Sprite could not load: " + folder);
        }
        BODYGUARD.setThumbnail(CowSprite.FILE_THUMBNAIL, folder + CowSprite.FILE_THUMBNAIL + EXTENSION);

        folder = "cow/crop_wheat/";
        try {
            BufferedImage idleCycleSheet = ImageIO
                    .read(new File(SOURCE + folder + CowSprite.IDLE_CYCLE + EXTENSION));
            for (int i = 0; i < WheatCrop.numWheatStages; i++) {
                BufferedImage tempSpriteSheet = idleCycleSheet.getSubimage(DEFAULT_SPRITE_TILE_SIZE * i, 0, DEFAULT_SPRITE_TILE_SIZE, idleCycleSheet.getHeight());
                WHEAT.setCycle(CowSprite.IDLE_CYCLE + i, tempSpriteSheet, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Sprite could not load: " + folder);
        }
        WHEAT.setThumbnail(CowSprite.FILE_THUMBNAIL, folder + CowSprite.FILE_THUMBNAIL + EXTENSION);
    
        folder = "cheerio/";
        CHEERIO.setCycle("projectile", folder + "default" + EXTENSION, 1);
        FROZEN_CHEERIO.setCycle("projectile", folder + "frozen" + EXTENSION, 1);
        RAINBOW.setCycle("projectile", folder + "rainbow" + EXTENSION, 1);
    }
    
    private int ticksPerFrame;
    private int width;
    private int height;
    
    private HashMap<String, AnimationCycle> cycles;
    private HashMap<String, BufferedImage> thumbnails;
    
    private AnimationCycle curCycle;
    private int curFrame;
    
    public Sprite(int frameWidth, int frameHeight, int ticksPerFrame) {
        this.width = frameWidth;
        this.height = frameHeight;
        this.ticksPerFrame = ticksPerFrame;

        this.cycles = new HashMap<String, AnimationCycle>();
        this.thumbnails = new HashMap<String, BufferedImage>();
        this.curCycle = NULL_CYCLE;
    }

    public void setCycle(String name, String filePath, int numFrames) {
        this.cycles.put(name, new AnimationCycle(SOURCE + filePath, numFrames));
    }

    public void setCycle(String name, BufferedImage spriteSheet, int numFrames) {
        this.cycles.put(name, new AnimationCycle(spriteSheet, numFrames));
    }

    public void setCycle(String name, AnimationCycle cycle) {
        this.cycles.put(name, cycle);
    }

    public void setCycle(String name, Image[] frames) {
        this.cycles.put(name, new AnimationCycle(frames));
    }

    public void setCycles(HashMap<String, AnimationCycle> cycles) {
        this.cycles = cycles;
    }

    public HashMap<String, AnimationCycle> getCycles() {
        return this.cycles;
    }

    public int getCycleTicks(String name) {
        if (!this.cycles.containsKey(name)) return 0;

        return this.getTicksPerFrame() * this.cycles.get(name).getNumFrames();
    }

    public void setThumbnail(String name, String filePath) {
        BufferedImage thumbnail = null;
        try {
            thumbnail = ImageIO.read(new File(SOURCE + filePath));
        } catch (IOException e) {
            System.err.println("Image not found: " + filePath);
        }

        this.thumbnails.put(name, thumbnail);
    }

    public void setThumbnail(String name, BufferedImage thumbnail) {
        this.thumbnails.put(name, thumbnail);
    }

    public void setThumbnails(HashMap<String, BufferedImage> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public BufferedImage getThumbnail(String name) {
        return this.thumbnails.get(name);
    }

    public HashMap<String, BufferedImage> getThumbnails() {
        return this.thumbnails;
    }

    public void useCycle(String name) {
        AnimationCycle tempCycle = this.cycles.get(name);
        if (tempCycle == null) {
            tempCycle = NULL_CYCLE;
        }

        if (tempCycle != this.curCycle) {
            this.reset();
        }
        this.curCycle = tempCycle;
    }

    public int getTicksPerFrame() {
        return this.ticksPerFrame;
    }

    public void reset() {
        this.curFrame = 0;
    }

    @Override
    public void update() {
        this.curFrame++;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        this.curCycle.draw(g, x, y, width, height);
        this.update();
    }
    
    public void draw(Graphics g, int x, int y, int width, int height, boolean wasHit){
        if(wasHit){
            this.curCycle.draw(g, x, y, width, height, wasHit);
            this.update();
        }else{
            draw(g, x, y, width, height);
        }
    }

    public final AnimationCycle NULL_CYCLE = new AnimationCycle();

    public class AnimationCycle {
        private Image[] frames;
        private int numFrames;

        /**
         * Creates a null animation cycle.
         */
        public AnimationCycle() {
            this.numFrames = 0;
        }

        public AnimationCycle(String filePath, int numFrames) {
            this.numFrames = numFrames;

            if (this.numFrames == 0)
                return;

            try {
                BufferedImage tempSpriteSheet = ImageIO.read(new File(filePath));
                this.createCycle(tempSpriteSheet);
            } catch (IOException e) {
                System.err.println("Animation cycle not found: " + filePath);
                this.frames = null;
                this.numFrames = 0;
            }
        }

        public AnimationCycle(BufferedImage spriteSheet, int numFrames) {
            this.numFrames = numFrames;

            if (this.numFrames == 0)
                return;
            this.createCycle(spriteSheet);
        }

        public AnimationCycle(Image[] frames) {
            if (frames != null) {
                this.numFrames = frames.length;
            } else {
                this.numFrames = 0;
            }
            this.frames = frames;
        }

        private void createCycle(BufferedImage spriteSheet) {
            this.frames = new Image[this.numFrames];
            int i = 0;
            for (int yPos = 0; yPos < spriteSheet.getHeight() && i < this.numFrames; yPos += getHeight()) {
                for (int xPos = 0; xPos < spriteSheet.getWidth() && i < this.numFrames; xPos += getWidth()) {
                    this.frames[i] = spriteSheet.getSubimage(xPos, yPos, getWidth(), getHeight());
                    i++;
                }
            }
        }

        public int getNumFrames() {
            return this.numFrames;
        }

        public Image[] getFrames() {
            return this.frames;
        }

        public void draw(Graphics g, int x, int y, int width, int height) {
            if (this.numFrames == 0)
                return;

            g.drawImage(this.frames[(curFrame / getTicksPerFrame()) % this.numFrames], x, y, width, height, null);
        }

        public void draw(Graphics g, int x, int y, int width, int height, boolean hit){
            if(hit){
                RescaleOp rescaleOp = new RescaleOp(1.5f, 30, null);
                BufferedImage drw = rescaleOp.filter((BufferedImage)this.frames[(curFrame / getTicksPerFrame()) % this.numFrames], null);
                g.drawImage(drw, x, y, width, height, null);
            }else{
                draw(g, x, y, width, height);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Sprite clone() {
        Sprite sprite = new Sprite(this.getWidth(), this.getHeight(), this.getTicksPerFrame());
        
        for (String name: this.thumbnails.keySet()) {
            sprite.setThumbnail(name, this.thumbnails.get(name));
        }
        
        for (String name: this.cycles.keySet()) {
            sprite.setCycle(name, this.cycles.get(name).getFrames());
        }

        return sprite;
    }
}
