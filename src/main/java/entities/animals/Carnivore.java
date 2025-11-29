package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public final class Carnivore extends Animal {
    private static final double BASE_PROBABILITY = 100.0;
    private static final double CARNIVORE_REDUCTION = 30.0;
    private static final double DIVISOR = 10.0;

    public Carnivore(final String name, final double mass, final AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.CARNIVORES);
    }

    /**
     * @return if the animal can eat animals
     */
    public boolean eatsAnimals() {
        return true;
    }

    /**
     * calculates probability of this animal attacking terraBot.
     * @return probability
     */
    public double attackProbability() {
        return (BASE_PROBABILITY - CARNIVORE_REDUCTION) / DIVISOR;
    }
}
