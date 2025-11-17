package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;

public class Omnivore extends Animal {
    public Omnivore(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger);
    }
}
