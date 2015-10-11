import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Creeper {
    
    private Rectangle bodyBox;
    BufferedImage picture;
    int health = 50;
    Point footPoint;
    Point movePoint;
    int dx;
    int moveCounter = 0;
    
    //private static final Rectangle LIMIT_BOX1 = new Rectangle(212, 238, 232, 61);
    //private static final Rectangle LIMIT_BOX2 = new Rectangle(444, 238, 530, 129);
    //private static final Rectangle LIMIT_BOX3 = new Rectangle(714, 367, 260, 179);
    
    public Creeper() {
        respawn();
        Random gen = new Random();
        movePoint = new Point(0, 1);
        dx = gen.nextInt(3);
        if(gen.nextInt()%2==0) dx = -dx;
    }
    
    public void draw(Graphics2D drawer) {
        drawer.drawImage(picture, (int)bodyBox.getX(), (int)bodyBox.getY(), null);
    }
    
    public void move() {
            int y = 1;
            movePoint = new Point(dx, y);
            if(moveCounter%5==0) {
                int newWidth = picture.getWidth() + 1;
                int newHeight = picture.getHeight() + 2;
                picture = new ImgUtils().scaleImage(newWidth, newHeight, "zombie.png");
            }
        
        //Point nextPoint = new Point((int)(footPoint.getX()+movePoint.getX()),(int)(footPoint.getY()+movePoint.getY()));
        //if(!LIMIT_BOX1.contains(nextPoint)&&!LIMIT_BOX2.contains(nextPoint)&&!LIMIT_BOX3.contains(nextPoint)) {
        //    movePoint = new Point((int)-movePoint.getX(), (int)-movePoint.getY());
           Point nextPoint = new Point((int)(footPoint.getX()+movePoint.getX()), (int)(footPoint.getY()+movePoint.getY()));
        //}
        if(nextPoint.getX()<1000 && nextPoint.getY()<625 && nextPoint.getX()-picture.getWidth()>0) footPoint = nextPoint;
        else if(nextPoint.getY()<625) footPoint = new Point((int)footPoint.getX(),(int)footPoint.getY()+1);
        else if(nextPoint.getY()>=625) {
            JOptionPane.showMessageDialog(null,"You lose!");
        }
        bodyBox = new Rectangle((int)(footPoint.getX()-picture.getWidth()), (int)(footPoint.getY()-picture.getHeight()), picture.getWidth(), picture.getHeight());

        moveCounter++;
    }
    
    public boolean contains(Point x) {
        return bodyBox.contains(x);
    }
    
    public void hit() {
        health--;
        if(health<0) health = 0;
    }
    
    public void respawn() {
        try {
            picture = new ImgUtils().scaleImage(50,87,"zombie.png");
        } catch (Exception e) {}
        Random gen = new Random();
        footPoint = new Point(50+gen.nextInt(900), picture.getHeight());
        bodyBox = new Rectangle((int)(footPoint.getX()-picture.getWidth()), (int)(footPoint.getY()-picture.getHeight()), picture.getWidth(), picture.getHeight());
        health = 50;
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