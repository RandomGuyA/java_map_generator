package Model.Map;

import Controller.Coordinates;
import Model.Interfaces.Drawable;

import java.awt.*;


public class Map implements Drawable {


    private Terrain terrain;
    private TileMap tile_map;

    public Map(){
        terrain = new Terrain();
        tile_map = new TileMap(terrain.getTile_map());

    }

    public void draw(Graphics g) {
        tile_map.draw(g);
    }

    public void setLocationOffSet(Coordinates locationOffSet) {
        tile_map.setX_position(locationOffSet.getX());
        tile_map.setY_position(locationOffSet.getY());

    }
}
