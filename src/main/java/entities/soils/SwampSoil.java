package entities.soils;

import entities.Soil;

public class SwampSoil extends Soil{
    private double waterLogging;

    public SwampSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                         double organicMatter, double waterLogging) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.waterLogging = waterLogging;
    }
}
