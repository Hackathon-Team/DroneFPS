/**
 * Created by Ryan on 10/10/15.
 */
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {


    }

    //@Override
    protected void paintComponent(Graphics g) {

        // Allow super to paint
        //super.paintComponent(g);

        // Apply our own painting effect
        Graphics2D g2d = (Graphics2D) g.create();
        // 50% transparent Alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        g2d.setColor(getBackground());
        g2d.fill(getBounds());

        g2d.dispose();

    }

}
