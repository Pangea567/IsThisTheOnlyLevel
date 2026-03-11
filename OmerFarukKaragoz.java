// name surname: Ömer Faruk Karagöz
// student ID: 2023400255


import java.util.*;
import java.awt.event.KeyEvent;

public class OmerFarukKaragoz {
    public static void main(String[] args) {

        // Configure StdDraw canvas
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 600);
        StdDraw.enableDoubleBuffering();

        // Initialize list of stages
        ArrayList<Stage> stages = new ArrayList<>();
        stages.add(new Stage(-0.45, 3.65, 10, 0, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Arrow keys are required", "Arrow keys move player, press button and enter the second pipe"));

        stages.add(new Stage(-0.45, 3.65, 10, 1, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
                "Not always straight forward", "Right and left buttons reversed"));

        stages.add(new Stage(-2, 3.65, 24, 2, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, -1,
                "A bit bouncy here", "You jump constantly"));

        stages.add(new Stage(-0.45, 3.65, 10, 3, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Never gonna give you up", "Press button 5 times"));

        stages.add(new Stage(-0.45, 3.65, 10, 4, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Think before doing", "spike opens the door "));

        // Create and run the game
        Game game = new Game(stages);
        game.play();
    }
}
