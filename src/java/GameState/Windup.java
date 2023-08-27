package src.java.GameState;

public class Windup {

    private int windUpTime;
    private Projectile projectile;

    public Windup(int windUpTime, Projectile projectile){
        this.windUpTime = windUpTime;
        this.projectile = projectile;
    }

    public int getWindUpTime(){
        return this.windUpTime;
    }

    public Projectile getProjectile(){
        return this.projectile;
    }
    
}
