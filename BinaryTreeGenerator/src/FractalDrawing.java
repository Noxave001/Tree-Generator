import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Draws the fractal tree using data received from the subject
 *
 * @author Ace Breaux
 * @version 6-8-2025
 */
public class FractalDrawing extends JFrame implements FractalObserver {

    /** A list containing the fractal elements that compose the fractal tree */
    private static ArrayList<FractalElement> elements;

    /** The background image of the panel */
    private static Image backgroundImage = null;

    /** The FractalSubject of this observer */
    private final FractalSubject subj;

    /**
     * Instantiates the panel on which to draw the fractal tree
     *
     * @param subj the FractalSubject of this observer
     */
    public FractalDrawing(FractalSubject subj) {
        // Set the subject of the drawing window
        if (subj == null) {
            throw new IllegalArgumentException("Error: Subject cannot be null");
        }
        this.subj = subj;
        subj.attach(this);

        // Set initial options for the window
        setSize(850, 650);
        setLocation(775, 250);
        setTitle("Fractal Output");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Make the drawing area and add it to the window
        JPanel drawPanel = new DrawArea();
        getContentPane().add(drawPanel);

        // Get fractal data from the subject
        elements = subj.getFractalData();

        // Set the window to be visible
        setVisible(true);
    }

    /**
     * Updates the drawing area to reflect any slider changes
     */
    public void update() {
        elements = subj.getFractalData();
        this.repaint();
    }

    /**
     * Custom drawing area to generate the fractal tree
     */
    private static class DrawArea extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Set background of the panel
            try {
                backgroundImage = ImageIO.read(new File("fractalBackground.jpg"));
            } catch (IOException e) {
                // Do nothing if image is not found
            }
            g.drawImage(backgroundImage, 0, 0, null);

            // Draw the fractal tree
            for (FractalElement element : elements) {
                element.draw(g);
            }
        }
    }
}
