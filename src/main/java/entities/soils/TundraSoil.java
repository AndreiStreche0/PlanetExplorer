package entities.soils;

import entities.Soil;
import entities.enums.SoilType;

public final class TundraSoil extends Soil {
    private final double permafrostDepth;

    private static final double NITROGEN_MULTIPLIER = 0.7;
    private static final double ORGANIC_MULTIPLIER = 0.5;
    private static final double PERMAFROST_MULTIPLIER = 1.5;
    private static final double MAX_VALUE = 100.0;
    private static final double ROUNDER = 100.0;
    private static final double BLOCKING_BASE = 50.0;
    private static final double BLOCKING_DIVISOR = 50.0;
    private static final double BLOCKING_MULTIPLIER = 100.0;

    public TundraSoil(final String name, final double mass, final double nitrogen,
                      final double waterRetention, final double soilpH,
                      final double organicMatter, final double permafrostDepth) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter, SoilType.TUNDRA);
        this.permafrostDepth = permafrostDepth;
    }
    /**
     * calculates soil quality for this specific soil type
     */
    public void calculateSoilQuality() {
        double quality = super.getNitrogen() * NITROGEN_MULTIPLIER;
        quality += super.getOrganicMatter() * ORGANIC_MULTIPLIER;
        quality -= this.permafrostDepth * PERMAFROST_MULTIPLIER;
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
        return (BLOCKING_BASE - permafrostDepth) / BLOCKING_DIVISOR * BLOCKING_MULTIPLIER;
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
