package Model.Map;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Tile {

    private double value;
    private BufferedImage image;

    public Tile() {

    }

    public void draw(Graphics g, int x, int y){
        g.drawImage(image, x, y, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
