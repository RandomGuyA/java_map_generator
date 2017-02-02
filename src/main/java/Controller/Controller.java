package Controller;

import Model.Model;
import View.View;

public class Controller {

    private View theView;
    private Model theModel;

    public Controller(Model theModel, View theView) {
        this.theView=theView;
        this.theModel=theModel;
        theView.init(theModel);
        //this.theView.addEventListener(new EventListener(theView, theModel));
    }
}
