package entities;

import entities.enums.SoilType;
import planet.Cell;
/**
 * class that handles soil, its characteristics, types and evolving logic
 */
public abstract class Soil extends Entity implements Evolve {
    private static final int LOW_QUALITY = 40;
    private static final int MEDIUM_QUALITY = 70;
    private static final double EVOLUTION_AMOUNT = 0.1;
    private static final double ROUNDER = 100.0;
    private final double nitrogen;
    private double waterRetention;
    private final double soilpH;
    private double organicMatter;
    private final SoilType soilType;
    private double soilQuality;

    public Soil(final String name, final double mass, final double nitrogen,
                final double waterRetention, final double soilpH, final double organicMatter,
                final SoilType soilType) {
        super(name, mass);

        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
        this.soilType = soilType;
        super.setScanned(true);
    }

    /**
     * @return display type
     */
    public String getDisplayType() {
        return soilType.getDisplayType();
    }

    /**
     * @param waterRetention - sets it
     */
    public void setWaterRetention(final double waterRetention) {
        this.waterRetention = waterRetention;
    }

    /**
     * @param soilQuality -sets it
     */
    public void setSoilQuality(final double soilQuality) {
        this.soilQuality = soilQuality;
    }
    /**
     * @return nitrogen
     */
    public double getNitrogen() {
        return nitrogen;
    }
    /**
     * @return water retention
     */
    public double getWaterRetention() {
        return waterRetention;
    }
    /**
     * @return soil ph
     */
    public double getSoilpH() {
        return soilpH;
    }
    /**
     * @return organic matter
     */
    public double getOrganicMatter() {
        return organicMatter;
    }
    /**
     * @return soil quality
     */
    public double getSoilQuality() {
        return soilQuality;
    }

    /**
     * @return string for soil quality
     */
    public String getStringSoilQuality() {
        this.calculateSoilQuality();
        if (this.soilQuality < LOW_QUALITY) {
            return "poor";
        }
        if (this.soilQuality < MEDIUM_QUALITY) {
            return "moderate";
        }
        return "good";
    }

    /**
     * abstract method that calculates the soil quality for each type of soil
     */
    public abstract void calculateSoilQuality();

    /**
     * abstract method for final output
     * @return specific characteristic of soil type
     */
    public abstract double getSpecificCharacteristic();
    /**
     * abstract method for final output
     * @return specific name characteristic of soil type
     */
    public abstract String getSpecificNameCharacteristic();

    /**
     * abstract method that gets the probability of terraBot getting blocked in the soil
     * @return probability
     */
    public abstract double blockingProbability();

    /**
     * evolving logic for soil and how it changes based on other water and animal entities
     * @param currentCell the cell the soil is on
     * @param timeStamp time of current simulation
     */
    public void evolve(final Cell currentCell, final int timeStamp) {
        if (currentCell.getCellWater() != null && currentCell.getCellWater().isScanned()
                && (timeStamp - currentCell.getCellWater().getScanTimeStamp()) % 2 == 0) {
            this.waterRetention += EVOLUTION_AMOUNT;
            this.waterRetention = (double) Math.round(this.waterRetention * ROUNDER) / ROUNDER;
        }
        if (currentCell.getCellAnimal() != null) {
            Animal currAnimal = currentCell.getCellAnimal();
            if (currAnimal.isScanned() && currAnimal.getNutritionalAddition() > 0.0
                    && !currAnimal.isSick()) {
                this.organicMatter += currAnimal.getNutritionalAddition();
                currAnimal.setNutritionalAddition(0.0);
                this.organicMatter = (double) Math.round(this.organicMatter * ROUNDER) / ROUNDER;
            }
        }
        this.calculateSoilQuality();
    }

    /**
     * @param organicMatter - sets it
     */
    public void setOrganicMatter(final double organicMatter) {
        this.organicMatter = organicMatter;
    }
}
