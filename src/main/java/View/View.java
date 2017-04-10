package View;

import Controller.Coordinates;
import Controller.CustomKeyListener;
import Controller.CustomMouseListener;
import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class View extends JPanel{

    private int DELAY = 10;
    private Timer timer;
    private CustomMouseListener mouseListener;
    private CustomKeyListener keyListener;
    private Model theModel;

    public View(){}

    public void init(Model theModel) {

        this.theModel=theModel;
        this.setFocusable(true);
        mouseListener  = new CustomMouseListener(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        keyListener = new CustomKeyListener(theModel);
        this.addKeyListener(keyListener);

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

        int x = theModel.getMap().getTile_map().getX_position();
        int y  = theModel.getMap().getTile_map().getY_position();

        if(mouseListener.getNewLocation()!=null){
            Coordinates newLocation = mouseListener.getNewLocation();
            theModel.setLocationOffSet(newLocation);
        }
        else if(keyListener.getNewLocation(x,y)!=null){
            Coordinates newLocation = keyListener.getNewLocation(x,y);
            theModel.setLocationOffSet(newLocation);
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
