import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import javax.sound.sampled.*;
import java.io.*;

public class Engine {
    
    public static void main(String[] args) {
        //JOptionPane.showMessageDialog(null, "Mouse around with your mouse to aim, click and hold to shoot, and gather the most points possible!\np.s. sometimes the program freezes and just displays white so just restart it!");
        
        JFrame display = new JFrame("DroneFPS");
        display.setSize(1000, 625);
        display.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        final ViewComp picture = new ViewComp();
        display.add(picture);
        display.setVisible(true);
        JLabel backgroundLabel = new JLabel(new ImageIcon("background.jpg"));
        display.add(backgroundLabel);
        display.setVisible(true);
        
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        display.getContentPane().setCursor(blankCursor);
        
        final Timer viewUpdater = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                mousePoint.translate(-8, -31);
                picture.setViewLocation((int)mousePoint.getX(), (int)mousePoint.getY());
            }
        });
        
        class MoveListener implements MouseListener {
            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) { viewUpdater.start(); }
            public void mouseExited(MouseEvent e) { viewUpdater.stop(); }
            public void mousePressed(MouseEvent e) { picture.firePressed(); }
            public void mouseReleased(MouseEvent e) { picture.fireReleased(); }
        }
        display.addMouseListener(new MoveListener());
        viewUpdater.start();

//        try {
//            Clip backgroundSound = AudioSystem.getClip();
//            backgroundSound.open(AudioSystem.getAudioInputStream(new File("backgroundNoise.wav")));
//            backgroundSound.loop(Clip.LOOP_CONTINUOUSLY);
//        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {}
    }
    
}