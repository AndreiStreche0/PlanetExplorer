package entities;

import entities.enums.AnimalHunger;
import entities.enums.AnimalType;
import planet.Cell;

public abstract class Animal extends Entity{
    private AnimalHunger hunger;
    private final AnimalType animalType;
    private boolean scanned;
    private int scanTimeStamp;
    private boolean sick;
    private double nutritionalAddition;
    private int lastTimeStampMovement;

    public Animal(String name, double mass, AnimalHunger hunger, AnimalType animalType) {
        super(name, mass);
        this.hunger = hunger;
        this.animalType = animalType;
        this.scanned = false;
        this.scanTimeStamp = -1;
        this.sick = false;
        this.nutritionalAddition = 0.0;
        this.lastTimeStampMovement = 0;
    }

    public int getLastTimeStampMovement() {
        return lastTimeStampMovement;
    }

    public void setLastTimeStampMovement(int lastTimeStampMovement) {
        this.lastTimeStampMovement = lastTimeStampMovement;
    }

    public abstract boolean eatsAnimals();

    public boolean isSick() {
        return this.sick;
    }

    public void setSick(boolean sick) {
        this.sick = sick;
    }

    public double getNutritionalAddition() {
        return nutritionalAddition;
    }

    public void setNutritionalAddition(double nutritionalAddition) {
        this.nutritionalAddition = nutritionalAddition;
    }

    public int getScanTimeStamp() {
        return scanTimeStamp;
    }

    public void setScanTimeStamp(int scanTimeStamp) {
        this.scanTimeStamp = scanTimeStamp;
    }

    public void setHunger(AnimalHunger hunger) {
        this.hunger = hunger;
    }

    public AnimalHunger getHunger() {
        return hunger;
    }

    public String getDisplayType() {
        return animalType.getDisplayType();
    }
    public boolean isScanned() {
        return this.scanned;
    }

    public abstract double attackProbability();

    @Override
    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

    public void evolve(Cell currentCell, int timestamp) {
        if (currentCell.getCellAir().isToxic()) {
            this.sick = true;
        } else if (this.sick) {
            this.sick = false;
        }
        if (this.hunger == AnimalHunger.WELL_FED && timestamp != this.lastTimeStampMovement) {
            hunger = AnimalHunger.HUNGRY;
        }
    }
}
