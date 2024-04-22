import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Stevie K. Halprin
// 4/7/2024
public class Player {
    // Instance variables
    private String name;
    private int x;
    private int y;
    private int width;
    private int score;
    private Color kit;
    private boolean isP1;
    private int step;

    // Constructor
    public Player (String name, int x, int y, Color kit, boolean isP1) {
        this.name = name;
        this.width = 90;
        this.score = -1;
        this.x = x;
        this.y = y;
        this.kit = kit;
        this.isP1 = isP1;
        step = 65;
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }
    public int getWidth() {
        return this.width;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int newScore) {
        this.score = newScore;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getStep() {
        return step;
    }
    public void setX(int newX) {
        this.x = newX;
    }
    public void setY(int newY) {
        this.y = newY;
    }

    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();

        if (isP1)
        {
            if (keyCode == KeyEvent.VK_A)
            {
                if (x > 10 + step) {
                    x -= step;
                }
            }
            else if (keyCode == KeyEvent.VK_D)
            {
                if (x < 1240 - step) {
                    x += step;
                }
            }
            else if (keyCode == KeyEvent.VK_W)
            {
                if (y > 160 + step) {
                    y -= step;
                }
            }
            else if (keyCode == KeyEvent.VK_S)
            {
                if (y < 880 - step) {
                    y += step;
                }
            }
        }
        else {
            if (keyCode == KeyEvent.VK_LEFT)
            {
                if (x > 10 + step) {
                    x -= step;
                }
            }
            else if (keyCode == KeyEvent.VK_RIGHT)
            {
                if (x < 1240 - step) {
                    x += step;
                }
            }
            else if (keyCode == KeyEvent.VK_UP)
            {
                if (y > (160 + step)) {
                    y -= step;
                }
            }
            else if (keyCode == KeyEvent.VK_DOWN)
            {
                if (y < 880 - step) {
                    y += step;
                }
            }
        }
    }

    public void drawPlayer(Graphics g) {
        g.setColor(kit);
        g.fillOval(x - width/2, y - width/2, width, width);
    }

}
