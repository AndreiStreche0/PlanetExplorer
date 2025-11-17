package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;

public class Herbivore extends Animal {
    public Herbivore(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger);
    }
}
