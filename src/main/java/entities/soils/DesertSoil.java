package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public class DesertSoil extends Soil {
    private double salinity;

    public DesertSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                      double organicMatter, double salinity) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.DESERT);
        this.salinity = salinity;
    }
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() / 2;
        quality += super.getWaterRetention() * 0.3;
        quality -= this.salinity;
        quality = (double) Math.round(quality * 100) / 100;
        super.setSoilQuality(quality);
    }

    public double blockingProbability() {
        return (double) (100 - super.getWaterRetention() + salinity) / 100.0 * 100;
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
