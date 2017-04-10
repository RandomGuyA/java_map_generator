package Model.Map;


import libnoiseforjava.util.ColorCafe;

public class TileType {

    private ColorCafe color;
    private String name;
    private int id;
    private double gradientEnd;
    private double lowEnd, highEnd;

    public TileType(ColorCafe color, String name, int id, double gradientEnd, double lowEnd, double highEnd) {
        this.color = color;
        this.name = name;
        this.id = id;
        this.gradientEnd = gradientEnd;
        this.lowEnd = lowEnd;
        this.highEnd = highEnd;
    }

    public ColorCafe getColor() {
        return color;
    }

    public void setColor(ColorCafe color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGradientEnd() {
        return gradientEnd;
    }

    public void setGradientEnd(double gradientEnd) {
        this.gradientEnd = gradientEnd;
    }

    public double getLowEnd() {
        return lowEnd;
    }

    public void setLowEnd(double lowEnd) {
        this.lowEnd = lowEnd;
    }

    public double getHighEnd() {
        return highEnd;
    }

    public void setHighEnd(double highEnd) {
        highEnd = highEnd;
    }
}
