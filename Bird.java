import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import javax.sound.sampled.*;

//detailed comment code for classes implementing Shootable interface can be found in Creeper class

public class Bird implements Shootable {
    
    private Rectangle bodyBox;
    BufferedImage picture;
    int health = 200;
    int moveCounter = 40;
    Point movePoint;
    private static Rectangle LIMIT_BOX = new Rectangle(0, 0, 950, 180);
    
    //constructor
    public Bird() {
        respawn();
        try {
            picture = ImageIO.read(new File("bird.png"));
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
        Point nextPoint = new Point((int)(bodyBox.getX()+movePoint.getX()), (int)(bodyBox.getY()+movePoint.getY()));
        if(!LIMIT_BOX.contains(nextPoint)) {
            movePoint = new Point((int)-movePoint.getX(), (int)-movePoint.getY());
            nextPoint = new Point((int)(bodyBox.getX()+movePoint.getX()), (int)(bodyBox.getY()+movePoint.getY()));
        }
        bodyBox = new Rectangle((int)nextPoint.getX(), (int)nextPoint.getY(), 120, 74);
        moveCounter--;
    }
    
    //checks if point is contained in hitBox
    public boolean contains(Point x) {
        return bodyBox.contains(x);
    }
    
    //subtracts one from health
    public void hit() {
        health--;
        if(health<0) health = 0;       
    }
    
    //respawns creature
    public void respawn() {
        Random gen = new Random();
        bodyBox = new Rectangle(gen.nextInt((int)LIMIT_BOX.getWidth()), gen.nextInt((int)LIMIT_BOX.getHeight()), 120, 74);
        health = 200;
    }
    
    //true if creature still alive
    public boolean isAlive() {
        if(health==0) return false;
        return true;
    }
    
    //plays the dying sound
    public void die() {
        try {
            Clip dieSound = AudioSystem.getClip();
            dieSound.open(AudioSystem.getAudioInputStream(new File("birdDie.wav")));
            dieSound.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}  
    }
}