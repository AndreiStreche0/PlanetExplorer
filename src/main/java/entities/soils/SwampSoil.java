package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public final class SwampSoil extends Soil {
    private final double waterLogging;

    private static final double NITROGEN_MULTIPLIER = 1.1;
    private static final double ORGANIC_MULTIPLIER = 2.2;
    private static final double WATER_LOGGING_MULTIPLIER = 5.0;
    private static final double MAX_VALUE = 100.0;
    private static final double ROUNDER = 100.0;
    private static final double BLOCKING_MULTIPLIER = 10.0;

    public SwampSoil(final String name, final double mass, final double nitrogen,
                     final double waterRetention, final double soilpH,
                     final double organicMatter, final double waterLogging) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.SWAMP);
        this.waterLogging = waterLogging;
    }
    /**
     * calculates soil quality for this specific soil type
     */
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() * NITROGEN_MULTIPLIER;
        quality += super.getOrganicMatter() * ORGANIC_MULTIPLIER;
        quality -= this.waterLogging * WATER_LOGGING_MULTIPLIER;
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
        return waterLogging * BLOCKING_MULTIPLIER;
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

