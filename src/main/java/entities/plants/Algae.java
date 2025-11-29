package entities.plants;

import entities.Plant;
import entities.enums.PlantAge;
import entities.enums.PlantType;

public final class Algae extends Plant {

    private static final double YOUNG_OXYGEN_PRODUCTION = 0.2;
    private static final double MATURE_OXYGEN_PRODUCTION = 0.7;
    private static final double OLD_OXYGEN_PRODUCTION = 0.4;
    private static final double ALGAE_BONUS_OXYGEN = 0.5;
    private static final double ALGAE_STUCK_PROBABILITY = 0.2;

    public Algae(final String name, final double mass, final PlantAge plantAge) {
        super(name, mass, plantAge, PlantType.ALGAE);
    }
    /**
     * calculates oxygen production of this plant type
     * @return oxygen production value
     */
    public double calculateOxygenProduction() {
        double oxygen = switch (super.getPlantAge()) {
            case YOUNG -> YOUNG_OXYGEN_PRODUCTION;
            case MATURE -> MATURE_OXYGEN_PRODUCTION;
            case OLD -> OLD_OXYGEN_PRODUCTION;
            default -> 0.0;
        };
        oxygen += ALGAE_BONUS_OXYGEN;
        return oxygen;
    }
    /**
     * calculates stuck probability of terraBot for this plant type
     * @return probability
     */
    public double stuckProbability() {
        return ALGAE_STUCK_PROBABILITY;
    }
}
