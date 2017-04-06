package Controller;


import View.View;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CustomMouseListener implements MouseListener, MouseMotionListener {

    private Coordinates initialClick = null;
    private Coordinates newLocation = null;
    private Coordinates distanceTraveled;
    private int MOUSE_MOVEMENT_SPEED_FACTOR = 10;
    private View view;
    private int movementX = 0, movementY = 0;

    public CustomMouseListener(View view) {
        this.view = view;
    }

    public Coordinates getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(Coordinates newLocation) {
        this.newLocation = newLocation;
    }


    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }


    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    public void mousePressed(MouseEvent evt) {
        if (SwingUtilities.isRightMouseButton(evt)) {
            newLocation = null;

            initialClick = new Coordinates(movementX + evt.getX(), movementY + evt.getY());


        }
    }


    public void mouseReleased(MouseEvent evt) {

    }


    public void mouseDragged(MouseEvent evt) {
        if (SwingUtilities.isRightMouseButton(evt)) {

            movementX = initialClick.getX() - evt.getX();
            movementY = initialClick.getY() - evt.getY();

            newLocation = new Coordinates(movementX * MOUSE_MOVEMENT_SPEED_FACTOR, movementY * MOUSE_MOVEMENT_SPEED_FACTOR);
        }
    }


    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public Coordinates getInitialClick() {
        return initialClick;
    }

    public void setInitialClick(Coordinates initialClick) {
        this.initialClick = initialClick;
    }
}
