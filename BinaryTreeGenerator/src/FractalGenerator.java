import java.util.ArrayList;
import java.awt.Color;

/**
 * Generates a fractal tree using user-defined values from a GUI
 *
 * @author Ace Breaux
 * @version 6-8-2025
 */
public class FractalGenerator implements FractalSubject {

    /** The list of observers to notify (any window containing the fractal tree) */
    private ArrayList<FractalObserver> observers = new ArrayList<>();
    /** The list of elements composing the fractal tree */
    private ArrayList<FractalElement> fractalData;

    /** The amount of layers of branches on the tree (recursion depth) */
    private int recDepth;
    /** The ratio of how large the child element is compared to its parent (%) */
    private double ctpRatio;
    /** The angle of the left branches (in degrees) */
    private double leftChildAngle;
    /** The angle of the right branches (in degrees) */
    private double rightChildAngle;
    /** The length of the tree trunk */
    private double trunkLength;
    /** The width of the tree trunk */
    private double trunkWidth;

    /** The color of the tree trunk (the base of the tree) */
    private Color trunkColor;
    /** The color of the "leaves" of the tree (the last branches) */
    private Color leafColor;

    /** The height of the drawing area */
    private final int PANEL_HEIGHT = 650;
    /** The width of the drawing area */
    private final int PANEL_WIDTH = 850;

    /**
     * Add an observer to the subject's update list
     *
     * @param obs the observer to attach
     */
    public void attach(FractalObserver obs) {
        if (obs == null) {
            throw new IllegalArgumentException("Error: Observer cannot be null");
        }
        observers.add(obs);
    }

    /**
     * Remove an observer to the subject's update list
     *
     * @param obs the observer to detach
     */
    public void detach(FractalObserver obs) {
        if (obs == null) {
            throw new IllegalArgumentException("Error: Observer cannot be null");
        }
        observers.remove(obs);
    }

    /**
     * Ping all observers with an update
     */
    public void notifyObservers() {
        for (FractalObserver obs : observers) {
            obs.update();
        }
    }

    /**
     * Generates the elements of the fractal tree
     *
     * @param recLevel the current depth of recursion (max level = total recursion depth)
     * @param x the x-coordinate of the branch's origin
     * @param y the y-coordinate of the branch's origin
     * @param angle the angle of the branch
     * @param thickness the thickness of the parent branch
     * @param length the length of the parent branch
     */
    private void generateFractalData(int recLevel, double x, double y, double angle, double thickness, double length) {
        // Base case
        if (recLevel == recDepth) {
            return;
        }

        double x2 = x + Math.cos(angle) * length;
        double y2 = y - Math.sin(angle) * length;

        double interpolationFraction = (double) 1 / recDepth * (recLevel - 1);

        // Add right branch
        fractalData.add(new Branch(x, x2, y, y2,
                interpolate(trunkColor, leafColor, interpolationFraction), thickness));
        // Add left branch
        fractalData.add(new Branch(x, x2, y, y2,
                interpolate(trunkColor, leafColor, interpolationFraction), thickness));

        // Generate right tree
        generateFractalData(recLevel + 1, x2, y2, angle - rightChildAngle,
                thickness * ctpRatio, length * ctpRatio);
        // Generate left tree
        generateFractalData(recLevel + 1, x2, y2, angle + leftChildAngle,
                thickness * ctpRatio , length * ctpRatio);
    }

    /**
     * Calculates the color at a certain point between two colors
     *
     * @param color1 the starting color
     * @param color2 the ending color
     * @param fraction the
     * @return
     */
    private Color interpolate(Color color1, Color color2, double fraction) {
        double inverseFraction = 1.0 - fraction;

        double red = (color1.getRed() * inverseFraction + color2.getRed() * fraction);
        double green = (color1.getGreen() * inverseFraction + color2.getGreen() * fraction);
        double blue = (color1.getBlue() * inverseFraction + color2.getBlue() * fraction);

        return new Color((int) red, (int) green, (int) blue);
    }


    /**
     * Retrieves the elements in the fractal tree
     *
     * @return a list of all FractalElements that the tree is composed of
     */
    public ArrayList<FractalElement> getFractalData() {
        fractalData = new ArrayList<>();
        generateFractalData(1, (double) PANEL_WIDTH / 2, PANEL_HEIGHT,
                Math.PI/2, trunkWidth, trunkLength);
        return fractalData;
    }

    /**
     * Sets the design choices for the fractal tree
     *
     * @param recDepth the amount of layers of branches on the tree (recursion depth)
     * @param ctpRatio the ratio of how large the child element is compared to its parent (%)
     * @param leftChildAngle the angle of the left branches (in degrees)
     * @param rightChildAngle the angle of the right branches (in degrees)
     * @param trunkLength the length of the tree trunk
     * @param trunkWidth the width of the tree trunk
     * @param trunkColor the color of the tree trunk (the base of the tree)
     * @param leafColor the color of the "leaves" of the tree (the last branches)
     */
    public void setOptions(int recDepth, int ctpRatio, int leftChildAngle,
                           int rightChildAngle, int trunkLength, int trunkWidth,
                           Color trunkColor, Color leafColor) {

        this.trunkColor = trunkColor;
        this.leafColor = leafColor;
        this.recDepth = recDepth;
        this.ctpRatio = (double) ctpRatio / 100;
        this.leftChildAngle = Math.toRadians(leftChildAngle);
        this.rightChildAngle = Math.toRadians(rightChildAngle);
        this.trunkLength = trunkLength;
        this.trunkWidth = trunkWidth;

        notifyObservers();
    }
}
