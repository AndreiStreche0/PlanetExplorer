package entities.soils;

import entities.Soil;

public class ForestSoil extends Soil {
    public double leafLitter;

    public ForestSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                      double organicMatter, double leafLitter) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.leafLitter = leafLitter;
    }
}
