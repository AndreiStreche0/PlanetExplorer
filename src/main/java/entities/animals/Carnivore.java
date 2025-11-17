package entities.animals;

import entities.Animal;
import entities.enums.AnimalHunger;

public class Carnivore extends Animal {
    public Carnivore(String name, double mass, AnimalHunger hunger) {
        super(name, mass, hunger);
    }
}
