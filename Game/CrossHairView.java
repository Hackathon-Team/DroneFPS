import java.awt.*;
import java.awt.geom.*;

public class CrossHairView {
    
    private Line2D.Double topLine;
    private Line2D.Double rightLine;
    private Line2D.Double bottomLine;
    private Line2D.Double leftLine;
    private Ellipse2D.Double circleHair;
    private Ellipse2D.Double circleDot;
    
    public CrossHairView() { //constructor
        topLine = new Line2D.Double(0, 0, 0, 0);
        rightLine = new Line2D.Double(0, 0, 0, 0 );
        bottomLine = new Line2D.Double(0, 0, 0, 0);
        leftLine = new Line2D.Double(0, 0, 0, 0);
        circleHair = new Ellipse2D.Double(0, 0, 0, 0);
        circleDot = new Ellipse2D.Double(0, 0, 0, 0);
    }
    
    public void setLocation(int x, int y, boolean small) {
        if(small) circleHair = new Ellipse2D.Double(x-15, y-15, 30, 30);
        else circleHair = new Ellipse2D.Double(x-20, y-20, 40, 40);
        circleDot = new Ellipse2D.Double(x-2, y-2, 4, 4);
        topLine.setLine(x, y-60, x, y-40);
        rightLine.setLine(x+60, y, x+40, y);
        bottomLine.setLine(x, y+60, x, y+40);
        leftLine.setLine(x-60, y, x-40, y);
    }
    
    public void draw(Graphics2D drawer, boolean aimed) {
        drawer.setColor(Color.BLACK);
        drawer.draw(topLine);
        drawer.draw(rightLine);
        drawer.draw(bottomLine);
        drawer.draw(leftLine);        
        if(aimed) drawer.setColor(Color.RED);
        drawer.draw(circleHair);
        drawer.draw(circleDot);
    }
}