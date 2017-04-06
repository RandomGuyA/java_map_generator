package View;

import Controller.Coordinates;
import Controller.CustomMouseListener;
import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View extends JPanel{

    private int DELAY = 10;
    private Timer timer;
    private CustomMouseListener mouseListener;
    private Model theModel;

    public View(){}

    public void init(Model theModel) {

        this.theModel=theModel;

        mouseListener  = new CustomMouseListener(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);


        //Swing Timer
        timer = new Timer(DELAY, new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                update();
                repaint();
                revalidate();
            }
        });
        timer.start();
    }

    private void update() {
        if(mouseListener.getNewLocation()!=null){
            Coordinates newLocation = mouseListener.getNewLocation();
            theModel.setLocationOffSet(newLocation);
            /* positionX = newLocation.getX();//+newLocationX;
            positionY = newLocation.getY();//+newLocationY;*/

        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        setBackground(Color.gray);
        theModel.getMap().draw(g);
    }

    public Model getTheModel() {
        return theModel;
    }
}
