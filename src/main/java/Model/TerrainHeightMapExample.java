package Model;

import Model.NoiseModules.Radial;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.*;
import libnoiseforjava.util.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Model.Map.Terrain.buffBuilder;

public class TerrainHeightMapExample
{
    // generates an example Terrain Height Map, as shown at
    // http://libnoise.sourceforge.net/tutorials/tutorial3.html
    public static void main(String[] args) throws ExceptionInvalidParam {

        int WIDTH = 256;
        int HEIGHT = 256;

        Perlin noiseModule = new Perlin();
        noiseModule.setFrequency(0.1);
        noiseModule.setSeed(998);

        Radial radial = new Radial();
        radial.setMax_width(1.5);

        Add add = new Add(radial, noiseModule);

        // create Noisemap object
        NoiseMap heightMap = new NoiseMap(WIDTH, HEIGHT);

        // create Builder object
        NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
        heightMapBuilder.setSourceModule(add);
        heightMapBuilder.setDestNoiseMap(heightMap);
        heightMapBuilder.setDestSize(WIDTH, HEIGHT);


        heightMapBuilder.setBounds(-14.0, 14.0, -14.0, 14.0);
        //heightMapBuilder.setBounds (-1.0, 1.0, -1.0, 1.0);

        heightMapBuilder.build();

        // create renderer object
        RendererImage renderer = new RendererImage();

        // terrain gradient
        renderer.clearGradient();
        renderer.addGradientPoint(-1.0000, new ColorCafe(7, 52, 127, 255)); // deeps
        renderer.addGradientPoint(-0.2500, new ColorCafe(14, 104, 255, 255)); // shallow
        renderer.addGradientPoint(0.0000, new ColorCafe(14, 158, 255, 255)); // shore
        renderer.addGradientPoint(0.0625, new ColorCafe(229, 228, 124, 255)); // sand
        renderer.addGradientPoint(0.4000, new ColorCafe(91, 255, 75, 255)); // grass
        renderer.addGradientPoint(0.7000, new ColorCafe(127, 102, 50, 255)); // dirt
        renderer.addGradientPoint(0.8500, new ColorCafe(128, 128, 128, 255)); // rock
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

        BufferedImage im = buffBuilder(destTexture.getHeight(), destTexture.getWidth(), destTexture);

        try
        {
            ImageIO.write(im, "png", new File("terrain_test.png"));
        }
        catch (IOException e1)
        {
            System.out.println("Could not write the image file.");
        }

    }


    /*
    public static void main(String[] args) throws ExceptionInvalidParam
    {
        // create Perlin noise module object
        Perlin perlin1 = new Perlin();
        Voronoi voronoi = new Voronoi();
        perlin1.setSeed(1000);

        // create Noisemap object
        NoiseMap heightMap = new NoiseMap(1024, 1024);

        // create Builder object
        NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
        heightMapBuilder.setSourceModule (perlin1);
        heightMapBuilder.setDestNoiseMap (heightMap);
        heightMapBuilder.setDestSize (1024, 1024);
        heightMapBuilder.setBounds (2.0, 6.0, 1.0, 5.0);
        heightMapBuilder.build();

        // create renderer object
        RendererImage renderer = new RendererImage();

        // terrain gradient
        renderer.clearGradient ();
        renderer.addGradientPoint (-1.0000, new ColorCafe (  7,  52, 127, 255)); // deeps
        renderer.addGradientPoint (-0.2500, new ColorCafe ( 14, 104, 255, 255)); // shallow
        renderer.addGradientPoint ( 0.0000, new ColorCafe ( 14, 158, 255, 255)); // shore
        renderer.addGradientPoint ( 0.0625, new ColorCafe (229, 228, 124, 255)); // sand
        renderer.addGradientPoint ( 0.1250, new ColorCafe ( 91, 255,  75, 255)); // grass
        renderer.addGradientPoint ( 0.3750, new ColorCafe (127, 102,  50, 255)); // dirt
        renderer.addGradientPoint ( 0.7500, new ColorCafe (128, 128, 128, 255)); // rock
        renderer.addGradientPoint ( 1.0000, new ColorCafe (255, 255, 255, 255)); // snow


        // Set up the texture renderer and pass the noise map to it.
        ImageCafe destTexture = new ImageCafe(heightMap.getWidth(), heightMap.getHeight());
        renderer.setSourceNoiseMap (heightMap);
        renderer.setDestImage (destTexture);
        renderer.enableLight (true);
        renderer.setLightContrast (2.0); // Triple the contrast
        renderer.setLightBrightness (2.0); // Double the brightness


        // Render the texture.
        renderer.render();

        BufferedImage im = buffBuilder(destTexture.getHeight(), destTexture.getWidth(), destTexture);

        try
        {
            ImageIO.write(im, "png", new File("terrain_test.png"));
        }
        catch (IOException e1)
        {
            System.out.println("Could not write the image file.");
        }


    }//end of main

    static BufferedImage buffBuilder(int height, int width, ImageCafe imageCafe)
    {

        BufferedImage im = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int c = getRGBA(imageCafe.getValue(i, j));
                im.setRGB(i, j, c);
            }
        }
        return im;
    }

    public static final int getRGBA(ColorCafe colorCafe)
    {
        int red, blue, green, alpha;
        red = colorCafe.getRed();
        blue = colorCafe.getBlue();
        green = colorCafe.getGreen();
        alpha = colorCafe.getAlpha();
        Color color = new Color(red, green, blue, alpha);
        int rgbnumber = color.getRGB();
        return rgbnumber;
    }
    */
}

