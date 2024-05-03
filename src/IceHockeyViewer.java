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
    private boolean isSoccerGame;

    private static final Image homeScreen = new ImageIcon("Resources/screen1.png").getImage();
    private static final Image instructions = new ImageIcon("Resources/instructions.png").getImage();
    private static final Image gameBackground = new ImageIcon("Resources/game.png").getImage();
    private static final Image soccerBackground = new ImageIcon("Resources/soccer.png").getImage();
    private static final Image oneWins = new ImageIcon("Resources/p1wins.png").getImage();
    private static final Image twoWins = new ImageIcon("Resources/p2wins.png").getImage();
    private static final Image oneWinsSoc = new ImageIcon("Resources/p1winsSoc.png").getImage();
    private static final Image twoWinsSoc = new ImageIcon("Resources/p2winsSoc.png").getImage();
    private static final Image ronaldo = new ImageIcon("Resources/ronaldo.png").getImage();
    private static final Image messi = new ImageIcon("Resources/messi.png").getImage();

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
        isSoccerGame = ih.getIsSoccer();

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
        isSoccerGame = game.getIsSoccer();

        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

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

        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

        // Clear window
        g.setColor(Color.black);
        g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
        if (!isSoccerGame) {
            g.drawImage(gameBackground, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
        }
        else {
            g.drawImage(soccerBackground, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
        }

        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

        thePuck.wallBounce(20, MAX_WIDTH - 10, SCOREBOARD_HEIGHT - 5, MAX_HEIGHT - 50);
        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);
        thePuck.move();

        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

        thePuck.draw(g, this);
        if (isSoccerGame) {
            playerOne.drawSoccerPlayer(g, ronaldo, this);
            playerTwo.drawSoccerPlayer(g, messi, this);
        }
        else {
            playerOne.drawPlayer(g);
            playerTwo.drawPlayer(g);
        }

        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

        game.scoreCheck();

         if (game.getGameStage() == 3) {
            if (playerOne.getScore() >= 3) {
                if (isSoccerGame) {
                    g.drawImage(oneWinsSoc, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
                }
                else {
                    g.drawImage(oneWins, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
                }
                thePuck.setDx(0);
                thePuck.setDy(0);
                gameOver = true;
            }
            else {
                if (isSoccerGame) {
                    g.drawImage(twoWinsSoc, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
                }
                else {
                    g.drawImage(twoWins, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
                }
                gameOver = true;
            }
             thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
             thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("bold", Font.BOLD, 95));
        g.drawString("" + playerOne.getScore(), 522, 110);
        g.drawString("" + playerTwo.getScore(), 665, 110);

    }


}
