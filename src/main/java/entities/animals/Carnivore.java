package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public class Carnivore extends Animal {
    public Carnivore(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.CARNIVORES);
    }
    public boolean eatsAnimals() {
        return true;
    }

    public double attackProbability() {
        return (100 - 30) / 10.0;
    }
}
