package src.java.GameState;

import java.awt.Graphics;
import java.util.ArrayList;
import src.java.Drawable;
import src.java.Updatable;

public class ProjectileManager implements Drawable, Updatable{

    public static ProjectileManager projectileManager = new ProjectileManager();

    private DuckManager duckManager;
    private ArrayList<Windup> windups;
    private ArrayList<Projectile> projectiles;

    private ProjectileManager(){}

    public void setDuckManager(DuckManager duckManager){
        this.duckManager = duckManager;
    }

    public void addWindup(Windup windup){
        this.windups.add(windup);
    }

    @Override
    public void update(){
        
        for(int i=this.projectiles.size()-1; i>=0; i--){
            Projectile cur = this.projectiles.get(i);
            if(!cur.getActive() || cur.getDuration() <= 0){
                this.projectiles.remove(i);
            }
        }
    }

    @Override
    public void draw(Graphics g){

    }
    
}
