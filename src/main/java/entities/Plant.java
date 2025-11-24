package entities;

import entities.enums.PlantAge;
import entities.enums.PlantType;
import planet.Cell;

public abstract class Plant extends Entity implements Evolve{
    private PlantAge plantAge;
    private PlantType plantType;
    private boolean isScanned;

    public Plant(String name, double mass, PlantAge plantAge, PlantType plantType) {
        super(name, mass);
        this.plantAge = plantAge;
        this.plantType = plantType;
        this.isScanned = false;
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

    }

    public abstract double stuckProbability();

    public boolean isScanned() {
        return this.isScanned;
    }
}
