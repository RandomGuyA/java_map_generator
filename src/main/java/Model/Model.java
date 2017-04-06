package Model;

import Controller.Coordinates;
import Model.Map.Map;

public class Model {

    private Map map;


    public Model() {
        map = new Map();
    }

    public Map getMap() {
        return map;
    }

    public void setLocationOffSet(Coordinates locationOffSet) {
        map.setLocationOffSet(locationOffSet);

    }
}
