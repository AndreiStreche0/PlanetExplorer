package entities.plants;

import entities.Plant;
import entities.enums.PlantAge;
import entities.enums.PlantType;

public class Fern extends Plant {

    public Fern(String name, double mass, PlantAge plantAge) {
        super(name, mass, plantAge, PlantType.FERNS);
    }

    @Override
    public double getStuckingPercentage() {
        return 0;
    }

    @Override
    public double getOxygen() {
        return 0;
    }

    public double calculateOxygenProduction() {
        double oxygen = 0;
        switch (super.getPlantAge()) {
            case YOUNG:
                oxygen = 0.2;
                break;
            case MATURE:
                oxygen = 0.7;
                break;
            case OLD:
                oxygen = 0.4;
                break;
            default:
                oxygen = 0.0;
                break;
        }
        return oxygen;
    }
    public double stuckProbability() {
        return 0.3;
    }
}
