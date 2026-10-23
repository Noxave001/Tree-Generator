import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Record blueprint for a tree branch
 *
 * @author Ace Breaux
 * @version 6-8-2025
 *
 * @param x1 the first x-coordinate of the branch
 * @param x2 the second x-coordinate of the branch
 * @param y1 the first y-coordinate of the branch
 * @param y2 the second y-coordinate of the branch
 * @param color the color of the branch
 * @param thickness the thickness of the branch
 */
public record Branch(double x1, double x2,
                     double y1, double y2,
                     Color color, double thickness) implements FractalElement {
    /**
     * Prompt the branch to draw itself on a given graphics panel
     * @param g the graphics panel in which to draw on
     */
    public void draw(Graphics g) {
        Stroke stroke = new BasicStroke((float) thickness,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        g.setColor(color);
        ((Graphics2D) g).setStroke(stroke);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }
}
