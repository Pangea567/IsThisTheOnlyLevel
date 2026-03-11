// name surname: Ömer Faruk Karagöz
// student ID: 2023400255

import java.awt.*;

public class Map {

    private Stage stage;
    private Player player;

    private int[][] obstacles = {
            new int[]{0, 120, 120, 270}, new int[]{0, 270, 168, 330},
            new int[]{0, 330, 30, 480}, new int[]{0, 480, 180, 600},
            new int[]{180, 570, 680, 600}, new int[]{270, 540, 300, 570},
            new int[]{590, 540, 620, 570}, new int[]{680, 510, 800, 600},
            new int[]{710, 450, 800, 510}, new int[]{740, 420, 800, 450},
            new int[]{770, 300, 800, 420}, new int[]{680, 240, 800, 300},
            new int[]{680, 300, 710, 330}, new int[]{770, 180, 800, 240},
            new int[]{0, 120, 800, 150}, new int[]{560, 150, 800, 180},
            new int[]{530, 180, 590, 210}, new int[]{530, 210, 560, 240},
            new int[]{320, 150, 440, 210}, new int[]{350, 210, 440, 270},
            new int[]{220, 270, 310, 300}, new int[]{360, 360, 480, 390},
            new int[]{530, 310, 590, 340}, new int[]{560, 400, 620, 430}};

    // Button Coordinates
    private int[] button = new int[]{400, 390, 470, 410};

    // Button Floor Coordinates
    private int[] buttonFloor = new int[]{400, 390, 470, 400};

    // Start Pipe Coordinates for Drawing
    private int[][] startPipe = {new int[]{115, 450, 145, 480}, new int[]{110, 430, 150, 450}};

    // Exit Pipe Coordinates for Drawing
    private int[][] exitPipe = {new int[]{720, 175, 740, 215}, new int[]{740, 180, 770, 210}};

    // Coordinates of spike areas
    private int[][] spikes = {
            new int[]{30, 333, 50, 423,90}, new int[]{121, 150, 207, 170,180},
            new int[]{441, 150, 557, 170,180}, new int[]{591, 180, 621, 200,180},
            new int[]{750, 301, 769, 419,270}, new int[]{680, 490, 710, 510,0},
            new int[]{401, 550, 521, 570,0}};

    // Door Coordinates
    private int[] door = new int[]{685, 180, 700, 240};

    private int buttonPressNum = 0;
    private boolean isDoorOpen = false;

    // Spawnpoint
    int[] spawnPoint = new int[]{(startPipe[0][0] + startPipe[0][2])/2, (startPipe[0][1] + startPipe[0][3])/2};


    private boolean isButtonPressed = false;

    private boolean playerJustDied = false;
    private boolean playerHitSpike = false;



    //CONSTRUCTOR

    public Map(Stage stage, Player player){

        this.stage = stage;
        this.player = player;
    }



    // MOVE PLAYER in the map
    public void movePlayer(char direction) {
        playerHitSpike = false;     //to reset its value

        double locationX = player.getX();
        double locationY = player.getY();

        double velocityX = stage.getVelocityX();
        double velocityY = stage.getVelocityY();
        double gravity = stage.getGravity();


        if (direction == 'L') {
            locationX -= velocityX;
            player.image = "misc/ElephantLeft.png";
        } else if (direction == 'R') {
            locationX += velocityX;
            player.image = "misc/ElephantRight.png";

        }
        else if (direction == 'U') {
            // it can only jump when it is on the ground
            if (isPlayerOnGround()) {

                player.setVelocityY(stage.getVelocityY());
            }
            return;
        }


        // Obstacle check
        for (int[] obs : obstacles) {
            if (checkCollision(locationX, locationY, obs)) {
                return; //
            }
        }

        // Flag to check if collision with ground occurred
        boolean collidedWithGround = false;

        // Check obstacle collision
        for (int[] obstacle : obstacles) {
            if (checkCollision(player.getX(), locationY, obstacle)) {
                // If moving downward and colliding, snap to top of obstacle
                if (velocityY < 0) {
                    locationY = obstacle[3] + 10; // Place player exactly on top of obstacle
                    velocityY = 0;
                    collidedWithGround = true;
                }
                // If moving upward and colliding, snap to bottom of obstacle
                else if (velocityY > 0) {
                    locationY = obstacle[1] - 10; // Place player exactly below obstacle
                    velocityY = 0;
                }
                break;
            }
        }

        // Update location of the player if there is no collision
        player.setX(locationX);
        player.setY(locationY);


    }

    // check if the player is on the ground
    public boolean isPlayerOnGround() {
        // Check if player is on any obstacle (ground)
        double feetY = player.getY() - 10; // Bottom of the player

        for (int[] obstacle : obstacles) {
            // Check if player's feet are just above an obstacle surface
            if (player.getX() >= obstacle[0] && player.getX() <= obstacle[2] &&
                    Math.abs(feetY - obstacle[3]) < 2) { // Small tolerance for collision
                return true;
            }
        }
        return false;
    }

