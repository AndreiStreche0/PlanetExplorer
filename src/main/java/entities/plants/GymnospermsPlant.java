package entities.plants;

import entities.Plant;
import entities.enums.PlantAge;

public class GymnospermsPlant extends Plant {

    public GymnospermsPlant(String name, double mass, PlantAge plantAge) {
        super(name, mass, plantAge);
    }

    @Override
    public double getStuckingPercentage() {
        return 0;
    }

    @Override
    public double getOxygen() {
        return 0;
    }

    @Override
    public void evolve() {

    }
}
