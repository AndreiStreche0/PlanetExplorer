package entities.soils;

import entities.Soil;

public class GrasslandSoil extends Soil{
    private double rootDensity;

    public GrasslandSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                      double organicMatter, double rootDensity) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.rootDensity = rootDensity;
    }
}
