package src.java.GameState;

import java.awt.Graphics;
import java.util.ArrayList;
import src.java.Drawable;
import src.java.Updatable;

public class ProjectileManager implements Drawable, Updatable {

    public static ProjectileManager projectileManager = new ProjectileManager();

    private DuckManager duckManager;
    private ArrayList<Projectile> projectiles;

    private ProjectileManager() {
    }

    public void setDuckManager(DuckManager duckManager) {
        this.projectiles = new ArrayList<>();
        this.duckManager = duckManager;
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    @Override
    public void update() {
        // check for collisions with the ducks
        for (Projectile projectile : this.projectiles) {
            boolean used = false;
            ArrayList<Entity> collidingLanes = this.duckManager.getCollidingLanes(projectile);
            projectile.update();

            for (Entity lane : collidingLanes) {
                ArrayList<Duck> ducks = ((Lane) lane).getDucks();
                for (Duck duck : ducks) {
                    if (projectile.getActive() && duck.collides(projectile)) {
                        // collision occurred.

                        if (projectile.getSingleTarget()) {
                            // delete projectile after hit.
                            projectile.setActive(false);
                            projectile.setDuration(0);
                        } else {
                            // delete projectile at the end of this frame.
                            used = true;
                        }

                        // damage duck.
                        duck.takeDamage(projectile.getDamage());
                    }
                }
            }
            if (used) {
                projectile.setActive(false);
            }
        }
        // if the projectile duration is over, or is inactive, remove it
        for (int i = this.projectiles.size() - 1; i >= 0; i--) {
            Projectile cur = this.projectiles.get(i);
            if (cur.getDuration() <= 0) {
                this.projectiles.remove(i);
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Projectile projectile : this.projectiles) {
            projectile.draw(g);
        }
    }

    public void reset() {
        this.duckManager = null;
        this.projectiles.clear();
    }

}
