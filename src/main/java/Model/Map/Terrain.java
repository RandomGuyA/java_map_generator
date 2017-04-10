package Model.Map;

import Model.Helper.Util;
import Model.Interfaces.Drawable;
import Model.NoiseModules.Radial;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.*;
import libnoiseforjava.util.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


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
    private Tile[][] tile_map;
    private float[][] noise_layer, terrain_layer;

    public Terrain() {

        make_map();
    }

    public void make_map() {

        try {

            int WIDTH = 16;
            int HEIGHT = 16;

            Perlin noiseModule = new Perlin();
            noiseModule.setFrequency(0.01);
            noiseModule.setSeed(Util.randInt(0, 10000));

            Radial radial = new Radial();
            radial.setMax_width(2.0);


            /*Add add = new Add(radial, noiseModule);
            ScaleBias scaleBias = new ScaleBias(add);
            scaleBias.setBias(-0.55);*/


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
            /*renderer.addGradientPoint(-1.0, new ColorCafe(0, 0, 0, 255)); //Black
            renderer.addGradientPoint(1.0, new ColorCafe(255, 255, 255, 255)); //White
            */

            renderer.addGradientPoint(-0.8500, new ColorCafe(7, 52, 127, 255)); // deeps
            renderer.addGradientPoint(-0.6000, new ColorCafe(14, 104, 255, 255)); // shallow
            renderer.addGradientPoint(-0.5000, new ColorCafe(14, 158, 255, 255)); // shore
            renderer.addGradientPoint(-0.4625, new ColorCafe(229, 228, 124, 255)); // sand
            renderer.addGradientPoint(-0.200, new ColorCafe(0, 105, 19, 255)); // grass
            renderer.addGradientPoint(0.5000, new ColorCafe(127, 102, 50, 255)); // dirt
            renderer.addGradientPoint(0.6000, new ColorCafe(128, 128, 128, 255)); // rock
            renderer.addGradientPoint(1.0000, new ColorCafe(255, 255, 255, 255)); // snow


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
            tile_map = textureMapBuilder(destTexture.getHeight(), destTexture.getWidth(), destTexture);

            //tile_map = tile_array_builder(destTexture.getHeight(), destTexture.getWidth(), destTexture);

        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }


        try
        {
            ImageIO.write(terrain_image, "png", new File("terrain_test.png"));
        }
        catch (IOException e1)
        {
            System.out.println("Could not write the image file.");
        }

    }

    private Tile[][] textureMapBuilder(int height, int width, ImageCafe imageCafe) {

        Tile[][] tileMap = new Tile[width][height];
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                System.out.println(++count);

                int rgb = getRGBA(imageCafe.getValue(x, y));
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                ColorCafe color = new ColorCafe(red, green, blue, 255);
                TextureBuilder textureBuilder = new TextureBuilder(color, 32, 32, x, y);
                Tile tile = new Tile();
                tile.setImage(textureBuilder.getTexture());
                tileMap[x][y] = tile;
            }
        }
        return tileMap;
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

    public static Tile[][] tile_array_builder(int height, int width, ImageCafe imageCafe) {

        Tile[][] tile_map = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int rgb = getRGBA(imageCafe.getValue(x, y));
                int red = (rgb >> 16) & 0xFF;

                double value = red * (1 / 255f);
                Tile tile = new Tile();
                tile.setValue(value);
                tile_map[x][y] = tile;
            }
        }
        return tile_map;
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

    public Tile[][] getTile_map() {
        return tile_map;
    }

}
