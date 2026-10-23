import java.awt.Color;
import java.util.ArrayList;

/**
 * Interface required for all fractal subjects and their communication between observers
 *
 * @author Ace Breaux
 * @version 6-8-2025
 */
public interface FractalSubject {

    /**
     * Add an observer to the subject's update list
     * @param obs the observer to attach
     */
    public void attach(FractalObserver obs);

    /**
     * Remove an observer to the subject's update list
     * @param obs the observer to detach
     */
    public void detach(FractalObserver obs);

    /**
     * Ping all observers with an update
     */
    public void notifyObservers();

    /**
     * Retrieves the elements in the fractal tree
     *
     * @return a list of all FractalElements that the tree is composed of
     */
    public ArrayList<FractalElement> getFractalData();

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
                           Color trunkColor, Color leafColor);
}
