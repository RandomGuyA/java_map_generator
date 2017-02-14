package Model.Map;

import Model.Helper.BlendComposite;
import Model.Helper.Noise_generator;
import Model.Helper.Util;
import Model.Interfaces.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Terrain implements Drawable {


    private int COUNT_X = 100;
    private int COUNT_Y = 100;
    private int SQUARE_SIZE = 4;
    private float HARD_LIGHT_DERIVATIVE = 0.5f;
    private float MASK_MINIMUM = 0.75f;
    private float MASK_MAXIMUM = 0.80f;
    private int TERRAIN_FREQUENCY = 4;

    private int width = COUNT_X * SQUARE_SIZE;
    private int height = COUNT_Y * SQUARE_SIZE;
    private BufferedImage terrain_image, terrain_mask;
    private float[][] noise_layer, terrain_layer;

    public Terrain() {

        noise_layer = Noise_generator.get_noise(width, height, 520, MASK_MINIMUM, MASK_MAXIMUM);
        terrain_mask = convert_float_array_to_buffered_image(noise_layer, 1, new Terrain_palette("basic"));

        int terrain_seed = Util.randInt(1, 500);

        terrain_layer = Noise_generator.get_custom_land_generation(COUNT_X, COUNT_Y, TERRAIN_FREQUENCY, terrain_seed);
        terrain_image = convert_float_array_to_buffered_image(terrain_layer, SQUARE_SIZE, new Terrain_palette("analog"));

        //Util.printArray(noise_layer);
    }
    /*
    public apply_quadratic_distance() {

        float distance_x = Math.abs(x - island_size * 0.5f);
        float distance_y = Math.abs(y - island_size * 0.5f);
        float distance = Math.sqrt(distance_x * distance_x + distance_y * distance_y); // circular mask

        float max_width = island_size * 0.5f - 10.0f;
        float delta = distance / max_width;
        float gradient = delta * delta;

        noise *= Math.max(0.0f, 1.0f - gradient);

        return noise;
    }
    */


    public BufferedImage convert_float_array_to_buffered_image(float[][] float_array, int square_size, Terrain_palette palette) {

        int width = float_array.length * square_size;
        int height = float_array[0].length * square_size;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < float_array[0].length; y++) {
            for (int x = 0; x < float_array.length; x++) {

                for (int dy = 0; dy < square_size; dy++) {
                    for (int dx = 0; dx < square_size; dx++) {

                        int true_x = (x * square_size) + dx;
                        int true_y = (y * square_size) + dy;
                        bufferedImage.setRGB(true_x, true_y, palette.get_color(float_array[x][y]));
                    }
                }
            }
        }
        return bufferedImage;
    }

    public void draw(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        //g.drawImage(terrain_image, 0, 0, null);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D big = bufferedImage.createGraphics();
        big.drawImage(terrain_image, 0, 0, null);
        big.setComposite(BlendComposite.HardLight.derive(HARD_LIGHT_DERIVATIVE));
        big.drawImage(terrain_mask, 0, 0, null);
        g2D.drawImage(bufferedImage, null, 0, 0);
    }
}