    // checks collisions
    private boolean checkCollision(double locationX, double locationY, int[] obstacle) {
        double playerLeft = locationX - 10;
        double playerRight = locationX + 10;
        double playerBottom = locationY - 10;
        double playerTop = locationY + 10;

        int obsLeft = obstacle[0];
        int obsBottom = obstacle[1];
        int obsRight = obstacle[2];
        int obsTop = obstacle[3];

        // AABB (Axis-Aligned Bounding Box) Collision
        boolean horizontalOverlap = playerRight > obsLeft && playerLeft < obsRight;
        boolean verticalOverlap = playerTop > obsBottom && playerBottom < obsTop;

        return horizontalOverlap && verticalOverlap;
    }

    public void checkSpikeCollision() {

        for (int[] spike : spikes) {
            if (checkCollision(player.getX(), player.getY(), spike)) {
                if (stage.getStageNumber() == 4) { // Stage 5 (0-index)
                    player.respawn(spawnPoint);


                        isDoorOpen = true; // if it collides with spike open the door
                    System.out.println("a");
                    }

                else {
                    System.out.println("b");

                    player.respawn(spawnPoint);
                    playerHitSpike = true;
                    return;
                }
            }
        }
    }

    public boolean playerHitSpike() {
        if (playerHitSpike && !playerJustDied) {
            playerJustDied = true;
            return true;
        } else if (!playerHitSpike) {
            playerJustDied = false; // reset to it is after death situation
        }
        return false;
    }

    //Applies gravity to the player, allowing falling and jumping behavior.
    public void applyGravity() {
        double locationY = player.getY();
        double velocityY = player.getVelocityY();
        double gravity = stage.getGravity();

        velocityY += gravity;
        locationY += velocityY;

        // Flag to control ground impact
        boolean collidedWithGround = false;

        for (int[] obstacle : obstacles) {
            if (checkCollision(player.getX(), locationY, obstacle)) {
                // If it is moving downwards (falling)
                if (velocityY < 0) {
                    locationY = obstacle[3] + 10; // Place player on obstacle

                    velocityY = 0;
                    collidedWithGround = true;
                }
                // If it moves upwards (jumps)
                else if (velocityY > 0) {
                    locationY = obstacle[1] - 10; // Place player under obstacle
                    velocityY = 0;

                }
                break;
            }
        }


        // Update player position and velocity
        player.setY(locationY);
        player.setVelocityY(velocityY);

        // Handle the special stage where player jumps automatically
        if (stage.getStageNumber() == 2 && collidedWithGround) {
            player.setVelocityY(stage.getVelocityY());
        }
    }


    public boolean changeStage() {
        return isDoorOpen && checkCollision(player.getX(), player.getY(), exitPipe[1]);
    }

    // check if the button is pressed
    public void checkButtonPress() {
        if (checkCollision(player.getX(), player.getY(), button)) {
            if (stage.getStageNumber() == 4){
                isDoorOpen = false;
                return;
        }
            if (!isButtonPressed) {
                pressButton();

            }
        } else {
            isButtonPressed = false; // to repress the button
        }


    }


    public void pressButton() {
        Player p = this.player;
        double px = p.getX();
        double py = p.getY();
            isButtonPressed = true;
            isDoorOpen = stage.getStageNumber()!= 3 ? true: buttonPressNum>=5;     // after pressing more than 5 times it becomes opened
            buttonPressNum++;
    }

    //Resets the map to its initial state
    public void restartStage() {
        // Player returns to spawn point
        this.player.respawn(new int[]{120, 460}); // Spawn point

        System.out.println(stage.getStageNumber()!=4);
        if (stage.getStageNumber()!=4) isDoorOpen = false;

        //Door and button state reset
        button[1] = buttonFloor[1];

        // Button press count reset
        buttonPressNum = 0;
    }


    public Stage getState(){
        return stage;
    }

    public Player getPlayer() {
        return player;
    }

