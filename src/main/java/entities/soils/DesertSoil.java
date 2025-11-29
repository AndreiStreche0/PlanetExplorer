package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public class DesertSoil extends Soil {
    private final double salinity;

    public DesertSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                      double organicMatter, double salinity) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.DESERT);
        this.salinity = salinity;
    }
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() / 2;
        quality += super.getWaterRetention() * 0.3;
        quality -= this.salinity;
        double normalizedQuality = Math.max(0, Math.min(100, quality));
        normalizedQuality = (double) Math.round(normalizedQuality * 100) / 100;
        super.setSoilQuality(normalizedQuality);
    }

    public double blockingProbability() {
        return (100 - super.getWaterRetention() + salinity) / 100.0 * 100;
    }

    public double getSpecificCharacteristic() {
        return salinity;
    }

    public String getSpecificNameCharacteristic() {
        return "salinity";
    }

    public double getSalinity() {
        return salinity;
    }
}
