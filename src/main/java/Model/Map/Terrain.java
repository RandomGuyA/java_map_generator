package Model.Map;

import Model.Helper.Util;
import Model.Interfaces.Drawable;
import Model.NoiseModules.Radial;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.*;
import libnoiseforjava.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Terrain implements Drawable {

    private int COUNT_X = 640;
    private int COUNT_Y = 480;
    private int SQUARE_SIZE = 1;
    private float HARD_LIGHT_DERIVATIVE = 0.0f;
    private float MASK_MINIMUM = 0.45f;
    private float MASK_MAXIMUM = 0.55f;

    private int TERRAIN_FREQUENCY = 4;

    private int width = COUNT_X * SQUARE_SIZE;
    private int height = COUNT_Y * SQUARE_SIZE;
    private BufferedImage terrain_image, terrain_mask;
    private float[][] noise_layer, terrain_layer;

    public Terrain() {

        /*
        noise_layer = Noise_generator.get_noise(width, height, 520, MASK_MINIMUM, MASK_MAXIMUM);

        terrain_mask = convert_float_array_to_buffered_image(noise_layer, 1, new Terrain_palette("basic"));

        int terrain_seed = Util.randInt(1, 500);

        //terrain_layer = Noise_generator.get_custom_land_generation(COUNT_X, COUNT_Y, TERRAIN_FREQUENCY, terrain_seed);
        terrain_layer = Noise_generator.generate_simplex_noise(COUNT_X, COUNT_Y);

        float[][] radial_mask = apply_quadratic_distance(COUNT_X, COUNT_Y);


        terrain_image = convert_float_array_to_buffered_image(terrain_layer, SQUARE_SIZE, new Terrain_palette("analog"));


        terrain_image = multiply(radial_mask, terrain_layer);
        */

        // create Perlin noise module object

        make_map();

    }

    public void make_normal_map() {

    }

    public void make_map() {

        try {

            int WIDTH = 512;
            int HEIGHT = 512;

            Perlin noiseModule = new Perlin();
            noiseModule.setFrequency(0.1);
            noiseModule.setSeed(Util.randInt(0, 10000));

            Radial radial = new Radial();
            radial.setMax_width(2.0);

            /*
            Add add = new Add(radial, noiseModule);
            ScaleBias scaleBias = new ScaleBias(add);
            scaleBias.setBias(-0.55);
            */

            // create Noisemap object
            NoiseMap heightMap = new NoiseMap(WIDTH, HEIGHT);

            // create Builder object
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(noiseModule);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(WIDTH, HEIGHT);

            heightMapBuilder.setBounds(-16.0, 16.0, -16.0, 16.0);
            //heightMapBuilder.setBounds (-1.0, 1.0, -1.0, 1.0);

            heightMapBuilder.build();

            // create renderer object
            RendererImage renderer = new RendererImage();

            // terrain gradient
            renderer.clearGradient();
            renderer.addGradientPoint(-1.0, new ColorCafe(0, 0, 0, 255)); //Black
            renderer.addGradientPoint(1.0, new ColorCafe(255, 255, 255, 255)); //White
            /*
            renderer.addGradientPoint(-0.8500, new ColorCafe(7, 52, 127, 255)); // deeps
            renderer.addGradientPoint(-0.6000, new ColorCafe(14, 104, 255, 255)); // shallow
            renderer.addGradientPoint(-0.5000, new ColorCafe(14, 158, 255, 255)); // shore
            renderer.addGradientPoint(-0.4625, new ColorCafe(229, 228, 124, 255)); // sand
            renderer.addGradientPoint(-0.200, new ColorCafe(0, 105, 19, 255)); // grass
            renderer.addGradientPoint(0.5000, new ColorCafe(127, 102, 50, 255)); // dirt
            renderer.addGradientPoint(0.6000, new ColorCafe(128, 128, 128, 255)); // rock
            renderer.addGradientPoint(1.0000, new ColorCafe(255, 255, 255, 255)); // snow
            */

            // Set up the texture renderer and pass the noise map to it.
            ImageCafe destTexture = new ImageCafe(heightMap.getWidth(), heightMap.getHeight());
            renderer.setSourceNoiseMap(heightMap);
            renderer.setDestImage(destTexture);
            renderer.enableLight(true);
            renderer.setLightContrast(2.0); // Triple the contrast
            renderer.setLightBrightness(2.0); // Double the brightness


            // Render the texture.
            renderer.render();

            // Render the texture.
            renderer.render();
            terrain_image = buffBuilder(destTexture.getHeight(), destTexture.getWidth(), destTexture);

        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }
    }

    public static BufferedImage buffBuilder(int height, int width, ImageCafe imageCafe) {

        BufferedImage im = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int c = getRGBA(imageCafe.getValue(i, j));
                im.setRGB(i, j, c);
            }
        }
        return im;
    }

    public static final int getRGBA(ColorCafe colorCafe) {
        int red, blue, green, alpha;
        red = colorCafe.getRed();
        blue = colorCafe.getBlue();
        green = colorCafe.getGreen();
        alpha = colorCafe.getAlpha();
        Color color = new Color(red, green, blue, alpha);
        int rgbnumber = color.getRGB();
        return rgbnumber;
    }

    public void draw(Graphics g) {
        g.drawImage(terrain_image, 0, 0, null);
    }

    public float[][] apply_quadratic_distance(int width, int height) {

        float[][] gradient = new float[width][height];
        float center_x = width * 0.5f;
        float center_y = height * 0.5f;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                float distance_from_center = distance(center_x, center_y, x, y);

                float max_width = height * 0.5f - 30.0f;
                float delta = distance_from_center / max_width;
                float grad = delta * delta;

                gradient[x][y] = Math.max(0.0f, 1.0f - grad);
            }
        }
        return gradient;
    }

    private float distance(float x1, float y1, float x2, float y2) {
        return (float) (Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2)));
    }

    private float fLerp(float norm, float min, float max) {
        return (max - min) * norm + min;
    }

    private BufferedImage multiply(float[][] image_a, float[][] image_b) {

        int width = image_a.length;
        int height = image_a[0].length;

        float[][] new_image = new float[width][height];


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                float aPixel = image_a[x][y];
                float bPixel = image_b[x][y];

                double alpha = 0.5;

                Color aColor = new Color(aPixel, aPixel, aPixel);
                Color bColor = new Color(bPixel, bPixel, bPixel);
                int src = aColor.getRGB();
                int dest = bColor.getRGB();

                int r1 = (src >> 16) & 0xFF;
                int g1 = (src >> 8) & 0xFF;
                int b1 = src & 0xFF;
                int r2 = (dest >> 16) & 0xFF;
                int g2 = (dest >> 8) & 0xFF;
                int b2 = dest & 0xFF;
                int ar, ag, ab;

                ar = (int) (alpha * (double) (r1 - r2) + r2);
                ag = (int) (alpha * (double) (g1 - g2) + g2);
                ab = (int) (alpha * (double) (b1 - b2) + b2);

                int pixel = ab | (ag << 8) | (ar << 16);
                bufferedImage.setRGB(x, y, pixel);
            }
        }
        return bufferedImage;
    }

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



    /*public void draw(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        //g.drawImage(terrain_image, 0, 0, null);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D big = bufferedImage.createGraphics();
        big.drawImage(terrain_image, 0, 0, null);
        big.setComposite(BlendComposite.Multiply.derive(HARD_LIGHT_DERIVATIVE));
        big.drawImage(terrain_mask, 0, 0, null);
        g2D.drawImage(bufferedImage, null, 0, 0);
    }*/
}
