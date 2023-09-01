package src.java.Utilities;

import src.java.Updatable;

/**
 * This class represents a value that can be set and reverted from a modified
 * for a set duration.
 */
public class Stat<T> implements Updatable {
    private T defaultValue;
    private int duration;
    private T modifiedValue;

    /**
     * This creates a new stat object. It is in its default state.
     * @param defaultValue The default value.
     */
    public Stat(T defaultValue) {
        this.defaultValue = defaultValue;
        this.duration = 0;
        this.modifiedValue = defaultValue;

    }

    @Override
    public void update() {
        if (this.duration > 0)
            this.duration--;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public T getModifiedValue() {
        return modifiedValue;
    }

    public void setModifiedValue(T modifiedValue) {
        this.modifiedValue = modifiedValue;
    }

    /**
     * This applies a modified value to this stat.
     * @param modifiedValue The modified value.
     * @param duration The duration this value lasts for.
     */
    public void applyModifier(T modifiedValue, int duration) {
        this.modifiedValue = modifiedValue;
        this.duration = duration;
    }

    /**
     * This gets the current value of this stat.
     * @return The modified value if a modifier is active, the default value otherwise.
     */
    public T getValue() {
        if (this.duration > 0)
            return this.modifiedValue;
        return this.defaultValue;
    }
}