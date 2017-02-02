package Model.Map;

import Model.Interfaces.Drawable;

import java.awt.*;


public class Map implements Drawable {


    private Terrain terrain;

    public Map(){
        terrain = new Terrain();
    }

    public void draw(Graphics g) {
        terrain.draw(g);
    }
}
