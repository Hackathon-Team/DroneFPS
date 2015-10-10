import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.*;

public class ViewComp extends JComponent {

    private Graphics2D drawer;
    Clip gunSound;
    private Point mousePoint;
    private CrossHairView crossHairs;
    private Creeper[] enemies;
    private int[] enemyCounter = new int[3];
    private boolean pressed;
    private int points;
    
    public ViewComp() {
        try {
            gunSound = AudioSystem.getClip();
            gunSound.open(AudioSystem.getAudioInputStream(new File("gunfire.wav")));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}        
        mousePoint = new Point(0, 0);
        for(int x=0; x<3; x++) enemyCounter[x] = 200;
        enemies = new Creeper[6];
        enemies[0] = new Creeper();
        enemies[1] = new Creeper();
        enemies[2] = new Creeper();
        crossHairs = new CrossHairView();              
        points = 0;
    }    

    public void firePressed() {
        pressed = true;
        gunSound.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void fireReleased() {
        pressed = false;
        gunSound.stop();
    }
    
    public void setViewLocation(int c, int y) {
        repaint();
        mousePoint = new Point(c, y);
        for(int x=0; x<3; x++) {
            if(enemies[x].contains(mousePoint)&&pressed) enemies[x].hit();
            if(enemies[x].isAlive()) enemies[x].move();
            else {
                if(enemyCounter[x]==200) {
                    enemies[x].die();
                    points += 50;
                }
                enemyCounter[x]--;
            }
            if(enemyCounter[x]==0) {
                enemies[x].respawn();
                enemyCounter[x] = 200;
            }
        }
    }
    
public void paintComponent(Graphics g) {
        drawer =(Graphics2D) g;
        drawer.setStroke(new BasicStroke(2));
        crossHairs.setLocation((int)mousePoint.getX(), (int)mousePoint.getY(), pressed);
        boolean aimed = false;
        for(int x=0; x<3; x++) {
            if(enemyCounter[x]==200) {
                enemies[x].draw(drawer);
                if(enemies[x].contains(mousePoint)) aimed = true;
            }
        }
        drawer.setColor(Color.WHITE);
        drawer.setFont(new Font("Helvetica", Font.PLAIN, 20));
        drawer.drawString("Points: " + points, 850, 50);
        crossHairs.draw(drawer, aimed);
    }      
    
}