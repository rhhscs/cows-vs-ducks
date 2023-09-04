package src.java.GameState;

public class PiercingProjectile extends Projectile {
    public PiercingProjectile(int x, int y, int width, int height, int speed, int damage, int duration, Sprite sprite) {
        super(x, y, width, height, speed, damage, 0, false, 0, true, duration, sprite);
    }

    @Override
    public PiercingProjectile clone() {
        return new PiercingProjectile(getX(), getY(), getWidth(), getHeight(), getSpeed(), getDamage(), getDuration(), getSprite());
    }
}
