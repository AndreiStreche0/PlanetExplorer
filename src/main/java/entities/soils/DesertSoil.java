package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public final class DesertSoil extends Soil {
    private final double salinity;

    private static final double NITROGEN_DIVISOR = 2.0;
    private static final double WATER_RETENTION_MULTIPLIER = 0.3;
    private static final double MAX_VALUE = 100.0;
    private static final double ROUNDER = 100.0;
    private static final double BASE_BLOCKING = 100.0;
    private static final double BLOCKING_DIVISOR = 100.0;
    private static final double BLOCKING_MULTIPLIER = 100.0;

    public DesertSoil(final String name, final double mass, final double nitrogen,
                      final double waterRetention, final double soilpH,
                      final double organicMatter, final double salinity) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.DESERT);
        this.salinity = salinity;
    }
    /**
     * calculates soil quality for this specific soil type
     */
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() / NITROGEN_DIVISOR;
        quality += super.getWaterRetention() * WATER_RETENTION_MULTIPLIER;
        quality -= this.salinity;
        double normalizedQuality = Math.max(0, Math.min(MAX_VALUE, quality));
        normalizedQuality = (double) Math.round(normalizedQuality * ROUNDER)
                / ROUNDER;
        super.setSoilQuality(normalizedQuality);
    }
    /**
     * calculates the probability to block terraBot for this specific soil type
     * @return probability
     */
    public double blockingProbability() {
        return (BASE_BLOCKING - super.getWaterRetention() + salinity)
                / BLOCKING_DIVISOR * BLOCKING_MULTIPLIER;
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
