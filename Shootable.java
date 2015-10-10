import java.awt.*;

public interface Shootable { //interface declaration for Shootable interface that ties all objects that can be shot by the user
    
    public void draw(Graphics2D drawer); //method stubs that all implementing classes must override
    public void move();
    public boolean contains(Point x);
    public void hit();
    public boolean isAlive();
    public void respawn();
    public void die();
    
}