package Model.NoiseModules;


import libnoiseforjava.module.ModuleBase;

public class Radial extends ModuleBase
{

    double DEFAULT_RADIAL_FREQUENCY = 0.15;
    double DEFAULT_MAX_WIDTH = 1.0;

    double frequency;
    double max_width;


    public Radial (){
        super(0);
        frequency = DEFAULT_RADIAL_FREQUENCY;
        max_width = DEFAULT_MAX_WIDTH;
    }

    public double getValue (double x, double y, double z)
    {

        x *= frequency;
        y *= frequency;
        z *= frequency;

        double distance_from_center  = Math.sqrt (x * x + y * y + z * z);

        double delta = distance_from_center / max_width;
        double grad = delta * delta;

        return Math.max(-1.0f, 1.0f - grad);

    }

    public double getMax_width() {
        return max_width;
    }

    public void setMax_width(double max_width) {
        this.max_width = max_width;
    }
}
