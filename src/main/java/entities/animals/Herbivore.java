package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public final class Herbivore extends Animal {
    private static final double BASE_PROBABILITY = 100.0;
    private static final double HERBIVORE_REDUCTION = 85.0;
    private static final double DIVISOR = 10.0;

    public Herbivore(final String name, final double mass, final AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.HERBIVORES);
    }

    /**
     * calculates probability of this animal attacking terraBot.
     * @return probability
     */
    public double attackProbability() {
        return (BASE_PROBABILITY - HERBIVORE_REDUCTION) / DIVISOR;
    }

    /**
     * @return if the animal can eat animals
     */
    public boolean eatsAnimals() {
        return true;
    }
}
