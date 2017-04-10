package Model.Map;

import libnoiseforjava.util.ColorCafe;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Tile {

    private double value;
    private BufferedImage image;
    private BufferedImage texture, horizontal, vertical;
    private ColorCafe baseColor;
    private TileType tileType;

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

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public ColorCafe getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(ColorCafe baseColor) {
        this.baseColor = baseColor;
    }

    public BufferedImage getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(BufferedImage horizontal) {
        this.horizontal = horizontal;
    }

    public BufferedImage getVertical() {
        return vertical;
    }

    public void setVertical(BufferedImage vertical) {
        this.vertical = vertical;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }
}
