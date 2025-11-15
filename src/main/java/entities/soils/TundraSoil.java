package entities.soils;

import entities.Soil;

public class TundraSoil extends Soil{
    public double permafrostDepth;

    public TundraSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                     double organicMatter, double permafrostDepth) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.permafrostDepth = permafrostDepth;
    }
}
