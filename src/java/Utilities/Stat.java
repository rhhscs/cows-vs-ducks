package src.java.Utilities;

import java.util.ArrayList;
import java.util.Iterator;

import src.java.Updatable;

/**
 * This class represents an integer value that can be temporarily modified to handle status effects.
 */
public class Stat implements Updatable {
    private int baseValue;
    private ArrayList<Modifier> mods;
    private int value;

    /**
     * This constructs a new stat with a base value.
     * @param baseValue The base value without any modifiers.
     */
    public Stat(int baseValue) {
        this.baseValue = baseValue;
        this.value = baseValue;
        this.mods = new ArrayList<Modifier>();
    }

    /**
     * This gets the current value with the modifiers active.
     * @return The current value.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * This gets the value without any modifiers.
     * @return The base value.
     */
    public int getBaseValue() {
        return this.baseValue;
    }

    /**
     * This adds a modifier to this stat.
     * @param value The value to change this stat by.
     * @param duration The duration the modifier lasts for.
     */
    public void addModifier(int value, int duration) {
        this.value += value;
        this.mods.add(new Modifier(value, duration));
    }

    @Override
    public void update() {
        Iterator<Modifier> iterator = this.mods.iterator();
        while (iterator.hasNext()) {
            Modifier mod = iterator.next();

            mod.update();
            if (!mod.isActive()) {
                this.value -= mod.getValue();
                iterator.remove();
            }
        }
    }

    /**
     * This class represents a modifier applied to a stat
     */
    private static class Modifier implements Updatable {
        private int value;
        private int duration;

        /**
         * This constructs a new modifier.
         * @param value The value to modify the stat by.
         * @param duration The duration the modifier lasts for.
         */
        public Modifier(int value, int duration) {
            this.value = value;
            this.duration = duration;
        }

        @Override
        public void update() {
            this.duration--;
        }

        public boolean isActive() {
            return this.duration > 0;
        }

        public int getValue() {
            return this.value;
        }
    }
}