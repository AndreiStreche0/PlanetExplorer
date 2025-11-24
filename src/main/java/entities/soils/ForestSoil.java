package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public class ForestSoil extends Soil {
    private double leafLitter;

    public ForestSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                      double organicMatter, double leafLitter) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.FOREST);
        this.leafLitter = leafLitter;
    }
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() * 1.2;
        quality += super.getOrganicMatter() * 2;
        quality += super.getWaterRetention() * 1.5;
        quality += this.leafLitter * 0.3;
        quality = (double) Math.round(quality * 100) / 100;
        super.setSoilQuality(quality);
    }

    public double blockingProbability() {
        double probability = super.getWaterRetention() * 0.6;
        probability += leafLitter * 0.4;
        return probability / 80.0 * 100;
    }

    public double getSpecificCharacteristic() {
        return leafLitter;
    }

    public String getSpecificNameCharacteristic() {
        return "leafLitter";
    }

    public double getLeafLitter() {
        return leafLitter;
    }
}
