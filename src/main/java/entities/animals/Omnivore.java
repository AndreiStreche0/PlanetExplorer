package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public class Omnivore extends Animal {
    public Omnivore(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.OMNIVORES);
    }

    public double attackProbability() {
        return (100 - 60) / 10.0;
    }
}
