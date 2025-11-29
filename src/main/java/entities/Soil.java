package entities;

import entities.enums.AnimalHunger;
import entities.enums.PlantAge;
import entities.enums.SoilType;
import planet.Cell;

public abstract class Soil extends Entity implements Evolve{
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private SoilType soilType;
    private double soilQuality;

    public Soil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                double organicMatter, SoilType soilType) {
        super(name, mass);

        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
        this.soilType = soilType;
        super.setScanned(true);
    }

    public String getDisplayType() {
        return soilType.getDisplayType();
    }

    public void setWaterRetention(double waterRetention) {
        this.waterRetention = waterRetention;
    }

    public void setSoilQuality(double soilQuality) {
        this.soilQuality = soilQuality;
    }

    public double getNitrogen() {
        return nitrogen;
    }

    public double getWaterRetention() {
        return waterRetention;
    }

    public double getSoilpH() {
        return soilpH;
    }

    public double getOrganicMatter() {
        return organicMatter;
    }

    public double getSoilQuality() {
        return soilQuality;
    }

    public String getStringSoilQuality() {
        this.calculateSoilQuality();
        double soilQuality = this.getSoilQuality();
        if (soilQuality < 40) {
            return "poor";
        }
        if (soilQuality < 70) {
            return "moderate";
        }
        return "good";
    }
    public abstract void calculateSoilQuality();

    public abstract double getSpecificCharacteristic();
    public abstract String getSpecificNameCharacteristic();

    public abstract double blockingProbability();

    public void evolve(Cell currentCell, int timeStamp) {
        if (currentCell.getCellWater() != null && currentCell.getCellWater().isScanned()
                && (timeStamp - currentCell.getCellWater().getScanTimeStamp()) % 2 == 0) {
            this.waterRetention += 0.1;
            this.waterRetention = (double) Math.round(this.waterRetention * 100.0) / 100.0;
        }
        if (currentCell.getCellAnimal() != null) {
            Animal currAnimal = currentCell.getCellAnimal();
            if (currAnimal.isScanned() && currAnimal.getNutritionalAddition() > 0.0 && !currAnimal.isSick()) {
                this.organicMatter += currAnimal.getNutritionalAddition();
                currAnimal.setNutritionalAddition(0.0);
                this.organicMatter = (double) Math.round(this.organicMatter * 100.0) / 100.0;
            }
        }
        this.calculateSoilQuality();
    }

    public void setOrganicMatter(double organicMatter) {
        this.organicMatter = organicMatter;
    }
}
