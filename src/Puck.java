import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

// Stevie K. Halprin
// 4/7/2024
public class Puck {
    // Instance variables
    private double x;
    private double y;
    private double dx;
    private double dy;
    private int radius;
    private double distance;
    private boolean isSoccer;

    // Constants based on locations on screen
    private static final int MAX_WIDTH = 1240;
    private static final int CENTER_X = 623;
    private static final int CENTER_Y = 529;
    private static final int GOAL_Y = 440;
    private static final int GOAL_WIDTH = 175;

    // Instance variables for the puck (or ball) being drawn on the screen
    private static final Color shade = Color.BLACK;
    private static final Image ball = new ImageIcon("Resources/soccerBall.png").getImage();

    private static final double EXIT_C = 55.0;

    // Constructor
    public Puck(boolean isSoccerBall) {
        x = CENTER_X;
        y = CENTER_Y;
        isSoccer = isSoccerBall;
        dx = -10;
        dy = -10;
        radius = 30;
    }

    // Getters and setters
    public boolean getIsInGoalOne() {
        return x < (0 - radius);
    }
    public boolean getIsInGoalTwo() {
        return x > (MAX_WIDTH + radius);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setDx(int newDx) {
        dx = newDx;
    }
    public void setDy(int newDy) {
        dy = newDy;
    }
    public void setIsSoccer(boolean isSoc) {
        isSoccer = isSoc;
    }


    // Checks to see if the puck is in contact with the wall, if so then bounce in the opposite direction
    public void wallBounce(int xLow, int xHigh, int yLow, int yHigh) {
        // Check for a y bounce
        if ((y - radius <= yLow && dy < 0) || (y + radius >= yHigh && dy > 0)) {
            dy = -dy;
        }
        // Check if puck is on same horizontal level as goals, if so don't look for x bounce
        if (y < GOAL_Y + GOAL_WIDTH && y > GOAL_Y) {
            return;
        }
        // Check for an x bounce
        if ((x - radius <= xLow && dx < 0) || (x + radius >= xHigh && dx > 0)) {
            dx = -dx;
        }
    }

    // Checks if the puck has collided with a player
    public void playerBounce(int playerX, int playerY, int playerWidth, boolean isP1) {
        // Check for an x bounce
        double xDif = playerX - x;
        double yDif = playerY - y;
        distance = Math.sqrt(xDif * xDif + yDif * yDif);

        // If the distance is within the player radius, then bounce toward the goal of the opposing player
        // Puck will deflect in random direction with random speed in this direction
        if (distance < radius + playerWidth + 10 && distance > radius + playerWidth - 8) {
            dx = Math.random() * EXIT_C - EXIT_C / 2 + 1;
            dy = Math.random() * EXIT_C - EXIT_C / 2 + 1;

            if (isP1) {
                dx = Math.abs(dx);
            }
            else {
                dx = -Math.abs(dx);
            }
        }
    }

    // Updates the position of the puck
    public void move() {
        x += dx;
        y += dy;
    }

    // Resets the puck to the center of the screen
    public void resetPuck() {
        x = CENTER_X;
        y = CENTER_Y;
    }

    // Draws the puck on the screen
    // Draws a black circle if in hockey mode, draws a soccer ball if in soccer mode
    public void draw(Graphics g, ImageObserver backEnd) {
        if (!isSoccer) {
            g.setColor(shade);
            g.fillOval((int)x - radius, (int)y - radius, 2 * radius, 2 * radius);
        }
        else {
            g.drawImage(ball, (int)x - radius, (int)y - radius, radius * 2, radius * 2, backEnd);
        }
    }
}
