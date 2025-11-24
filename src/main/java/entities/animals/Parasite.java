package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public class Parasite extends Animal {
    public Parasite(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.PARASITES);
    }

    public double attackProbability() {
        return (100 - 10) / 10.0;
    }
}
