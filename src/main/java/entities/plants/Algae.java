package entities.plants;

import entities.Plant;
import entities.enums.PlantAge;
import entities.enums.PlantType;

public class Algae extends Plant {

    public Algae(String name, double mass, PlantAge plantAge) {
        super(name, mass, plantAge, PlantType.ALGAE);
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
        oxygen += 0.5;
        return oxygen;
    }
    public double stuckProbability() {
        return 0.2;
    }

}
