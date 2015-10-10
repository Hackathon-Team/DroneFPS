import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import javax.sound.sampled.*;

//detailed comment code for classes implementing Shootable interface can be found in Creeper class

public class Fish implements Shootable {
    
    private Rectangle bodyBox;
    BufferedImage picture;
    int health = 200;
    int moveCounter = 40;
    Point footPoint;
    Point movePoint;
    private static final Rectangle LIMIT_BOX1 = new Rectangle(2, 269, 230, 60);
    private static final Rectangle LIMIT_BOX2 = new Rectangle(2, 329, 327, 102);
    private static final Rectangle LIMIT_BOX3 = new Rectangle(2, 431, 429, 152);
    
    //constructor
    public Fish() {
        respawn();
        try {
            picture = ImageIO.read(new File("fish.png"));
        } catch (Exception e) {}
        Random gen = new Random();
        movePoint = new Point(gen.nextInt(3), gen.nextInt(3));
    }
    
    //draws an image of the object
    public void draw(Graphics2D drawer) {        
        drawer.drawImage(picture, (int)bodyBox.getX(), (int)bodyBox.getY(), null);    
    }
    
    //calculates the next move position of the object inside limit boxes, according to direction
    public void move() {
        if(moveCounter==0) {
            Random gen = new Random();
            int x = gen.nextInt(3);
            int y = gen.nextInt(3);
            if(gen.nextInt()%2==0) x = -x;
            if(gen.nextInt()%2==0) y = -y;
            movePoint = new Point(x, y);
            moveCounter = 40;
        }
        
        Point nextPoint = new Point((int)(footPoint.getX()+movePoint.getX()), (int)(footPoint.getY()+movePoint.getY()));
        if(!LIMIT_BOX1.contains(nextPoint)&&!LIMIT_BOX2.contains(nextPoint)&&!LIMIT_BOX3.contains(nextPoint)) {
            movePoint = new Point((int)-movePoint.getX(), (int)-movePoint.getY());
            nextPoint = new Point((int)(footPoint.getX()+movePoint.getX()), (int)(footPoint.getY()+movePoint.getY()));
        }
        footPoint = nextPoint;
        bodyBox = new Rectangle((int)(footPoint.getX()), (int)(footPoint.getY()-85), 125, 85);
        moveCounter--;
    }
    
    //checks if point is contained in hitBox
    public boolean contains(Point x) {
        return bodyBox.contains(x);
    }
    
    //gets hit
    public void hit() {
        health--;
        if(health<0) health = 0;       
    }
    
    //respawns the dead object
    public void respawn() {
        Random gen = new Random();
        footPoint = new Point((int)(gen.nextInt((int)LIMIT_BOX2.getWidth())+LIMIT_BOX2.getX()), (int)(gen.nextInt((int)LIMIT_BOX2.getHeight())+LIMIT_BOX2.getY()));
        bodyBox = new Rectangle((int)(footPoint.getX()), (int)(footPoint.getY()-85), 125, 85);
        health = 200;
    }
    
    //checks whether is alive
    public boolean isAlive() {
        if(health==0) return false;
        return true;
    }
    
    //kills the object - plays death audio stream
    public void die() {
        try {
            Clip dieSound = AudioSystem.getClip();
            dieSound.open(AudioSystem.getAudioInputStream(new File("fishDie.wav")));
            dieSound.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}  
    }
    
}