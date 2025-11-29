package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public final class ForestSoil extends Soil {
    private final double leafLitter;

    private static final double NITROGEN_MULTIPLIER = 1.2;
    private static final double ORGANIC_MULTIPLIER = 2.0;
    private static final double WATER_RETENTION_MULTIPLIER = 1.5;
    private static final double LEAF_LITTER_MULTIPLIER = 0.3;
    private static final double MAX_VALUE = 100.0;
    private static final double ROUNDER = 100.0;
    private static final double BLOCKING_WATER_MULTIPLIER = 0.6;
    private static final double BLOCKING_LEAF_MULTIPLIER = 0.4;
    private static final double BLOCKING_DIVISOR = 80.0;
    private static final double BLOCKING_MULTIPLIER = 100.0;

    public ForestSoil(final String name, final double mass, final double nitrogen,
                      final double waterRetention, final double soilpH,
                      final double organicMatter, final double leafLitter) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.FOREST);
        this.leafLitter = leafLitter;
    }

    /**
     * calculates soil quality for this specific soil type
     */
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() * NITROGEN_MULTIPLIER;
        quality += super.getOrganicMatter() * ORGANIC_MULTIPLIER;
        quality += super.getWaterRetention() * WATER_RETENTION_MULTIPLIER;
        quality += this.leafLitter * LEAF_LITTER_MULTIPLIER;
        double normalizedQuality = Math.max(0, Math.min(MAX_VALUE, quality));
        normalizedQuality = (double) Math.round(normalizedQuality * ROUNDER)
                /  ROUNDER;
        super.setSoilQuality(normalizedQuality);
    }

    /**
     * calculates the probability to block terraBot for this specific soil type
     * @return probability
     */
    public double blockingProbability() {
        double probability = super.getWaterRetention() * BLOCKING_WATER_MULTIPLIER;
        probability += leafLitter * BLOCKING_LEAF_MULTIPLIER;
        return probability / BLOCKING_DIVISOR * BLOCKING_MULTIPLIER;
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
