package entities;

import planet.Cell;

public class Water extends Entity{
    private String type;
    private double purity;
    private double salinity;
    private double turbidity;
    private double contaminantIndex;
    private double pH;
    private boolean isFrozen;
    private boolean isScanned;
    private double waterQuality;

    public Water(String name, double mass, String type, double purity, double salinity,
                 int turbidity, double contaminantIndex, double pH, boolean isFrozen) {
        super(name, mass);
        this.type = type;
        this.purity = purity;
        this.salinity = salinity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.pH = pH;
        this.isFrozen = isFrozen;
        this.isScanned = false;
        this.waterQuality = 0;
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

    public double getpH() {
        return pH;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public boolean isScanned() {
        return this.isScanned;
    }

    public void calculateQuality () {
        double purityScore = purity / 100;
        double pHScore = 1 - Math.abs(pH - 7.5) / 7.5;
        double salinityScore = 1 - (salinity / 350);
        double turbidityScore = 1 - (turbidity / 100);
        double contaminantScore = 1 - (contaminantIndex / 100);
        double frozenScore = isFrozen ? 0 : 1;

        double quality = (0.3 * purityScore
                + 0.2 * pHScore
                + 0.15 * salinityScore
                + 0.1 * turbidityScore
                + 0.15 * contaminantScore
                + 0.2 * frozenScore) * 100;

        this.waterQuality = quality;
    }

    public double getWaterQuality() {
        return waterQuality;
    }

    public String getStringWaterQuality () {
        this.calculateQuality();
        if (waterQuality < 0.4) {
            return "poor";
        }
        if (waterQuality < 0.7) {
            return "moderate";
        }
        return "good";
    }

    public void evolve(Cell currentCell, int timestamp) {

    }
}
