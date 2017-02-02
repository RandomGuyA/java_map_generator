package View;

import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View extends JPanel{

    private int DELAY = 10;
    private Timer timer;
    private Model theModel;

    public View(){}

    public void init(Model theModel) {

        this.theModel=theModel;

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

    }

    public void paint(Graphics g) {
        super.paint(g);
        setBackground(Color.black);
        theModel.getMap().draw(g);
    }
}
