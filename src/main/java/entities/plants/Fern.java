package entities.plants;

import entities.Plant;
import entities.enums.PlantAge;
import entities.enums.PlantType;

public final class Fern extends Plant {
    private static final double YOUNG_OXYGEN_PRODUCTION = 0.2;
    private static final double MATURE_OXYGEN_PRODUCTION = 0.7;
    private static final double OLD_OXYGEN_PRODUCTION = 0.4;
    private static final double FERN_STUCK_PROBABILITY = 0.3;

    public Fern(final String name, final double mass, final PlantAge plantAge) {
        super(name, mass, plantAge, PlantType.FERNS);
    }
    /**
     * calculates oxygen production of this plant type
     * @return oxygen production value
     */
    public double calculateOxygenProduction() {
        return switch (super.getPlantAge()) {
            case YOUNG -> YOUNG_OXYGEN_PRODUCTION;
            case MATURE -> MATURE_OXYGEN_PRODUCTION;
            case OLD -> OLD_OXYGEN_PRODUCTION;
            default -> 0.0;
        };
    }
    /**
     * calculates stuck probability of terraBot for this plant type
     * @return probability
     */
    public double stuckProbability() {
        return FERN_STUCK_PROBABILITY;
    }
}
