import java.awt.*;
import java.awt.geom.*;

public class CrossHairView { //class declaration for a class that defines the visual for the crossHairs on the shooter game
    
    private Line2D.Double topLine; //declarations for the 4 lines of the crosshairs
    private Line2D.Double rightLine;
    private Line2D.Double bottomLine;
    private Line2D.Double leftLine;
    private Ellipse2D.Double circleHair; //declaration for the circle around the center target
    private Ellipse2D.Double circleDot; //declaration for the very small circle where the pointer is targeted
    
    public CrossHairView() { //constructor
        topLine = new Line2D.Double(0, 0, 0, 0); //default all value locations and dimensions to 0
        rightLine = new Line2D.Double(0, 0, 0, 0 );
        bottomLine = new Line2D.Double(0, 0, 0, 0);
        leftLine = new Line2D.Double(0, 0, 0, 0);
        circleHair = new Ellipse2D.Double(0, 0, 0, 0);
        circleDot = new Ellipse2D.Double(0, 0, 0, 0);
    }
    
    public void setLocation(int x, int y, boolean small) { //method declaration for a move method which moves the components of the crossHairs
        if(small) circleHair = new Ellipse2D.Double(x-15, y-15, 30, 30); //if small is true meaning circleHair should be small, create a new small Ellipse2D and set it equal to that
        else circleHair = new Ellipse2D.Double(x-20, y-20, 40, 40); //otherwise construct a slightly large ellipse2d
        circleDot = new Ellipse2D.Double(x-2, y-2, 4, 4); //initialize circledot with integer fields matching the x and y coordinates
        topLine.setLine(x, y-60, x, y-40); //initialize all four lines to newly created lines around the x and y coordinates given
        rightLine.setLine(x+60, y, x+40, y);
        bottomLine.setLine(x, y+60, x, y+40);
        leftLine.setLine(x-60, y, x-40, y);
    }
    
    public void draw(Graphics2D drawer, boolean aimed) { //method declaration for method that draws each part and takes in explicit parameter Graphics2D as drawing context and boolean whether it is aimed or not
        drawer.setColor(Color.BLACK); //set color to black
        drawer.draw(topLine); //draw each component by calling the draw method and passing in each one as explicit parameter
        drawer.draw(rightLine);
        drawer.draw(bottomLine);
        drawer.draw(leftLine);        
        if(aimed) drawer.setColor(Color.RED); //if the crossHairs are aimed at something draw the rest in red
        drawer.draw(circleHair);
        drawer.draw(circleDot);
    }
}