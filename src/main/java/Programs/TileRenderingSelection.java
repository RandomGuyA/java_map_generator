package Programs;

import Model.Helper.Blender;
import Model.Map.TileType;
import Model.Map.TileTypes;
import libnoiseforjava.util.ColorCafe;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TileRenderingSelection {

    private ArrayList<TileType> tileTypes;
    private String DIR = "/patterns/";
    private ArrayList<BufferedImage> tileCollection;

    public TileRenderingSelection() {

        TileTypes tt = new TileTypes(0, 1);
        tileTypes = tt.getTileTypes();
        tileCollection = new ArrayList<BufferedImage>();
        produceTileSet();
        BufferedImage mainImage = buildImage(tileCollection);

        saveImage(mainImage, "texture_test");

    }

    private BufferedImage buildImage(ArrayList<BufferedImage> tileCollection) {

        int imageWidth = tileCollection.get(0).getWidth();
        int imageHeight = tileCollection.get(0).getHeight();

        int width = tileCollection.size() * imageWidth;
        int height = imageHeight;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        for (int a = 0; a < tileCollection.size(); a++) {
            g2d.drawImage(tileCollection.get(a), imageWidth * a, 0, imageWidth, imageHeight, null);
        }

        g2d.dispose();

        return image;
    }

    public void produceTileSet() {

        BufferedImage image = loadImage("grass_1.png");

        for (int a = 0; a < tileTypes.size(); a++) {

            BufferedImage tileImage = addColourToTile(image, tileTypes.get(a).getColor());
            tileCollection.add(tileImage);
            saveImage(tileImage, "image_"+(a+1));
        }
    }

    public BufferedImage addColourToTile(BufferedImage image, ColorCafe color) {

        BufferedImage texturedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        float[] hsl = new float[3];
        Blender.RGBtoHSL(color.getRed(), color.getGreen(), color.getBlue(), hsl);
        double gradient = 0.20;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int rgba = image.getRGB(x, y);
                int red = (rgba >> 16) & 0xFF;

                float luminosity = (float) (hsl[2] - ((255 - red) * (gradient / 255)));
                int[] rgb = new int[3];
                Blender.HSLtoRGB(hsl[0], hsl[1], luminosity, rgb);
                ColorCafe colorCafe = new ColorCafe(rgb[0], rgb[1], rgb[2], 255);
                texturedImage.setRGB(x, y, getRGBA(colorCafe));

            }
        }
        return texturedImage;
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

    public void saveImage(BufferedImage image, String FileName) {

        try {
            ImageIO.write(image, "png", new File("tile_texture_tests/" + FileName + ".png"));
        } catch (IOException e) {
            System.out.println("Could not write the image file.");
        }

    }

    public BufferedImage loadImage(String fileName) {

        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(DIR + fileName));
        } catch (IOException e) {
            System.out.println("failed to load asset");
        }

        return img;
    }

    public static void main(String[] args) {
        new TileRenderingSelection();
    }

}

