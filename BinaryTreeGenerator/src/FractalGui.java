import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates a working GUI with different ways to modify the appearance of a fractal tree
 *
 * @author Ace Breaux
 * @version 6-8-2025
 */
public class FractalGui extends JFrame {
    /** The amount of layers of branches on the tree (recursion depth) */
    private int recDepth = 14;
    /** The ratio of how large the child element is compared to its parent (%) */
    private int ctpRatio = 72;
    /** The angle of the left branches (in degrees) */
    private int leftChildAngle = 42;
    /** The angle of the right branches (in degrees) */
    private int rightChildAngle = 30;
    /** The length of the tree trunk */
    private int trunkLength = 185;
    /** The width of the tree trunk */
    private int trunkWidth = 40;

    /** The color of the tree trunk (the base of the tree) */
    private Color trunkColor = new Color(4, 13, 80);
    /** The color of the "leaves" of the tree (the last branches) */
    private Color leafColor = new Color(255, 0, 0);

    /** The background color of the sliders */
    private static final Color SLIDER_BACKGROUND_COLOR = new Color(27, 0, 48);
    /** The color of the buttons */
    private static final Color BUTTON_COLOR = new Color(78, 22, 12);
    /** The color of the outer slider oval */
    private static final Color SLIDER_COLOR = new Color(122, 40, 97);
    /** The color of the middle slider oval */
    private static final Color SLIDER_HIGHLIGHT_COLOR = new Color(228, 123, 55);
    /** The color of the inner slider oval */
    private static final Color SLIDER_HIGHLIGHT_COLOR_2 = new Color(236, 187, 86);
    /** The color of the text */
    private static final Color FONT_COLOR = new Color(251, 235, 206);

    /** The background image of the gui */
    private static Image backgroundImage = null;

