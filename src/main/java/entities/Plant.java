package entities;

import entities.enums.PlantAge;
import entities.enums.PlantType;

public abstract class Plant extends Entity implements Evolve{
    private PlantType type;
    private double growthRate;
    private double oxygen;
    private double blockingPercentage;
    private PlantAge plantAge;

    public Plant(String name, double mass, double oxygen, double blockingPercentage, double growthRate, PlantAge plantAge) {
        super(name, mass);
        this.growthRate = growthRate;
        this.oxygen = oxygen;
        this.blockingPercentage = blockingPercentage;
        this.plantAge = plantAge;
    }
    public abstract double getStuckingPercentage();
}
