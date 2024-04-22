import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

// Stevie K. Halprin
// 4/7/2024
public class IceHockey implements ActionListener, KeyListener {
    // Instance variables
    private Puck p;
    private Player P1;
    private Player P2;
    private IceHockeyViewer window;
    private int gameStage;

    private static final int MAX_WIDTH = 1240;
    private static final int MAX_HEIGHT = 910;
    private static final int TOP_OF_WINDOW = 20;
    private static final int SCOREBOARD_HEIGHT = 190;

    private static final int PLAYER_Y = 530;
    private static final int PLAYER_ONE_X = 325;
    private static final int PLAYER_TWO_X = 925;
    private static final int SLEEP_TIME = 110;


    // Main method
    public static void main(String args[]) {
        IceHockey hockey = new IceHockey();
        Timer clock = new Timer(SLEEP_TIME, hockey);
        clock.start();
    }

    // Constructor
    public IceHockey() {
        p = new Puck();
        P1 = new Player("Player 1", PLAYER_ONE_X, PLAYER_Y, Color.RED, true);
        P2 = new Player("Player 2", PLAYER_TWO_X, PLAYER_Y,Color.BLUE, false);
        gameStage = 0;
        window = new IceHockeyViewer(this);

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
    public void gameCheck() {
        if (P1.getScore() >= 3 || P2.getScore() >= 3) {
            gameStage = 3;
        }
    }
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

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

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

        P1.keyPressed(e);
        P2.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (P1.getScore() < 3 && P2.getScore() < 3) {
            p.move();
            window.repaint();
        }
    }
}

