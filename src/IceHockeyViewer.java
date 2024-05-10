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
    private static final int WIN_SCORE = 2;
    private boolean isSoccerGame;

    // Sets the backgrounds to image variables
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

    // Constants for location on screen
    private static final int MAX_WIDTH = 1250;
    private static final int MAX_HEIGHT = 920;
    private static final int SCOREBOARD_HEIGHT = 190;

    // Constructor
    public IceHockeyViewer(IceHockey ih) {
        // Gets player and puck object from game parameter
        // Sets the game to the game parameter
        playerOne = ih.getP1();
        playerTwo = ih.getP2();
        thePuck = ih.getPuck();
        game = ih;
        gameOver = false;
        isSoccerGame = ih.getIsSoccer();

        // Window variables
        this.setTitle("Macbook AIR HOCKEY");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_WIDTH, MAX_HEIGHT);
        this.setVisible(true);
        createBufferStrategy(2);
    }

    // Paint method, calls myPaint method to repaint the window
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

    // Repaints the window
    public void myPaint(Graphics g) {
        isSoccerGame = game.getIsSoccer();

        // Checks if the puck should bounce off of a player, puck bounces if so
        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

        // If the gameStage is in the first stage, output home screen
        if (game.getGameStage() == 0) {
            g.drawImage(homeScreen, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
            playerOne.setScore(0);
            playerTwo.setScore(0);
            return;
        }
        // If the gameStage is in the second stage, output instructions screen
        else if (game.getGameStage() == 1) {
            g.drawImage(instructions, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
            return;
        }

        // Checks for puck bounce off of player
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

        // Checks for puck bounce off of player
        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

        // Checks for puck bounce off player, moves puck, checks for puck wall bounce
        thePuck.wallBounce(20, MAX_WIDTH - 10, SCOREBOARD_HEIGHT - 5, MAX_HEIGHT - 50);
        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);
        thePuck.move();

        thePuck.playerBounce(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), true);
        thePuck.playerBounce(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), false);

        // Draws the puck and players, images depend on if game is soccer of hockey mode
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

        // Checks to see if the game is won yet or not
        game.scoreCheck();

        // If the game is won, output the winscreen for the winning player
         if (game.getGameStage() == 3) {
            if (playerOne.getScore() >= WIN_SCORE) {
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

         // Prints out the updated score on the screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("bold", Font.BOLD, 95));
        g.drawString("" + playerOne.getScore(), 522, 110);
        g.drawString("" + playerTwo.getScore(), 665, 110);

    }


}
