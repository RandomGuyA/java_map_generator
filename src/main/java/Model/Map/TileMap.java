package Model.Map;

import Model.Interfaces.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TileMap implements Drawable {

    private Tile[][] tile_map;
    private BufferedImage tiles, horizontal_mask, vertical_mask;
    private int height, width, x_position, y_position, tile_width, tile_height;

    public TileMap(Tile[][] tile_map, int tile_width, int tile_height) {
        this.tile_map = tile_map;
        this.width = tile_map.length;
        this.height = tile_map[0].length;
        this.tile_width = tile_width;
        this.tile_height= tile_height;
        horizontal_mask = load_image("/blend_mask_h_32.png");
        vertical_mask = load_image("/blend_mask_v_32.png");
        //build_tiles();
         //blend_tiles();
    }

    private void blend_tiles() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x < width - 1) {
                    BufferedImage image_1 = tile_map[x][y].getImage();
                    BufferedImage image_2 = tile_map[x + 1][y].getImage();
                    tile_map[x][y].setImage(blend(image_1, image_2, horizontal_mask));
                }
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y < height - 1) {
                    BufferedImage image_1 = tile_map[x][y].getImage();
                    BufferedImage image_2 = tile_map[x][y+1].getImage();
                    tile_map[x][y].setImage(blend(image_1, image_2, vertical_mask));
                }
            }
        }
    }

    public BufferedImage blend(BufferedImage tile_1, BufferedImage tile_2, BufferedImage mask) {

        BufferedImage tile = new BufferedImage(tile_1.getWidth(), tile_1.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < tile_1.getHeight(); y++) {
            for (int x = 0; x < tile_1.getWidth(); x++) {

                Color color_1 = new Color(tile_1.getRGB(x, y));
                Color color_2 = new Color(tile_2.getRGB(x, y));
                Color weight_1 = new Color(mask.getRGB(x, y));

                float[] colorArray_1 = color_1.getRGBColorComponents(null);
                float[] colorArray_2 = color_2.getRGBColorComponents(null);
                float[] weightArray = weight_1.getRGBColorComponents(null);

                float[] new_color_array = new float[colorArray_1.length];

                for (int a = 0; a < colorArray_1.length; a++) {

                    float c1 = colorArray_1[a];
                    float c2 = colorArray_2[a];
                    float w1 = weightArray[a];
                    float w2 = 1 - weightArray[a];

                    new_color_array[a] = (c1 * w1) + (c2 * w2);

                }

                Color new_color = new Color(new_color_array[0], new_color_array[1], new_color_array[2]);
                tile.setRGB(x, y, new_color.getRGB());

            }
        }

        return tile;
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
                tile_map[x][y].draw(g, (x * tile_width) + x_position, (y * tile_height) + y_position);
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