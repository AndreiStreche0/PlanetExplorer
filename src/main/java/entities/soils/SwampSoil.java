package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public class SwampSoil extends Soil{
    private final double waterLogging;

    public SwampSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                         double organicMatter, double waterLogging) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.SWAMP);
        this.waterLogging = waterLogging;
    }
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() * 1.1;
        quality += super.getOrganicMatter() * 2.2;
        quality -= this.waterLogging * 5;
        double normalizedQuality = Math.max(0, Math.min(100, quality));
        normalizedQuality = (double) Math.round(normalizedQuality * 100) / 100;
        super.setSoilQuality(normalizedQuality);
    }

    public double blockingProbability() {
        return waterLogging * 10;
    }

    public double getSpecificCharacteristic() {
        return waterLogging;
    }

    public String getSpecificNameCharacteristic() {
        return "waterLogging";
    }

    public double getWaterLogging() {
        return waterLogging;
    }
}
