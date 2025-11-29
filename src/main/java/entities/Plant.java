package entities;

import entities.enums.PlantAge;
import entities.enums.PlantType;
import planet.Cell;

public abstract class Plant extends Entity implements Evolve{
    private PlantAge plantAge;
    private PlantType plantType;
    private boolean scanned;
    private int scanTimeStamp;
    private double growthRate;

    public Plant(String name, double mass, PlantAge plantAge, PlantType plantType) {
        super(name, mass);
        this.plantAge = plantAge;
        this.plantType = plantType;
        this.scanned = false;
        this.scanTimeStamp = -1;
        this.growthRate = 0;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    public void setPlantAge(PlantAge plantAge) {
        this.plantAge = plantAge;
    }

    public int getScanTimeStamp() {
        return scanTimeStamp;
    }

    @Override
    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

    public void setScanTimeStamp(int scanTimeStamp) {
        this.scanTimeStamp = scanTimeStamp;
    }

    public abstract double getStuckingPercentage();
    public abstract double getOxygen();

    public String getDisplayType() {
        return plantType.getDisplayType();
    }

    public PlantAge getPlantAge() {
        return plantAge;
    }

    public abstract double calculateOxygenProduction();
    public void evolve(Cell currentCell, int timestamp) {
        if (currentCell.getCellSoil() != null) {
            this.grow(currentCell, 0.2);
        }
        if (currentCell.getCellWater() != null && currentCell.getCellWater().isScanned()) {
            this.grow(currentCell, 0.2);
        }
    }

    public void grow(Cell currCell, double growth) {
        System.out.println("Plant " + getName() + " at (" + currCell.getxCoordinate() + "," +
                currCell.getyCoordinate() + ") growth: " + growthRate + " + " + growth +
                " = " + (growthRate + growth) + ", age: " + plantAge);
        if (growthRate == 0.8) {
            growthRate = 0;
            switch (plantAge) {
                case YOUNG:
                    this.setPlantAge(PlantAge.MATURE);
                    break;
                case MATURE:
                    this.setPlantAge(PlantAge.OLD);
                    break;
                case OLD:
                    currCell.setCellPlant(null);
                    return;
            }
        } else {
            growthRate += 0.2;
        }
    }

    public abstract double stuckProbability();

    public boolean isScanned() {
        return this.scanned;
    }
}
