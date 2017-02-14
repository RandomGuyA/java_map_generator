package Model.Map;

import Model.Helper.Blender;
import java.awt.*;
import java.math.BigDecimal;

public class Terrain_palette {

    private String type;

    public Terrain_palette(String type) {
        this.type = type.toLowerCase() ;
    }

    public int get_color(float value) {

        Color color = null;

        if (type == "basic") {

            color = generate_basic_color(value);

        } else if (type == "digital") {

            color = generate_terrain_color(value);

        } else if (type == "analog") {

            color = generate_analog_color(value);

        }

        return color.getRGB();
    }

    private Color generate_analog_color(float value) {

        Color color = null;

        if (between(value, 0.0, 0.39)) {


            color = new Color(0, 0, value);

            //color = new Color(22, 114, 163);
            //color = calculate_color(color, value);

        } else if (between(value, 0.4, 1)) {

            color = new Color(0, value, 0);
            //color = new Color(49, 105, 10);
            //color = calculate_color(color, value);
        }

        return color;
    }

    private Color calculate_color(Color color, float value) {

        float[] hsl = new float[3];
        Blender.RGBtoHSL(color.getRed(), color.getGreen(), color.getBlue(), hsl);

        hsl[2] = hsl[2]+value; // needs looking at later, may crete out of bounds error
        int[] rgb = new int[3];
        Blender.HSLtoRGB(hsl[0], hsl[1], hsl[2], rgb);

        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    private Color generate_terrain_color(float value) {

        Color color = null;

        if (between(value, 0.0, 0.09)) {
            color = new Color(22, 114, 163);
        } else if (between(value, 0.1, 0.19)) {
            color = new Color(27, 141, 201);
        } else if (between(value, 0.2, 0.29)) {
            color = new Color(49, 105, 10);
        } else if (between(value, 0.3, 0.39)) {
            color = new Color(61, 130, 13);
        } else if (between(value, 0.4, 0.49)) {
            color = new Color(61, 130, 13);
        } else if (between(value, 0.5, 0.59)) {
            color = new Color(75, 161, 15);
        } else if (between(value, 0.6, 0.69)) {
            color = new Color(75, 161, 15);
        } else if (between(value, 0.7, 0.79)) {
            color = new Color(84, 181, 17);
        } else if (between(value, 0.8, 0.89)) {
            color = new Color(84, 181, 17);
        } else if (between(value, 0.9, 1.0)) {
            color = new Color(95, 204, 20);
        } else {
            System.out.println(value);
            System.out.println("error");
        }
        return color;
    }

    public boolean between(float value, double min, double max) {

        float value_2dp = round(value, 2);
        float fmin = (float) min;
        float fmax = (float) max;

        return (value_2dp >= fmin && value_2dp <= fmax);
    }

    private Color generate_basic_color(float value) {
        return new Color(value, value, value, 1);
    }

    private float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