    /**
     * Creates a GUI with sliders and buttons to modify the look of the fractal tree
     *
     * @param subj the FractalSubject to which the GUI belongs
     */
    public FractalGui(FractalSubject subj) {
        // Preconditions for the subject
        if (subj == null) {
            throw new IllegalArgumentException("Error: Subject cannot be null");
        }

        // ---------- PANEL INITIALIZATION ----------
        setSize(350, 650);
        setLocation(400, 250);
        setTitle("Fractal Settings");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel guiPanel = new GuiPanel(null);
        getContentPane().add(guiPanel);
        // ---------- ---------- ---------- ---------

        // ---------- ---------- SLIDERS ---------- ----------
        // Recursion depth
        makeLabel("Recursion Depth", 117, 5, guiPanel);
        JSlider recDepthSlider = makeSlider(4, 20, recDepth, 1, 2,
                40, guiPanel, "recDepthSlider");

        // Child-to-parent ratio
        makeLabel("Child To Parent Ratio", 100, 85, guiPanel);
        JSlider ctpSlider = makeSlider(40, 80, ctpRatio, 5, 10,
                120, guiPanel, "ctpSlider");

        // Left child angle
        makeLabel("Left Child Angle", 115, 160, guiPanel);
        JSlider leftChildAngleSlider = makeSlider(0, 90, leftChildAngle, 5, 10,
                195, guiPanel, "leftChildAngleSlider");

        // Right child angle
        makeLabel("Right Child Angle", 115, 245, guiPanel);
        JSlider rightChildAngleSlider = makeSlider(0, 90, rightChildAngle, 5, 10,
                280, guiPanel, "rightChildAngleSlider");

        // Trunk length
        makeLabel("Trunk Length", 125, 330, guiPanel);
        JSlider trunkLengthSlider = makeSlider(100, 400, trunkLength, 25, 100,
                365, guiPanel, "trunkLengthSlider");

        // Trunk width
        makeLabel("Trunk Width", 125, 405, guiPanel);
        JSlider trunkWidthSlider = makeSlider(10, 50, trunkWidth, 5, 10,
                440, guiPanel, "trunkWidthSlider");

        // Slider events
        addSliderEvent(recDepthSlider, subj);
        addSliderEvent(ctpSlider, subj);
        addSliderEvent(leftChildAngleSlider, subj);
        addSliderEvent(rightChildAngleSlider, subj);
        addSliderEvent(trunkLengthSlider, subj);
        addSliderEvent(trunkWidthSlider, subj);
        // ---------- ---------- ---------- ---------- ----------

        // ---------- ---------- BUTTONS ---------- ----------
        // Trunk color
        JButton trunkColorButton = makeButton("Trunk Color", 43, 510, 120, 30, guiPanel);

        // Leaf color
        JButton leafColorButton = makeButton("Leaf Color", 173, 510, 120, 30, guiPanel);

        // Randomize button
        JButton randomizeButton = makeButton("Random", 116, 550, 100, 30, guiPanel);

        // Button events
        trunkColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JColorChooser();
                Color tempColor = JColorChooser.showDialog(null,
                        "Trunk Color Picker", trunkColor);

                // Handle what happens if the "cancel" button is pressed
                if (tempColor != null) {
                    trunkColor = tempColor;
                }

                setSubjectOptions(subj);
            }
        });

        leafColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JColorChooser();
                Color tempColor = JColorChooser.showDialog(null,
                        "Leaf Color Picker", leafColor);

                // Handle what happens if the "cancel" button is pressed
                if (tempColor != null) {
                    leafColor = tempColor;
                }

                setSubjectOptions(subj);
            }
        });

        randomizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random r = new Random();

                // Randomize slider values
                recDepth = r.nextInt(4, 21);
                ctpRatio = r.nextInt(40, 81);
                leftChildAngle = r.nextInt(0, 91);
                rightChildAngle = r.nextInt(0, 91);
                trunkLength = r.nextInt(100, 401);
                trunkWidth = r.nextInt(10, 51);

                // Randomize colors
                trunkColor = new Color(r.nextInt(1, 256),
                                r.nextInt(1, 256),
                                r.nextInt(1, 256));
                leafColor = new Color(r.nextInt(1, 256),
                                r.nextInt(1, 256),
                                r.nextInt(1, 256));

                // Add all sliders and their respective variables to a list to use below
                ArrayList<JSlider> sliders = new ArrayList<>(List.of(recDepthSlider, ctpSlider,
                        leftChildAngleSlider, rightChildAngleSlider, trunkLengthSlider, trunkWidthSlider));
                ArrayList<Integer> sliderVals = new ArrayList<>(List.of(recDepth, ctpRatio,
                        leftChildAngle, rightChildAngle, trunkLength, trunkWidth));

                // Update all sliders visually
                for (int i = 0; i < sliders.size(); i++) {
                    sliders.get(i).setValue(sliderVals.get(i));
                }

                setSubjectOptions(subj);
            }
        });
        // ---------- ---------- ---------- ---------- ----------

        // Initialize tree
        setSubjectOptions(subj);

        // Set the window to be visible
        setVisible(true);
    }

    /**
     * Makes a JLabel using the given values
     *
     * @param name the name of the label
     * @param x the x-coordinate of the label
     * @param y the y-coordinate of the label
     * @param panel the panel containing the label
     */
    public void makeLabel(String name, int x, int y, JPanel panel) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, 250, 50);
        label.setFont(new Font("Georgia", Font.BOLD, 12));
        label.setForeground(FONT_COLOR);
        panel.add(label);
    }

    /**
     * Make a new JSlider using the given values
     *
     * @param min the minimum bound of the slider
     * @param max the maximum bound of the slider
     * @param startValue the default/starting value of the slider
     * @param minorSpacing the spacing between minor ticks on the slider
     * @param majorSpacing the spacing between major ticks on the slider
     * @param y the y-coordinate of the slider on the panel
     * @param panel the panel containing the slider
     * @param name the name of the slider
     * @return the new slider
     */
    public JSlider makeSlider(int min, int max, int startValue, int minorSpacing,
                              int majorSpacing, int y, JPanel panel, String name) {
        JSlider slider = new JSlider(min, max, startValue);
        slider.setUI(new JSliderUI(slider));
        slider.setName(name);
        slider.setMinorTickSpacing(minorSpacing);
        slider.setMajorTickSpacing(majorSpacing);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBounds(42, y, 250, 50);
        slider.setFont(new Font("Year supply of fairy cakes", Font.PLAIN, 11));
        slider.setBackground(SLIDER_BACKGROUND_COLOR);
        slider.setForeground(FONT_COLOR);
        slider.setFocusable(false);
        panel.add(slider);

        return slider;
    }

    /**
     * Make a new JButton using the given values
     *
     * @param name the name of the button
     * @param x the x-position of the button on the panel
     * @param y the y-position of the button on the panel
     * @param width the width of the button
     * @param height the height of the button
     * @param panel the panel containing the button
     * @return the new button
     */
    public JButton makeButton(String name, int x, int y, int width, int height, JPanel panel) {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(FONT_COLOR);
        button.setFont(new Font("Georgia", Font.BOLD, 12));
        button.setFocusable(false);
        panel.add(button);

        return button;
    }

    /**
     * Relay all fractal settings to the subject
     *
     * @param subj the FractalSubject to send the values to
     */
    public void setSubjectOptions(FractalSubject subj) {
        subj.setOptions(recDepth, ctpRatio, leftChildAngle,
                rightChildAngle, trunkLength, trunkWidth,
                trunkColor, leafColor);
    }

    /**
     * Add a ChangeListener to the slider, making it function as intended
     *
     * @param slider the slider to attach a ChangeListener to
     * @param subj the FractalSubject of the GUI containing the slider
     */
    public void addSliderEvent(JSlider slider, FractalSubject subj) {
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Identify which slider was changed and get its new value
                JSlider source = (JSlider) e.getSource();
                String name = source.getName();
                int varChanged = slider.getValue();

                // Assign that new value to the corresponding global variable
                switch (name) {
                    case "recDepthSlider" -> recDepth = varChanged;
                    case "ctpSlider" -> ctpRatio = varChanged;
                    case "leftChildAngleSlider" -> leftChildAngle = varChanged;
                    case "rightChildAngleSlider" -> rightChildAngle = varChanged;
                    case "trunkLengthSlider" -> trunkLength = varChanged;
                    case "trunkWidthSlider" -> trunkWidth = varChanged;
                }

                // Update the fractal tree
                setSubjectOptions(subj);
            }
        });
    }

    /**
     * Custom panel for the GUI to allow for an image-based background
     */
    private static class GuiPanel extends JPanel {

        /**
         * Creates a GuiPanel using a LayoutManager (or null, in this project)
         *
         * @param layoutManager the LayoutManager to use on the panel
         */
        public GuiPanel(LayoutManager layoutManager) {
            super(layoutManager);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                backgroundImage = ImageIO.read(new File("GUIBackground.jpg"));
            } catch (IOException e) {
                // Do nothing
            }

            g.drawImage(backgroundImage, 0, 0, null);
        }
    }

    /**
     * Custom UI for the sliders
     */
    private static class JSliderUI extends BasicSliderUI {

        /**
         * Creates a JSliderUI using the parent's constructor
         *
         * @param slider the JSlider to create the UI with
         */
        public JSliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        protected Dimension getThumbSize() {
            return new Dimension(17, 17);
        }

        @Override
        public void paintTrack(Graphics g) {
            g.setColor(SLIDER_COLOR);
            g.fillRoundRect(0, 15,250, 12, 15, 25);
            g.setColor(SLIDER_HIGHLIGHT_COLOR);
            g.fillRoundRect(2, 16, 245, 9, 7, 20);
            g.setColor(SLIDER_HIGHLIGHT_COLOR_2);
            g.fillRoundRect(9, 18, 230, 5, 15, 25);
        }
    }
}
