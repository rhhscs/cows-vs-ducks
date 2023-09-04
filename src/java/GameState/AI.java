package src.java.GameState;

import java.util.ArrayList;

import src.java.GameState.Cows.Cow;

/**
 * This represents an interface that determines what this entity should attack next.
 */
@FunctionalInterface
public interface AI {
    public Entity findTarget(ArrayList<Entity> targets, Entity self);

    public static final AI MELEE_COW_AI = new AI() {
        /**
         * This type of cow attacks if any duck collides with its hitbox.
         * 
         * @param lanes The lanes of ducks that the cow could possibly reach.
         * @param cow   The cow to check.
         * @return The duck that this cow should hit, null if there is no duck.
         */
        @Override
        public Entity findTarget(ArrayList<Entity> lanes, Entity cow) {
            for (Entity entity: lanes) {
                Lane lane = (Lane) entity;
                for (Duck duck: lane) {
                    if (cow.collides(duck)) {
                        return duck;
                    }
                }
            }

            return null;
        }
    };

    public static final AI SHIELD_COW_AI = new AI() {
        /**
         * This type of cow never attacks.
         * @return null.
         */
        @Override
        public Entity findTarget(ArrayList<Entity> lanes, Entity cow) {
            return null;
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
         * @return The last duck in this cow's lane.
         */
        @Override
        public Entity findTarget(ArrayList<Entity> lanes, Entity cow) {
            for (Entity entity: lanes) {
                Lane lane = (Lane) entity;
                Duck duck = lane.getFarthestDuck();
                if (duck != null && cow.getX() < duck.getX()) {
                    return duck;
                }
            }

            return null;
        }
    };

    public static final AI WHEAT_CROP_COW_AI = new AI() {
        /**
         * This type of cow (wheat crop) always sends out an "attack" (produces wheat)
         * when available.
         * 
         * @param whatever  Doesn't matter what this is.
         * @param wheatCrop The wheat crop/cow.
         * @return A null duck
         */
        public Entity findTarget(ArrayList<Entity> whatever, Entity wheatCrop) {
            return Duck.NULL_DUCK;
        }
    };

    public static final AI MELEE_DUCK_AI = new AI() {
        /**
         * This type of duck attacks if any cow collides with its hitbox.
         * 
         * @param cows An arraylist of cows in the duck's lane.
         * @param duck The duck to check.
         * @return The cow to attack, null if there is no cow to hit.
         */
        @Override
        public Entity findTarget(ArrayList<Entity> cows, Entity duck) {
            for (Entity entity: cows) {
                Cow cow = (Cow) entity;
                    if (cow.isTargetable() && duck.collides(cow)) {
                        return cow;
                    }
            }

            return null;
        }
    };

    public static final AI RUBBER_DUCK_AI = new AI() {
        @Override
        public Entity findTarget(ArrayList<Entity> cows, Entity duck) {
            for (Entity entity: cows) {
                Cow cow = (Cow) entity;
                if (duck.collides(cow)) {
                    return cow;
                }
            }

            return null;
        }
    };
}
