package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public final class Parasite extends Animal {
    private static final double BASE_PROBABILITY = 100.0;
    private static final double PARASITE_REDUCTION = 10.0;
    private static final double DIVISOR = 10.0;

    public Parasite(final String name, final double mass, final AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.PARASITES);
    }

    /**
     * calculates probability of this animal attacking terraBot.
     * @return probability
     */
    public double attackProbability() {
        return (BASE_PROBABILITY - PARASITE_REDUCTION) / DIVISOR;
    }

    /**
     * @return if the animal can eat animals
     */
    public boolean eatsAnimals() {
        return true;
    }
}
