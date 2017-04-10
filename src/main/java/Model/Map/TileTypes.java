package Model.Map;

import libnoiseforjava.util.ColorCafe;

import java.util.ArrayList;


public class TileTypes {

    private ArrayList<TileType> tileTypes;
    private int id;
    private double lowEnd, highEnd;


    public TileTypes(double lowEnd, double highEnd) {

        this.lowEnd = lowEnd;
        this.highEnd = highEnd;

        tileTypes = new ArrayList<TileType>();

        tileTypes.add(new TileType(new ColorCafe(7, 52, 127, 255), "deep", ++id, -0.8500, lowEnd, highEnd));
        tileTypes.add(new TileType(new ColorCafe(14, 104, 55, 255), "shallow", ++id, -0.6000, lowEnd, highEnd));
        tileTypes.add(new TileType(new ColorCafe(14, 158, 255, 255), "shore", ++id, -0.5000, lowEnd, highEnd));
        tileTypes.add(new TileType(new ColorCafe(229, 228, 124, 255), "sand", ++id, -0.4625, lowEnd, highEnd));
        tileTypes.add(new TileType(new ColorCafe(0, 160, 19, 255), "grass", ++id, -0.3000, lowEnd, highEnd));
        tileTypes.add(new TileType(new ColorCafe(0, 195, 23, 255), "dark_grass", ++id, 0.2000, lowEnd, highEnd));
        tileTypes.add(new TileType(new ColorCafe(127, 102, 50, 255), "light_grass", ++id, 0.5000, lowEnd, highEnd));
        tileTypes.add(new TileType(new ColorCafe(128, 128, 128, 255), "rock", ++id, 0.6500, lowEnd, highEnd));
        tileTypes.add(new TileType(new ColorCafe(255, 255, 255, 255), "snow", ++id, 1.0000, lowEnd, highEnd));

    }

    public ArrayList<TileType> getTileTypes() {
        return tileTypes;
    }

    public void setTileTypes(ArrayList<TileType> tileTypes) {
        this.tileTypes = tileTypes;
    }

    public TileType getTileType(int rgbValue) {





        return tileTypes.get(0);
    }
}
