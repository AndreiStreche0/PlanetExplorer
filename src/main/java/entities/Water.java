package entities;
/**
 * class that handles water, its characteristics, types and evolving logic
 */
public final class Water extends Entity {
    private static final double MAX_PURITY = 100.0;
    private static final double OPTIMAL_PH = 7.5;
    private static final double MAX_PH_DEVIATION = 7.5;
    private static final double MAX_SALINITY = 350.0;
    private static final double MAX_TURBIDITY = 100.0;
    private static final double MAX_CONTAMINANT_INDEX = 100.0;
    private static final double PURITY_RATIO = 0.3;
    private static final double PH_RATIO = 0.2;
    private static final double SALINITY_RATIO = 0.15;
    private static final double TURBIDITY_RATIO = 0.1;
    private static final double CONTAMINANT_RATIO = 0.15;
    private static final double FROZEN_RATIO = 0.2;
    private static final double QUALITY_MULTIPLIER = 100.0;
    private final String type;
    private final double purity;
    private final double salinity;
    private final double turbidity;
    private final double contaminantIndex;
    private final double pH;
    private final boolean isFrozen;
    private boolean scanned;
    private double waterQuality;
    private int scanTimeStamp;

    public Water(final String name, final double mass, final String type, final double purity,
                 final double salinity, final double turbidity, final double contaminantIndex,
                 final double pH, final boolean isFrozen) {
        super(name, mass);
        this.type = type;
        this.purity = purity;
        this.salinity = salinity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.pH = pH;
        this.isFrozen = isFrozen;
        this.scanned = false;
        this.waterQuality = 0;
        this.scanTimeStamp = -1;
    }

    public int getScanTimeStamp() {
        return scanTimeStamp;
    }

    public void setScanTimeStamp(final int scanTimeStamp) {
        this.scanTimeStamp = scanTimeStamp;
    }

    public String getType() {
        return type;
    }

    public double getPurity() {
        return purity;
    }

    public double getSalinity() {
        return salinity;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public double getContaminantIndex() {
        return contaminantIndex;
    }

    /**
     * @return pH
     */
    public double getpH() {
        return pH;
    }

    /**
     * @return if the water is frozen
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * @return if the water is scanned
     */
    public boolean isScanned() {
        return this.scanned;
    }

    @Override
    public void setScanned(final boolean scanned) {
        this.scanned = scanned;
    }

    /**
     * calculates water quality
     */
    public void calculateQuality() {
        double purityScore = purity / MAX_PURITY;
        double pHScore = 1 - Math.abs(pH - OPTIMAL_PH) / MAX_PH_DEVIATION;
        double salinityScore = 1 - (salinity / MAX_SALINITY);
        double turbidityScore = 1 - (turbidity / MAX_TURBIDITY);
        double contaminantScore = 1 - (contaminantIndex / MAX_CONTAMINANT_INDEX);
        double frozenScore = isFrozen ? 0 : 1;

        this.waterQuality = (PURITY_RATIO * purityScore
                + PH_RATIO * pHScore
                + SALINITY_RATIO * salinityScore
                + TURBIDITY_RATIO * turbidityScore
                + CONTAMINANT_RATIO * contaminantScore
                + FROZEN_RATIO * frozenScore) * QUALITY_MULTIPLIER;
    }

    public double getWaterQuality() {
        return waterQuality;
    }

    /**
     * evolving logic
     */
    public void evolve() {
        this.calculateQuality();
    }
}
