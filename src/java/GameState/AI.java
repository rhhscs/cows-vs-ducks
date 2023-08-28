package src.java.GameState;

import java.util.ArrayList;

/**
 * This represents an interface that determines whether an entity (cow or duck)
 * should attack or not, assuming it is ready to attack.
 */
@FunctionalInterface
public interface AI {
    public boolean shouldAttack(ArrayList<Entity> targets, Entity self);

    public static final AI MELEE_COW_AI = new AI() {
        /**
         * This type of cow attacks if any duck collides with its hitbox.
         * 
         * @param lanes The lanes of ducks that the cow could possibly reach.
         * @param cow   The cow to check.
         */
        @Override
        public boolean shouldAttack(ArrayList<Entity> lanes, Entity cow) {
            return true;
        }
    };

    public static final AI SHIELD_COW_AI = new AI() {
        /**
         * This type of cow never attacks.
         */
        @Override
        public boolean shouldAttack(ArrayList<Entity> lanes, Entity cow) {
            return false;
        }
    };

    public static final AI SHOOTER_COW_AI = new AI() {
        /**
         * This decides if a cow should attack, given a list of possible lanes that it
         * can effect. It will attack if in any of the lanes, the farthest duck is in
         * front of the cow.
         * 
         * @param lanes The lanes of ducks.
         * @param cow   The cow to decide attack behaviour.
         */
        @Override
        public boolean shouldAttack(ArrayList<Entity> lanes, Entity cow) {
            return true;
        }
    };

    public static final AI WHEAT_CROP_COW_AI = new AI() {
        /**
         * This type of cow (wheat crop) always sends out an "attack" (produces wheat)
         * when available.
         * 
         * @param whatever  Doesn't matter what this is.
         * @param wheatCrop The wheat crop/cow.
         */
        public boolean shouldAttack(ArrayList<Entity> whatever, Entity wheatCrop) {
            return true;
        }
    };

    public static final AI MELEE_DUCK_AI = new AI() {
        /**
         * This type of duck attacks if any cow collides with its hitbox.
         * 
         * @param cows An arraylist of cows in the duck's lane.
         * @param duck The duck to check.
         */
        @Override
        public boolean shouldAttack(ArrayList<Entity> cows, Entity duck) {
            return false;
        }
    };
}
