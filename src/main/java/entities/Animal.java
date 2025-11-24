package entities;

import entities.enums.AnimalHunger;
import entities.enums.AnimalType;
import planet.Cell;

public abstract class Animal extends Entity{
    private AnimalHunger hunger;
    private AnimalType animalType;
    private boolean isScanned;

    public Animal(String name, double mass, AnimalHunger hunger, AnimalType animalType) {
        super(name, mass);
        this.hunger = hunger;
        this.animalType = animalType;
        this.isScanned = false;
    }

    public AnimalHunger getHunger() {
        return hunger;
    }

    public void setHunger(AnimalHunger hunger) {
        this.hunger = hunger;
    }

    public String getDisplayType() {
        return animalType.getDisplayType();
    }
    public boolean isScanned() {
        return this.isScanned;
    }

    public abstract double attackProbability();

    public void evolve(Cell currentCell, int timestamp) {

    }
}
