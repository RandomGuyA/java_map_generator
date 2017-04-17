package Programs;


import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.Perlin;
import libnoiseforjava.util.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PerlinPNGmaker {

    private int WIDTH = 2048;
    private int HEIGHT = 2048;
    private double NOISE_FREQUENCY = 0.05;
    private int SEED = 200;
    private double RADIAL_WIDTH = 2.0;
    private double SCALE_BIAS = -0.55;
    private int x = 0;
    private int y = 0;


    public PerlinPNGmaker() {
        saveImage(buildTexture());
    }

    public void saveImage(BufferedImage image) {
        try {
            ImageIO.write(image, "png", new File("perlin_tests/perlin_test.png"));
        } catch (IOException e1) {
            System.out.println("Could not write the image file.");
        }
    }

    public BufferedImage buildTexture() {
        BufferedImage image = null;
        Perlin noiseModule = new Perlin();
        noiseModule.setFrequency(NOISE_FREQUENCY);
        noiseModule.setSeed(SEED);

        // create Noisemap object
        NoiseMap heightMap = null;

        try {
            heightMap = new NoiseMap(WIDTH, HEIGHT);

            // create Builder object
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(noiseModule);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(WIDTH, HEIGHT);

            //heightMapBuilder.setBounds(-14.0, 14.0, -14.0, 14.0);
            heightMapBuilder.setBounds((double) x * WIDTH, (double) (x * WIDTH) + WIDTH, (double) y * HEIGHT, (double) (y * HEIGHT) + HEIGHT);

            heightMapBuilder.build();

            // create renderer object
            RendererImage renderer = new RendererImage();

            // terrain gradient
            renderer.clearGradient();
            alphaRender(renderer);


            ImageCafe destTexture = new ImageCafe(heightMap.getWidth(), heightMap.getHeight());
            renderer.setSourceNoiseMap(heightMap);
            renderer.setDestImage(destTexture);
            renderer.enableLight(true);
            //renderer.setLightContrast(2.0); // Triple the contrast
            //renderer.setLightBrightness(2.0); // Double the brightness
            // Render the texture.
            renderer.render();

            image = buffBuilder(destTexture.getHeight(), destTexture.getWidth(), destTexture);

        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }

        return image;

    }

    private void alphaRender(RendererImage renderer) {
        try {
            renderer.addGradientPoint(-1.0000, new ColorCafe(0, 0, 0, 0)); // black
            renderer.addGradientPoint(1.000, new ColorCafe(255, 255, 255, 255)); // white
        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }
    }

    public BufferedImage buffBuilder(int height, int width, ImageCafe imageCafe) {

        BufferedImage im = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int c = getRGBA(imageCafe.getValue(i, j));
                im.setRGB(i, j, c);
            }
        }
        return im;
    }

    public int getRGBA(ColorCafe colorCafe) {
        int red, blue, green, alpha;
        red = colorCafe.getRed();
        blue = colorCafe.getBlue();
        green = colorCafe.getGreen();
        alpha = colorCafe.getAlpha();
        Color color = new Color(red, green, blue, alpha);
        int rgbnumber = color.getRGB();
        return rgbnumber;
    }

    public static void main(String[] args) {
        new PerlinPNGmaker();
    }

}
