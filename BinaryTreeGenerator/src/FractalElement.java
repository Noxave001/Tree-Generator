import java.awt.Graphics;

/**
 * Interface required for the drawing of all tree elements
 *
 * @author Ace Breaux
 * @version 6-8-2025
 */
public interface FractalElement {

    /**
     * Prompt the element to draw itself on a given graphics panel
     * @param g the graphics panel in which to draw on
     */
    public void draw(Graphics g);
}
