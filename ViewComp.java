import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.*;

public class ViewComp extends JComponent { //class declaration for class which controls interactive elements on screen
    
    private Graphics2D drawer; //instance field declaration
    Clip gunSound; //gunSound when mouse is pressed
    private Point mousePoint; //mouse point
    private CrossHairView crossHairs; //a crosshair
    private Shootable[] enemies; //array of shootable enemies
    private int[] enemyCounter = new int[6]; //integer array of enemycounter to count down time until respawn
    private boolean pressed; //boolean controlling if trigger is pressed
    private int points; //number of points user has accumulated
    
    public ViewComp() { //constructor
        try { //try catch to catch exceptions
            gunSound = AudioSystem.getClip(); //get clip instance
            gunSound.open(AudioSystem.getAudioInputStream(new File("gunfire.wav"))); //open the file of the sound clip
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}        
        mousePoint = new Point(0, 0); //initialize all the instance fields
        for(int x=0; x<6; x++) enemyCounter[x] = 200; //counter is 200 - timer updates every 10, so every 200 updates (2 seconds) monster respawns if it dies
        enemies = new Shootable[6];
        enemies[0] = new Creeper();
        enemies[1] = new Creeper();
        enemies[2] = new Fish();
        enemies[3] = new Fish();
        enemies[4] = new Bird();
        enemies[5] = new Bird();
        crossHairs = new CrossHairView();              
        points = 0;
    }    
    
    public void firePressed() { //called if mouse is clicked
        pressed = true; //set boolean
        gunSound.loop(Clip.LOOP_CONTINUOUSLY); //play gunfire sound
    }
    
    public void fireReleased() { //called if mouse is released
        pressed = false; //set boolean
        gunSound.stop(); //stop gunfire sound
    }
    
    public void setViewLocation(int c, int y) { //update method called every time timer sends new locations; controlls all movement
        repaint(); //paint the view field, now update....
        mousePoint = new Point(c, y); //create new point based on explicit parameters
        for(int x=0; x<6; x++) { //for loop to loop through all the enemies
            if(enemies[x].contains(mousePoint)&&pressed) enemies[x].hit(); //calls hit method if crossHair is aimed and pressed on enemy
            if(enemies[x].isAlive()) enemies[x].move(); //moves the enemy only if it is alive
            else { //if enemy is dead
                if(enemyCounter[x]==200) { //only if the enemyCounter is 200, meaning is just died
                    enemies[x].die(); //call die method
                    points += 50; //increase points by 50
                }
                enemyCounter[x]--; //decrease enemyCounter by one
            }
            if(enemyCounter[x]==0) { //when enemyCounter reaches 0, or has died for 2 seconds
                enemies[x].respawn(); //respawn the enemy
                enemyCounter[x] = 200; //reset the enemyCounter
            }
        }
    }
    
    public void paintComponent(Graphics g) { //overriding the paintComponent method
        drawer =(Graphics2D) g; //typecast into Graphics2D for easier drawing
        drawer.setStroke(new BasicStroke(2)); //set the stroke to width of 2
        crossHairs.setLocation((int)mousePoint.getX(), (int)mousePoint.getY(), pressed); //call setLocation method on crossHairs with x and y coordinates stored in point instance field
        boolean aimed = false; //initialize aim to be false
        for(int x=0; x<6; x++) { //for loop to loop through all enemies
            if(enemyCounter[x]==200) { //if enemyCounter is 200 - meaning still alive
                enemies[x].draw(drawer); //then draw the enemy by calling the draw method on them
                if(enemies[x].contains(mousePoint)) aimed = true; //if the enemy contains the point of the crossHairs, set aimed to true  
            }        
        }
        drawer.setColor(Color.WHITE); //set font color to white of the drawer
        drawer.setFont(new Font("Helvetica", Font.PLAIN, 20)); //set the font to a bigger font so the score can be seen
        drawer.drawString("Points: " + points, 850, 50); //draw the score as a string to the user by calling the drawString method
        crossHairs.draw(drawer, aimed); //draw the crossHairs by calling the draw method and boolean aimed 
    }      
    
}