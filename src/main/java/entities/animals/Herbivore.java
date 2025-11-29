package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public class Herbivore extends Animal {
    public Herbivore(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.HERBIVORES);
    }

    public double attackProbability() {
        return (100 - 85) / 10.0;
    }
    public boolean eatsAnimals() {
        return true;
    }
}
