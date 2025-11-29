package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;
import entities.enums.AnimalType;

public class Detritivore extends Animal {
    public Detritivore(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger, AnimalType.DETRITIVORES);
    }
    public double attackProbability() {
        return (100 - 90) / 10.0    ;
    }
    public boolean eatsAnimals() {
        return false;
    }
}
