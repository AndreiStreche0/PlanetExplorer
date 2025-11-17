package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;

public class Detrivore extends Animal {
    public Detrivore(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger);
    }
}
