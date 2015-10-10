import javax.swing.*; //import statements
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import javax.sound.sampled.*;
import java.io.*;

public class Engine { //class declaration for the main class to run the program
    
    public static void main(String[] args) { //main method declaration, runs the program
        JOptionPane.showMessageDialog(null, "Mouse around with your mouse to aim, click and hold to shoot, and gather the most points possible!\np.s. sometimes the program freezes and just displays white so just restart it!"); //call static method on JOptionPane class to display message to user
        
        JFrame display = new JFrame("My FPS Game."); //declare new JFrame reference display and initialize with new instance of the class, string explicit parameter for the title
        display.setSize(1000, 625); //set the size of display by calling the setSize method on the reference
        display.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //set its default close operation by passing in the parameter as a parameter in the method call of method setDefaultCloseOperation on the reference display
        
        final ViewComp picture = new ViewComp(); //declare ViewComp reference picture and initialize it with a new isntance; viewComp handles the view of the elements in the frame
        display.add(picture); //call the add method on display with explicit parameter picture to add the picture to the display
        display.setVisible(true); //call setVisible method on display with parameter false to present the display to the user    
        JLabel backgroundLabel = new JLabel(new ImageIcon("background.jpg")); //declare JLabel background and initialize it with JLabel object and pass in ImageIcon in constructor and create imageicon with the string explicit parameter representing the file name of the background picture
        display.add(backgroundLabel); //add the JLabel backgroundLabel to display, the main JFrame
        display.setVisible(true);
        
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); //declare and initialize a BufferedImage variable with 2 integers and static field of the BufferedImage class
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor"); //declare and initialize a Cursor variable with a custom transparent cursor returned by the method creatCustomCursor with string, point, and picture explicit parameters
        display.getContentPane().setCursor(blankCursor); //set the Cursor of the mouse to the blankCursor image created in the previous line by calling the setCursor method on the content pane of the frame
        
        final Timer viewUpdater = new Timer(10, new ActionListener() { //declare a new Timer with 10 millisecond wait time and explicit parameter as anonymous inner class the implements ActionListener
            public void actionPerformed(ActionEvent e) { //method declaration to override method in ActionListener interface
                Point mousePoint = MouseInfo.getPointerInfo().getLocation(); //call getPointerInfo to get instance of the mouse class then call getLocation to return the Point location and assign the value to a point reference
                mousePoint.translate(-8, -31); //translate the point reference according to the explicit parameters to account for the width of the Jframe
                picture.setViewLocation((int)mousePoint.getX(), (int)mousePoint.getY()); //call the setViewLocation method on the ViewComp picture with explicit parameters being the x and y coordinates of the mouse pointer
            }
        });
        
        class MoveListener implements MouseListener { //class declaration for class that implements MouseListener interface
            public void mouseClicked(MouseEvent e) {} //dummy method stub that needs to be overriden to implement the interface
            public void mouseEntered(MouseEvent e) { viewUpdater.start(); } //start the timer by calling the start method on it if mouse Enters the frame
            public void mouseExited(MouseEvent e) { viewUpdater.stop(); } //stop the timer by calling the stop method on it if the mouse Exits the frame
            public void mousePressed(MouseEvent e) { picture.firePressed(); } //call the firepressed method on picture if mouse is pressed
            public void mouseReleased(MouseEvent e) { picture.fireReleased(); } //call the fireReleased method on picture if mouse is released
        }
        display.addMouseListener(new MoveListener()); //create new instance of MoveListener class and add it to display by calling the addMouseListener method and passing it in as an explicit parameter
        viewUpdater.start(); //start the timer
        
        try { //try catch statement to catch any exceptions thrown while playing audio clip
            Clip backgroundSound = AudioSystem.getClip(); //get new instance of Clip from AudioSystem class
            backgroundSound.open(AudioSystem.getAudioInputStream(new File("backgroundNoise.wav"))); //open the clip by passing in audio stream returned by caling method with explicit parameter a new file which locates the audio file
            backgroundSound.loop(Clip.LOOP_CONTINUOUSLY); //play the audio sound in a continuous loop
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}        
    }
    
}