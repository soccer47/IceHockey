import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

// Stevie K. Halprin
// 4/7/2024
public class IceHockeyViewer extends JFrame {
    // Instance variables
    private Player playerOne;
    private Player playerTwo;
    private Puck thePuck;
    private IceHockey game;
    private boolean gameOver;

    private static final Image homeScreen = new ImageIcon("Resources/screen1.png").getImage();
    private static final Image instructions = new ImageIcon("Resources/instructions.png").getImage();
    private static final Image gameBackground = new ImageIcon("Resources/game.png").getImage();
    private static final Image oneWins = new ImageIcon("Resources/p1wins.png").getImage();
    private static final Image twoWins = new ImageIcon("Resources/p2wins.png").getImage();

    private static final int MAX_WIDTH = 1250;
    private static final int MAX_HEIGHT = 920;
    private static final int TOP_OF_WINDOW = 20;
    private static final int SCOREBOARD_HEIGHT = 190;
    private static final int GOAL_Y = 445;
    private static final int GOAL_WIDTH = 210;

    // Constructor
    public IceHockeyViewer(IceHockey ih) {
        playerOne = ih.getP1();
        playerTwo = ih.getP2();
        thePuck = ih.getPuck();
        game = ih;
        gameOver = false;

        this.setTitle("Macbook AIR HOCKEY");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_WIDTH, MAX_HEIGHT);
        this.setVisible(true);
        createBufferStrategy(2);
    }

    public void paint (Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;
        Graphics g2 = null;
        try {
            g2 = bf.getDrawGraphics();
            myPaint(g2);
        }
        finally {
            g2.dispose();
        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void myPaint(Graphics g) {

        if (game.getGameStage() == 0) {
            g.drawImage(homeScreen, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
            playerOne.setScore(0);
            playerTwo.setScore(0);
            return;
        }
        else if (game.getGameStage() == 1) {
            g.drawImage(instructions, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
            return;
        }

        // Clear window
        g.setColor(Color.black);
        g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
        g.drawImage(gameBackground, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);


        thePuck.wallBounce(20, MAX_WIDTH - 10, SCOREBOARD_HEIGHT - 5, MAX_HEIGHT - 40);
        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth());
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth());
        thePuck.move();

        thePuck.draw(g);
        playerOne.drawPlayer(g);
        playerTwo.drawPlayer(g);

        game.scoreCheck();

         if (game.getGameStage() == 3) {
            if (playerOne.getScore() >= 3) {
                g.drawImage(oneWins, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
                thePuck.setDx(0);
                thePuck.setDy(0);
                gameOver = true;
            }
            else {
                g.drawImage(twoWins, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
                gameOver = true;
            }

        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("bold", Font.BOLD, 95));
        g.drawString("" + playerOne.getScore(), 522, 110);
        g.drawString("" + playerTwo.getScore(), 665, 110);

    }


}
