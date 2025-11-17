package entities;

import entities.enums.PlantAge;
import entities.enums.PlantType;

public abstract class Plant extends Entity implements Evolve{
    private PlantAge plantAge;

    public Plant(String name, double mass, PlantAge plantAge) {
        super(name, mass);
        this.plantAge = plantAge;
    }

    public abstract double getStuckingPercentage();
    public abstract double getOxygen();
}
