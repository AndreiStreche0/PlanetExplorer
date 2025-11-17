package entities.soils;

import entities.Soil;

public class DesertSoil extends Soil {
    private double salinity;

    public DesertSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                      double organicMatter, double salinity) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.salinity = salinity;
    }
}
