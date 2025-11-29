package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public class TundraSoil extends Soil{
    private final double permafrostDepth;

    public TundraSoil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                     double organicMatter, double permafrostDepth) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.TUNDRA);
        this.permafrostDepth = permafrostDepth;
    }
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() * 0.7;
        quality += super.getOrganicMatter() * 0.5;
        quality -= this.permafrostDepth * 1.5;
        double normalizedQuality = Math.max(0, Math.min(100, quality));
        normalizedQuality = (double) Math.round(normalizedQuality * 100) / 100;
        super.setSoilQuality(normalizedQuality);
    }

    public double blockingProbability() {
        return  (50 - permafrostDepth) / 50.0 * 100;
    }

    public double getSpecificCharacteristic() {
        return permafrostDepth;
    }

    public String getSpecificNameCharacteristic() {
        return "permafrostDepth";
    }

    public double getPermafrostDepth() {
        return permafrostDepth;
    }
}
