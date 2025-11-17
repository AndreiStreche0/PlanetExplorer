package entities;

import entities.Entity;
import entities.enums.AnimalHunger;

public abstract class Animal extends Entity{
    private AnimalHunger hunger;

    public Animal(String name, double mass, AnimalHunger hunger) {
        super(name, mass);
        this.hunger = hunger;
    }

    public AnimalHunger getHunger() {
        return hunger;
    }

    public void setHunger(AnimalHunger hunger) {
        this.hunger = hunger;
    }
}
