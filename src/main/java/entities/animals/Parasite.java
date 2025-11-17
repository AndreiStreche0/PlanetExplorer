package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;

public class Parasite extends Animal {
    public Parasite(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger);
    }
}
