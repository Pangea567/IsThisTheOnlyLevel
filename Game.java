// name surname: Ömer Faruk Karagöz
// student ID: 2023400255

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {

    private int stageIndex;                 // Current stage index
    private ArrayList<Stage> stages;        // List of all stages
    private int deathNumber;                // Total death count
    private double gameTime;
    private double resetTime;               // Time since game reset
    private boolean resetGame;              // Flag for full game reset

    // CONSTRUCTOR
    public Game(ArrayList<Stage> stages) {
        this.stages = stages;
        this.stageIndex = 0;
        this.deathNumber = 0;
        this.gameTime = 0;
        this.resetTime = System.currentTimeMillis();
        this.resetGame = false;
    }

    //Main game loop. Handles stage switching, deaths, drawing, and user input.
    public void play() {
        while (stageIndex < stages.size()) {
            Stage currentStage = stages.get(stageIndex);
            Player player = new Player(120, 460); // Initial spawn point
            Map map = new Map(currentStage, player);
            map.applyGravity(); // Drop player to the ground initially

            boolean stageComplete = false;

            while (!stageComplete) {
                StdDraw.clear();


                handleInput(map);               //handle user input

                map.applyGravity();             // Apply gravity to player

                map.checkSpikeCollision();      // Spike collision check
                if (map.playerHitSpike()) {
                    deathNumber++;
                    map.restartStage();

                }

                map.checkButtonPress();         // Check for button interaction

                map.draw();                         // Draw map and elements
                drawBottomBar(currentStage);        // Draw bottom info bar

                // check if stage is complete
                stageComplete = map.changeStage();

                if (stageComplete) {
                    StdDraw.show();             // Show map and buttom bar
                    showStagePassedBanner();   // Display banner
                } else {
                    StdDraw.show();
                }

                StdDraw.pause(33); // ~60 FPS
            }

            stageIndex++;
        }

        // Show final screen
        showEndGameBanner();
    }

    private void handleInput(Map map) {
        Stage currentStage = map.getState(); // get stage object
        Player player = map.getPlayer();
        int[] keys = currentStage.getKeyCodes();

        // Keyboard movement
        if (StdDraw.isKeyPressed(keys[0])) {
            map.movePlayer('R');
        }
        if (StdDraw.isKeyPressed(keys[1])) {
            map.movePlayer('L');
        }
        if (keys[2] == -1 || StdDraw.isKeyPressed(keys[2])) {
            map.movePlayer('U');
        }

        map.checkButtonPress();

        // Mouse input for buttons
        if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();

            // Help button (250,85,40,15)
            if (inBounds(mouseX, mouseY, 210, 70, 290, 100)) {
                System.out.println(currentStage.getHelp()); // Geçici çözüm
            }

            // Restart button (550,85,40,15)
            if (inBounds(mouseX, mouseY, 510, 70, 590, 100)) {
                map.restartStage();
                deathNumber++;
            }

            // Reset Game button (400,20,80,15)
            if (inBounds(mouseX, mouseY, 320, 5, 480, 35)) {
                stageIndex = 0;
                deathNumber = 0;
                resetTime = System.currentTimeMillis();
                resetGame = true;
            }
        }
    }

     // Checks whether a given coordinate is within button bounds.
    private boolean inBounds(double x, double y, double left, double bottom, double right, double top) {
        return x >= left && x <= right && y >= bottom && y <= top;
    }


     // Displays a temporary banner when a stage is completed.
    private void showStagePassedBanner() {
        // Arka planı temizlemiyoruz, harita zaten çizilmiş oluyor

        StdDraw.setPenColor(new Color(56, 172, 93)); // Banner rengi
        StdDraw.filledRectangle(400, 300, 400, 40);  // Yatay banner: ekran ortasına, yüksekliği 80px

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
        StdDraw.text(400, 320, "You passed the stage.");
        StdDraw.text(400, 280, "But is the level over?!");

        StdDraw.show();
        StdDraw.pause(2000);
    }


    // Displays the final end-game screen after completing all stages.
    private void showEndGameBanner() {
        // Ekranı tamamen temizle ve arka plan olarak koyu yeşil yap
        StdDraw.clear();
        StdDraw.setPenColor(new Color(34, 139, 34)); // Koyu yeşil
        StdDraw.filledRectangle(400, 300, 400, 300); // Tüm ekranı doldur

        StdDraw.setPenColor(Color.WHITE); // Yazılar beyaz
        StdDraw.setFont(new Font("Arial", Font.BOLD, 28));
        StdDraw.text(400, 360, "CONGRATULATIONS YOU FINISHED THE LEVEL");

        StdDraw.setFont(new Font("Arial", Font.BOLD, 22));
        StdDraw.text(400, 310, "You finished with " + deathNumber + " deaths in " + formattedTime());

        StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
        StdDraw.text(400, 250, "PRESS 'A' TO PLAY AGAIN!");
        StdDraw.text(400, 220, "Press 'Q' to quit.");

        StdDraw.show();

        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
                stageIndex = 0;
                deathNumber = 0;
                resetTime = System.currentTimeMillis();
                return;
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
                System.exit(0);
            }
        }
    }


    // Draws the bottom info bar with stage and death info.
    private void drawBottomBar(Stage currentStage) {
        StdDraw.setPenColor(new Color(56, 93, 172)); // Arka plan
        StdDraw.filledRectangle(400, 60, 400, 60);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 16));

        StdDraw.text(250, 85, "Help");
        StdDraw.rectangle(250, 85, 40, 15);

        StdDraw.text(550, 85, "Restart");
        StdDraw.rectangle(550, 85, 40, 15);

        StdDraw.text(400, 20, "RESET THE GAME");
        StdDraw.rectangle(400, 20, 80, 15);

        StdDraw.text(700, 75, "Deaths: " + deathNumber);
        StdDraw.text(700, 50, "Stage: " + (stageIndex + 1));
        StdDraw.text(100, 50, formattedTime());
        StdDraw.text(100, 75, "Level: 1");
        StdDraw.text(400, 85, "Clue:");
        StdDraw.text(400, 55, currentStage.getClue());
    }


    // Formats the elapsed time since game start in MM:SS:MS format.
    private String formattedTime() {
        long totalMillis = (long) (System.currentTimeMillis() - resetTime);
        long seconds = (totalMillis / 1000) % 60;
        long minutes = (totalMillis / 1000) / 60;
        long millis = totalMillis % 1000 / 10;
        return String.format("%02d:%02d:%02d", minutes, seconds, millis);
    }



    public int getStageIndex() {
        return stageIndex;
    }


    public Stage getCurrentStage() {
        return stages.get(stageIndex);
    }

}
