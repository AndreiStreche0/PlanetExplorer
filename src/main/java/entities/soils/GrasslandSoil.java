package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public final class GrasslandSoil extends Soil {
    private final double rootDensity;

    private static final double NITROGEN_MULTIPLIER = 1.3;
    private static final double ROOT_DENSITY_MULTIPLIER = 0.8;
    private static final double ORGANIC_MULTIPLIER = 1.5;
    private static final double MAX_QUALITY = 100.0;
    private static final double ROUNDING_FACTOR = 100.0;
    private static final double BLOCKING_WATER_WEIGHT = 0.5;
    private static final double BLOCKING_BASE = 50.0;
    private static final double BLOCKING_DIVISOR = 75.0;
    private static final double BLOCKING_MULTIPLIER = 100.0;

    public GrasslandSoil(final String name, final double mass, final double nitrogen,
                         final double waterRetention, final double soilpH,
                         final double organicMatter, final double rootDensity) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.GRASSLAND);
        this.rootDensity = rootDensity;
    }
    /**
     * calculates soil quality for this specific soil type
     */
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() * NITROGEN_MULTIPLIER;
        quality += this.rootDensity * ROOT_DENSITY_MULTIPLIER;
        quality += super.getOrganicMatter() * ORGANIC_MULTIPLIER;
        double normalizedQuality = Math.max(0, Math.min(MAX_QUALITY, quality));
        normalizedQuality = (double) Math.round(normalizedQuality * ROUNDING_FACTOR)
                / ROUNDING_FACTOR;
        super.setSoilQuality(normalizedQuality);
    }
    /**
     * calculates the probability to block terraBot for this specific soil type
     * @return probability
     */
    public double blockingProbability() {
        double probability = super.getWaterRetention() * BLOCKING_WATER_WEIGHT;
        probability += (BLOCKING_BASE - rootDensity);
        return probability / BLOCKING_DIVISOR * BLOCKING_MULTIPLIER;
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
