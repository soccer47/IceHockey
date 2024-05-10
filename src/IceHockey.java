import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



// Stevie K. Halprin
// 4/7/2024
public class IceHockey implements ActionListener, KeyListener {
    // Instance variables
    private Puck p;
    private Player P1;
    private Player P2;
    private IceHockeyViewer window;
    private boolean isSoccer;
    private int gameStage;

    // Locations on screen constants

    private static final int PLAYER_Y = 530;
    private static final int PLAYER_ONE_X = 325;
    private static final int PLAYER_TWO_X = 925;
    private static final int WIN_SCORE = 2;
    private static final int SLEEP_TIME = 70;


    // Main method
    public static void main(String args[]) {
        IceHockey hockey = new IceHockey();
        Timer clock = new Timer(SLEEP_TIME, hockey);
        clock.start();
    }

    // Constructor
    public IceHockey() {
        // Creates new puck and players
        p = new Puck(false);
        P1 = new Player("Player 1", PLAYER_ONE_X, PLAYER_Y, Color.RED, Color.WHITE,true, 7);
        P2 = new Player("Player 2", PLAYER_TWO_X, PLAYER_Y,Color.BLUE, Color.MAGENTA,false, 8);
        isSoccer = false;
        gameStage = 0;
        // Creates new window
        window = new IceHockeyViewer(this);

        // Adds KeyListener object
        window.addKeyListener(this);
    }

    // Getters
    public Player getP1() {
        return P1;
    }
    public Player getP2() {
        return P2;
    }
    public Puck getPuck() {
        return p;
    }

    public int getGameStage() {
        return gameStage;
    }
    public boolean getIsSoccer() {
        return isSoccer;
    }

    // Checks if game has been won yet, if so then change the gameStage accordingly
    public void gameCheck() {
        if (P1.getScore() >= WIN_SCORE || P2.getScore() >= WIN_SCORE) {
            gameStage = 3;
        }
    }

    // Checks if a goal has been scored by either player, updates players, score  and puck accordingly
    public void scoreCheck() {
        if (p.getIsInGoalOne()) {
            P2.setScore(P2.getScore() + 1);
            gameCheck();
            resetPos();
        }
        else if (p.getIsInGoalTwo()) {
            P1.setScore(P1.getScore() + 1);
            gameCheck();
            resetPos();
        }
    }

    // Resets the position of the puck and players to starting positions
    public void resetPos() {
        p.resetPuck();
        P1.setX(PLAYER_ONE_X);
        P2.setX(PLAYER_TWO_X);
        P1.setY(PLAYER_Y);
        P2.setY(PLAYER_Y);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    // User input for when there is a key typed
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // If the user inputs a space key, update the gameStage accordingly
        if (keyCode == KeyEvent.VK_SPACE)
        {
            if (gameStage == 2) {
                return;
            }
            else if (gameStage == 1 || gameStage == 3){
                gameStage = 2;
                P1.setScore(0);
                P2.setScore(0);
                p.resetPuck();
                p.setDx(-20);
                p.setDy(-20);
            }
            else if (gameStage == 0){
                gameStage = 1;
            }
        }
        // If 1 key pressed, change the mode from soccer to hockey
        if (keyCode == KeyEvent.VK_1)
        {
            if (gameStage == 2) {
                return;
            }
            else if (gameStage == 1 || gameStage == 3){
                isSoccer = true;
                p.setIsSoccer(true);
                gameStage = 2;
                P1.setScore(0);
                P2.setScore(0);
                p.resetPuck();
                p.setDx(-20);
                p.setDy(-20);
            }
            else if (gameStage == 0){
                isSoccer = true;
                p.setIsSoccer(true);
                gameStage = 1;
            }
        }

        // Transfers user input to player objects
        P1.keyPressed(e);
        P2.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    // Continues to repaint the window and update the puck, as long as the game isn't won yet
    @Override
    public void actionPerformed(ActionEvent e) {
        if (P1.getScore() < WIN_SCORE && P2.getScore() < WIN_SCORE) {
            p.move();
            window.repaint();
        }
    }
}

