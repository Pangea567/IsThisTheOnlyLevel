// name surname: Ömer Faruk Karagöz
// student ID: 2023400255

public class Player {

    private double x;           // Player's X position (center)
    private double y;           // Player's Y position (center)
    private final double width; // Width of the player image
    private final double height;// Height of the player image
    private double velocityY;   // Vertical speed for jumping/falling

    public String image = "misc/ElephantRight.png";   // Player's image

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 20;
        this.velocityY = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

     //Resets the player's position and vertical velocity to the given spawn point.
    public void respawn(int[] spawnPoint) {
        this.x = spawnPoint[0];
        this.y = spawnPoint[1];
        this.velocityY = 0;
    }

    public void draw() {
        StdDraw.picture(x, y, image, width, height);
    }
}
