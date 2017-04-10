package Controller;

import Model.Model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Random Guy A on 08/04/2017.
 */
public class CustomKeyListener implements KeyListener {

    private Model theModel;
    private int x, y;
    private boolean upPressed, downPressed, leftPressed, rightPressed;


    public CustomKeyListener(Model theModel) {
        this.theModel = theModel;
        x = 0;
        y = 0;
        System.out.println("key listener");

    }

    public void keyTyped(KeyEvent e) {


    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 87) { //UP
            if (!upPressed) {
                y+=5;
                upPressed = true;
            }
        }
        if (e.getKeyCode() == 83) { //DOWN
            if (!downPressed) {
                y-=5;
                downPressed = true;
            }
        }
        if (e.getKeyCode() == 65) { //LEFT
            if (!leftPressed) {
                x+=5;
                leftPressed = true;
            }
        }
        if (e.getKeyCode() == 68) { //RIGHT

            if (!rightPressed) {
                x-=5;
                rightPressed = true;
            }
        }
    }

    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == 87) { //UP
            upPressed = false;
            y = 0;
        }
        if (e.getKeyCode() == 83) { //DOWN
            downPressed = false;
            y = 0;
        }
        if (e.getKeyCode() == 65) { //LEFT
            leftPressed = false;
            x = 0;
        }
        if (e.getKeyCode() == 68) { //RIGHT
            rightPressed = false;
            x = 0;
        }

    }

    public Coordinates getNewLocation(int currentX, int currentY) {

        return new Coordinates(currentX + x, currentY + y);
    }
}
