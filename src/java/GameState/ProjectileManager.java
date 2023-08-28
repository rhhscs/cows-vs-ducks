package src.java.GameState;

import java.awt.Graphics;
import java.util.ArrayList;
import src.java.Drawable;
import src.java.Updatable;

public class ProjectileManager implements Drawable, Updatable{

    public static ProjectileManager projectileManager = new ProjectileManager();

    private DuckManager duckManager;
    private ArrayList<Projectile> projectiles;

    private ProjectileManager(){}

    public void setDuckManager(DuckManager duckManager){
        this.projectiles = new ArrayList<>();
        this.duckManager = duckManager;
    }

    public void addProjectile(Projectile projectile){
        this.projectiles.add(projectile);
    }

    @Override
    public void update(){
        // check for collisions with the ducks
        for(Projectile cur: this.projectiles){
            boolean used = false;
            ArrayList<Lane> collidingLanes = this.duckManager.getCollidingLanes(cur);
            for(Lane nextLane: collidingLanes){
                ArrayList<Duck> ducks = nextLane.getDucks();
                for(Duck nextDuck: ducks){
                    if(cur.getActive() && nextDuck.collides(cur)){
                        if(cur.getSingleTarget()){
                            // do damage
                            cur.setActive(false);
                            cur.setDuration(0);
                        }else{
                            used = true;
                            // do damage
                        }
                    }
                }
            }
            if(used){
                cur.setActive(false);
            }
        }
        // if the projectile duration is over, or is inactive, remove it
        for(int i=this.projectiles.size()-1; i>=0; i--){
            Projectile cur = this.projectiles.get(i);
            if(cur.getDuration() <= 0){
                this.projectiles.remove(i);
            }
        }
    }

    @Override
    public void draw(Graphics g){
        for(Projectile cur: this.projectiles){
            cur.draw(g);
        }
    }

    public void reset(){
        this.duckManager = null;
        this.projectiles.clear();
    }
    
}
