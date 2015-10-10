import java.awt.*;

public interface Shootable {
    
    public void draw(Graphics2D drawer);
    public void move();
    public boolean contains(Point x);
    public void hit();
    public boolean isAlive();
    public void respawn();
    public void die();
    
}