package Model.Map;

import Model.Interfaces.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TileMap implements Drawable {

    private Tile[][] tile_map;
    private BufferedImage tiles;
    private int height, width, x_position, y_position;

    public TileMap(Tile[][] tile_map) {
        this.tile_map = tile_map;
        this.width = tile_map.length;
        this.height = tile_map[0].length;

        build_tiles();


    }

    private void build_tiles() {

        tiles = load_image("/tiles.png");
        BufferedImage[] image_array = create_image_array(tiles);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double value = tile_map[x][y].getValue();
                int new_value = (int) (value * 10) - 3;
                if (new_value < 0) {
                    new_value = 0;
                }
                System.out.println(new_value);
                tile_map[x][y].setImage(image_array[new_value]);
            }
        }
    }

    private BufferedImage[] create_image_array(BufferedImage tiles) {

        BufferedImage[] images = new BufferedImage[tiles.getWidth() / tiles.getHeight()];

        for (int a = 0; a < images.length; a++) {

            images[a] = tiles.getSubimage(a * 32, 0, 32, 32);

        }
        return images;
    }

    public Tile[][] getTile_map() {
        return tile_map;
    }

    public void setTile_map(Tile[][] tile_map) {
        this.tile_map = tile_map;
    }

    public void draw(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tile_map[x][y].draw(g, (x * 32) + x_position, (y * 32) + y_position);
            }
        }
    }

    public BufferedImage load_image(String fileName) {

        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            System.out.println("failed to load asset");
        }

        return img;
    }

    public int getX_position() {
        return x_position;
    }

    public void setX_position(int x_position) {
        this.x_position = x_position;
    }

    public int getY_position() {
        return y_position;
    }

    public void setY_position(int y_position) {
        this.y_position = y_position;
    }

}