    public void draw() {
        StdDraw.clear();

        // Timer area (buttom part)
        StdDraw.setPenColor(new Color(56, 93, 172)); // Mavi renkli timer alanı
        StdDraw.filledRectangle(400, 60, 400, 60); // Timer alanını çiz

        // Clue text
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(400, 85, "Clue:");
        StdDraw.text(400, 55, stage.getClue());

        // Help, Restart, Reset buttons
        StdDraw.text(250, 85, "Help");
        StdDraw.rectangle(250, 85, 40, 15);

        StdDraw.text(550, 85, "Restart");
        StdDraw.rectangle(550, 85, 40, 15);

        StdDraw.text(400, 20, "RESET THE GAME");
        StdDraw.rectangle(400, 20, 80, 15);


        StdDraw.setPenColor(stage.getColor()); // random color for every stage
        for (int[] obs : obstacles) {
            double centerX = (obs[0] + obs[2]) / 2.0;
            double centerY = (obs[1] + obs[3]) / 2.0;
            double halfWidth = (obs[2] - obs[0]) / 2.0;
            double halfHeight = (obs[3] - obs[1]) / 2.0;

            StdDraw.filledRectangle(centerX, centerY, halfWidth, halfHeight);
        }


        // door drawing
        // draw if the door is closed
        if (!isDoorOpen) {
            StdDraw.setPenColor(Color.GRAY);
            double doorCenterX = (door[0] + door[2]) / 2.0;
            double doorCenterY = (door[1] + door[3]) / 2.0;
            double doorHalfWidth = (door[2] - door[0]) / 2.0;
            double doorHalfHeight = (door[3] - door[1]) / 2.0;
            StdDraw.filledRectangle(doorCenterX, doorCenterY, doorHalfWidth, doorHalfHeight);
        } else {
            // if the door is open (like animation effect)
            StdDraw.setPenColor(Color.GRAY);
            double doorCenterX = (door[0] + door[2]) / 2.0;
            double doorCenterY = door[1] - (door[3] - door[1]) / 2.0; // pull down the door
            double doorHalfWidth = (door[2] - door[0]) / 2.0;
            double doorHalfHeight = (door[3] - door[1]) / 2.0;
            StdDraw.filledRectangle(doorCenterX, doorCenterY, doorHalfWidth, doorHalfHeight);
        }


        // Buton çizimi
        if (!isButtonPressed) {

            // if button is not pressed , show the red part of the button
            StdDraw.setPenColor(Color.RED);
            double buttonCenterX = (button[0] + button[2]) / 2.0;
            double buttonCenterY = (button[1] + button[3]) / 2.0;
            double buttonHalfWidth = (button[2] - button[0]) / 2.0;
            double buttonHalfHeight = (button[3] - button[1]) / 2.0;
            StdDraw.filledRectangle(buttonCenterX, buttonCenterY, buttonHalfWidth, buttonHalfHeight);
        } else {
            // If the button is pressed, only the white background is visible (button is pushed in)
            // We don't draw anything in particular here, because the white color behind it will be visible
        }

        //  Grey floor under the button - this should always be drawn
        StdDraw.setPenColor(Color.DARK_GRAY);
        double floorCenterX = (buttonFloor[0] + buttonFloor[2]) / 2.0;
        double floorCenterY = (buttonFloor[1] + buttonFloor[3]) / 2.0;
        double floorHalfWidth = (buttonFloor[2] - buttonFloor[0]) / 2.0;
        double floorHalfHeight = (buttonFloor[3] - buttonFloor[1]) / 2.0;
        StdDraw.filledRectangle(floorCenterX, floorCenterY, floorHalfWidth, floorHalfHeight);

        // Start Pipe
        StdDraw.setPenColor(Color.GREEN);
        for (int[] pipe : startPipe) {
            double startPipeCenterX = (pipe[0] + pipe[2]) / 2.0;
            double startPipeCenterY = (pipe[1] + pipe[3]) / 2.0;
            double startPipeHalfWidth = (pipe[2] - pipe[0]) / 2.0;
            double startPipeHalfHeigth = (pipe[3] - pipe[1]) / 2.0;
            StdDraw.filledRectangle(startPipeCenterX, startPipeCenterY, startPipeHalfWidth, startPipeHalfHeigth);
        }

        // Exit Pipe
        StdDraw.setPenColor(Color.YELLOW);
        for (int[] pipe : exitPipe) {
            double exitPipeCenterX = (pipe[0] + pipe[2]) / 2.0;
            double exitPipeCenterY = (pipe[1] + pipe[3]) / 2.0;
            double exitPipeHalfWidth = (pipe[2] - pipe[0]) / 2.0;
            double exitPipeHalfHeight = (pipe[3] - pipe[1]) / 2.0;
            StdDraw.filledRectangle(exitPipeCenterX, exitPipeCenterY, exitPipeHalfWidth, exitPipeHalfHeight);
        }

        // Spikes
        StdDraw.setPenColor(Color.PINK);
        for (int[] spike : spikes) {
            double spikeCenterX = (spike[0] + spike[2]) / 2.0;
            double spikeCenterY = (spike[1] + spike[3]) / 2.0;
            double spikeHalfWidth = (spike[2] - spike[0]) / 2.0;
            double spikeHalfHeight = (spike[3] - spike[1]) / 2.0;
            if (spike[4] == 90 || spike[4] == 270) {
                StdDraw.picture(spikeCenterX, spikeCenterY, "misc/Spikes.png", 2 * spikeHalfHeight, 2 * spikeHalfWidth, spike[4]);
            }
            else{
                StdDraw.picture(spikeCenterX, spikeCenterY, "misc/Spikes.png", 2 * spikeHalfWidth, 2 * spikeHalfHeight, spike[4]);

            }

        }


        // Draw the player
        player.draw();

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

