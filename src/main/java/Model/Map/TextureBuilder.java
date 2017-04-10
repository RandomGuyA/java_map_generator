package Model.Map;

import Model.Helper.Blender;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.Perlin;
import libnoiseforjava.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextureBuilder {

    private BufferedImage texture;
    private int WIDTH, HEIGHT, x, y;


    public TextureBuilder(ColorCafe colorCafe, int WIDTH, int HEIGHT, int x, int y) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.x = x;
        this.y = y;

        texture = buildTexture(colorCafe);
    }

    public BufferedImage buildTexture(ColorCafe color) {
        BufferedImage image = null;
        Perlin noiseModule = new Perlin();
        noiseModule.setFrequency(0.05);
        noiseModule.setSeed(20152);

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
            colourRender(renderer, color);


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
            renderer.addGradientPoint(-1.0000, new ColorCafe(0, 0, 0, 255)); // black
            renderer.addGradientPoint(1.000, new ColorCafe(255, 255, 255, 255)); // white
        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }

    }

    private void grassRender(RendererImage renderer) {
        try {
            renderer.addGradientPoint(-1.0000, new ColorCafe(0, 255, 96, 255));
            renderer.addGradientPoint(0.000, new ColorCafe(22, 127, 62, 255));
            renderer.addGradientPoint(1.000, new ColorCafe(0, 94, 35, 255));
        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }

    }

    private void colourRender(RendererImage renderer, ColorCafe color) {
        //color = new ColorCafe(171, 140, 107, 255);
        float[] hsl = new float[3];
        Blender.RGBtoHSL(color.getRed(), color.getGreen(), color.getBlue(), hsl);

        int[] rgb = new int[3];

        hsl[2] += 0.01;

        Blender.HSLtoRGB(hsl[0], hsl[1], hsl[2], rgb);
        ColorCafe higherColor = new ColorCafe(rgb[0], rgb[1], rgb[2], 255);

        hsl[2] -= 0.02;

        Blender.HSLtoRGB(hsl[0], hsl[1], hsl[2], rgb);
        ColorCafe lowerColor = new ColorCafe(rgb[0], rgb[1], rgb[2], 255);

        try {
            renderer.addGradientPoint(-1.0000, lowerColor);
            renderer.addGradientPoint(1.000, color);
            //renderer.addGradientPoint(1.000, higherColor);
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

    public BufferedImage getTexture() {
        return texture;
    }
}
