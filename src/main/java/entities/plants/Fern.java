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
        double oxygen = switch (super.getPlantAge()) {
            case YOUNG -> 0.2;
            case MATURE -> 0.7;
            case OLD -> 0.4;
            default -> 0.0;
        };
        return oxygen;
    }
    public double stuckProbability() {
        return 0.3;
    }
}
