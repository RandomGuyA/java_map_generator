package Model.NoiseModules;


import libnoiseforjava.module.ModuleBase;

public class Radial extends ModuleBase
{


    static final double DEFAULT_SPHERES_FREQUENCY = 1.0;

    /// Frequency of the concentric spheres.
    double frequency;


    public Radial ()
    {
        super(0);
        frequency = DEFAULT_SPHERES_FREQUENCY;
    }

    public double getValue (double x, double y, double z)
    {
        x *= frequency;
        y *= frequency;
        z *= frequency;

        double distFromCenter = Math.sqrt (x * x + y * y + z * z);
        double distFromSmallerSphere = distFromCenter - Math.floor (distFromCenter);
        double distFromLargerSphere = 1.0 - distFromSmallerSphere;
        double nearestDist = Math.min(distFromSmallerSphere, distFromLargerSphere);
        return 1.0 - (nearestDist * 4.0); // Puts it in the -1.0 to +1.0 range.
    }

    /// Returns the frequency of the concentric spheres.
    ///
    /// @returns The frequency of the concentric spheres.
    ///
    /// Increasing the frequency increases the density of the concentric
    /// spheres, reducing the distances between them.
    public double getFrequency ()
    {
        return frequency;
    }

    /// Sets the frequency of the concentric spheres.
    ///
    /// @param frequency The frequency of the concentric spheres.
    ///
    /// Increasing the frequency increases the density of the concentric
    /// spheres, reducing the distances between them.
    public void setFrequency (double frequency)
    {
        this.frequency = frequency;
    }

}
