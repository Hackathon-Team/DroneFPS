import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import javax.sound.sampled.*;

public class Creeper implements Shootable { //class declaration for creeper that implements Shootable interface
    
    private Rectangle bodyBox; //rectangle declaration for the hitbox of the creeper
    BufferedImage picture; //instance field for picture to display this
    int health = 200; //health
    int moveCounter = 40; //movecounter that dictates when to change moving direction (when movecount reaches 0)
    Point footPoint; //footpoint - defining point to make sure creeper is in the limitBoxes
    Point movePoint; //point that is added to the current point to move the creeper to the next position
    
    private static final Rectangle LIMIT_BOX1 = new Rectangle(212, 238, 232, 61); //limitboxes - set the boundaries for where the creeper can move
    private static final Rectangle LIMIT_BOX2 = new Rectangle(444, 238, 530, 129);
    private static final Rectangle LIMIT_BOX3 = new Rectangle(714, 367, 260, 179);
    
    public Creeper() { //constructor
        respawn(); //call respawn method
        try {
            picture = ImageIO.read(new File("creeper.png")); //set the picture to the file picture in the package
        } catch (Exception e) {}
        Random gen = new Random(); //create a random generator
        movePoint = new Point(gen.nextInt(5), gen.nextInt(5)); //randomly set the movePoint, meaning the next location of the creeper
    }
    
    public void draw(Graphics2D drawer) { //override the draw method
        drawer.drawImage(picture, (int)bodyBox.getX(), (int)bodyBox.getY(), null); //call drawImage method on drawer with parameters the picture and x and y coordinates to draw the image 
    }
    
    public void move() { //method declaration for move method which moves the creeper
        if(moveCounter==0) { //if movecounter == 0, meaning creeper has moved in same direction 40 times already
            Random gen = new Random(); //create random gen
            int x = gen.nextInt(3); //generate randomly change in x and y values for next point
            int y = gen.nextInt(3);
            if(gen.nextInt()%2==0) x = -x; //randomly invert the x and y values to get true random
            if(gen.nextInt()%2==0) y = -y;
            movePoint = new Point(x, y); //creat a new point based on the x and y and assign it to instance field movePoint
            moveCounter = 40; //reset moveCounter back to 40
        }
        
        Point nextPoint = new Point((int)(footPoint.getX()+movePoint.getX()), (int)(footPoint.getY()+movePoint.getY())); //temporarily calculate the nextPoint of the creeper by adding the current foorPoint to the movepoint
        if(!LIMIT_BOX1.contains(nextPoint)&&!LIMIT_BOX2.contains(nextPoint)&&!LIMIT_BOX3.contains(nextPoint)) { //if neither limitBoxes contain the nextPoint
            movePoint = new Point((int)-movePoint.getX(), (int)-movePoint.getY()); //invert the change in x and y values of movePoint
            nextPoint = new Point((int)(footPoint.getX()+movePoint.getX()), (int)(footPoint.getY()+movePoint.getY())); //recalculate the nextPoint based on the new movePoint
        }
        footPoint = nextPoint; //assign current footPoint the value of nextPoint
        bodyBox = new Rectangle((int)(footPoint.getX()-60), (int)(footPoint.getY()-116), 60, 116); //re-calculate the hitBox of the creeper based on the movePoint
        moveCounter--; //decrement the movecounter by one
    }
    
    public boolean contains(Point x) { //contains method to see if the creeper contains the point
        return bodyBox.contains(x); //return value of hitbox containing the point
    }
    
    public void hit() { //hit method for creeper to take a hit
        health--; //subtract health by one
        if(health<0) health = 0; //restrict health from going less that 0
    }
    
    public void respawn() { //method to randomly spawn the creeper
        Random gen = new Random(); //declare and initialize random generator
        footPoint = new Point((int)(gen.nextInt((int)LIMIT_BOX2.getWidth())+LIMIT_BOX2.getX()), (int)(gen.nextInt((int)LIMIT_BOX2.getHeight())+LIMIT_BOX2.getY())); //assign new point to footpoint, new point is randomly selected somewhere in the bounds of LIMIT_BOX2
        bodyBox = new Rectangle((int)(footPoint.getX()-60), (int)(footPoint.getY()-116), 60, 116); //create new rectangle bodyBox which is the hit box based on the defining footpoint
        health = 200; //reset health to 200
    }
    
    public boolean isAlive() { //returns aliveness of creeper 
        if(health==0) return false; //return false if no health
        return true; //otherwise return true
    }
    
    public void die() { //method that plays a sound clip when the creeper dies
        try { //try catch statement to catch any exceptions that might be thrown
            Clip dieSound = AudioSystem.getClip(); //declare audio Clip
            dieSound.open(AudioSystem.getAudioInputStream(new File("creeperDie.wav"))); //open the clip from the sound file
            dieSound.start(); //play the sound clip once 
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}  
    }
    
}