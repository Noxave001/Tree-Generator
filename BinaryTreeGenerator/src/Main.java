/**
 * Launches the fractal GUI and the fractal drawing window, demonstrating the project
 *
 * @author Ace Breaux
 * @version 6-8-2025
 */
public class Main {

    /**
     * Launches the windows
     *
     * @param args not meant to be initialized
     */
    public static void main(String[] args) {
        FractalGenerator gen = new FractalGenerator();

        new FractalGui(gen);
        new FractalDrawing(gen);
    }
}
