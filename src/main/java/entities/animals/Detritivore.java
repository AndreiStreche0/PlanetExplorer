package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public final class Detritivore extends Animal {
    private static final double PROBABILITY_TO_ATTACK = 1.0;
    public Detritivore(final String name, final double mass, final AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.DETRITIVORES);
    }
    /**
     * calculates probability of this animal attacking terraBot.
     * @return probability
     */
    public double attackProbability() {
        return PROBABILITY_TO_ATTACK;
    }
    /**
     * @return if the animal can eat animals
     */
    public boolean eatsAnimals() {
        return false;
    }
}
