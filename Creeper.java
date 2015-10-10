import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import javax.sound.sampled.*;

public class Creeper implements Shootable {
    
    private Rectangle bodyBox;
    BufferedImage picture;
    int health = 200;
    int moveCounter = 40;
    Point footPoint;
    Point movePoint;
    
    private static final Rectangle LIMIT_BOX1 = new Rectangle(212, 238, 232, 61);
    private static final Rectangle LIMIT_BOX2 = new Rectangle(444, 238, 530, 129);
    private static final Rectangle LIMIT_BOX3 = new Rectangle(714, 367, 260, 179);
    
    public Creeper() {
        respawn();
        try {
            picture = ImageIO.read(new File("creeper.png"));
        } catch (Exception e) {}
        Random gen = new Random();
        movePoint = new Point(gen.nextInt(5), gen.nextInt(5));
    }
    
    public void draw(Graphics2D drawer) {
        drawer.drawImage(picture, (int)bodyBox.getX(), (int)bodyBox.getY(), null);
    }
    
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
        bodyBox = new Rectangle((int)(footPoint.getX()-60), (int)(footPoint.getY()-116), 60, 116);
        moveCounter--;
    }
    
    public boolean contains(Point x) {
        return bodyBox.contains(x);
    }
    
    public void hit() {
        health--;
        if(health<0) health = 0;
    }
    
    public void respawn() {
        Random gen = new Random();
        footPoint = new Point((int)(gen.nextInt((int)LIMIT_BOX2.getWidth())+LIMIT_BOX2.getX()), (int)(gen.nextInt((int)LIMIT_BOX2.getHeight())+LIMIT_BOX2.getY()));
        bodyBox = new Rectangle((int)(footPoint.getX()-60), (int)(footPoint.getY()-116), 60, 116);
        health = 200;
    }
    
    public boolean isAlive() {
        if(health==0) return false;
        return true;
    }
    
    public void die() {
        try {
            Clip dieSound = AudioSystem.getClip();
            dieSound.open(AudioSystem.getAudioInputStream(new File("creeperDie.wav")));
            dieSound.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}  
    }
    
}