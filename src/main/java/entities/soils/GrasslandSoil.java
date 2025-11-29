package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public class GrasslandSoil extends Soil{
    private final double rootDensity;

    public GrasslandSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                      double organicMatter, double rootDensity) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.GRASSLAND);
        this.rootDensity = rootDensity;
    }
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() * 1.3;
        quality += this.rootDensity * 0.8;
        quality += super.getOrganicMatter() * 1.5;
        double normalizedQuality = Math.max(0, Math.min(100, quality));
        normalizedQuality = (double) Math.round(normalizedQuality * 100) / 100;
        super.setSoilQuality(normalizedQuality);
    }

    public double blockingProbability() {
        double probability = super.getWaterRetention() * 0.5;
        probability += (50 - rootDensity);
        return probability / 75.0 * 100;
    }

    public double getSpecificCharacteristic() {
        return rootDensity;
    }

    public String getSpecificNameCharacteristic() {
        return "rootDensity";
    }

    public double getRootDensity() {
        return rootDensity;
    }
}
