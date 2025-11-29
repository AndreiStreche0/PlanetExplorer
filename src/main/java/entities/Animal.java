package entities;

import entities.enums.AnimalHunger;
import entities.enums.AnimalType;
import planet.Cell;

/**
 * class that handles animals, their characteristics, types and evolving logic
 */
public abstract class Animal extends Entity {
    private static final double WATER_INTAKE_RATE = 0.08;
    private AnimalHunger hunger;
    private final AnimalType animalType;
    private boolean scanned;
    private int scanTimeStamp;
    private boolean sick;
    private double nutritionalAddition;
    private int lastTimeStampMovement;

    public Animal(final String name, final double mass,
                  final AnimalHunger hunger, final AnimalType animalType) {
        super(name, mass);
        this.hunger = hunger;
        this.animalType = animalType;
        this.scanned = false;
        this.scanTimeStamp = -1;
        this.sick = false;
        this.nutritionalAddition = 0.0;
        this.lastTimeStampMovement = 0;
    }

    /**
     * Gets the timestamp of the last movement.
     * @return the timestamp.
     */
    public int getLastTimeStampMovement() {
        return lastTimeStampMovement;
    }

    /**
     * Sets the timestamp of the last movement.
     * @param lastTimeStampMovement the timestamp to set.
     */
    public void setLastTimeStampMovement(final int lastTimeStampMovement) {
        this.lastTimeStampMovement = lastTimeStampMovement;
    }

    /**
     * Abstract method to check if animal eats other animals.
     * @return true if carnivorous behavior.
     */
    public abstract boolean eatsAnimals();

    /**
     * Checks if the animal is sick.
     * @return true if sick.
     */
    public boolean isSick() {
        return this.sick;
    }

    /**
     * Gets the nutritional addition value.
     * @return the value.
     */
    public double getNutritionalAddition() {
        return nutritionalAddition;
    }

    /**
     * Sets the nutritional addition value.
     * @param nutritionalAddition the value to set.
     */
    public void setNutritionalAddition(final double nutritionalAddition) {
        this.nutritionalAddition = nutritionalAddition;
    }

    /**
     * Gets the timestamp when the animal was scanned.
     * @return the timestamp.
     */
    public int getScanTimeStamp() {
        return scanTimeStamp;
    }

    /**
     * Sets the scan timestamp.
     * @param scanTimeStamp the timestamp.
     */
    public void setScanTimeStamp(final int scanTimeStamp) {
        this.scanTimeStamp = scanTimeStamp;
    }

    /**
     * Gets the hunger status.
     * @return the hunger enum.
     */
    public AnimalHunger getHunger() {
        return hunger;
    }

    /**
     * Gets the display type string.
     * @return the display type.
     */
    public String getDisplayType() {
        return animalType.getDisplayType();
    }

    /**
     * @return if the animal is scanned
     */
    public boolean isScanned() {
        return this.scanned;
    }

    /**
     * Calculates the attack probability.
     * @return the probability.
     */
    public abstract double attackProbability();

    /**
     * @param scanned - sets it
     */
    @Override
    public void setScanned(final boolean scanned) {
        this.scanned = scanned;
    }

    /**
     * evolving logic depending on the cell's air
     * @param currentCell the current cell of animal
     * @param timestamp current simulation timestamp
     */
    public void evolve(final Cell currentCell, final int timestamp) {
        if (currentCell.getCellAir().isToxic()) {
            this.sick = true;
        } else if (this.sick) {
            this.sick = false;
        }
        if (this.hunger == AnimalHunger.WELL_FED
                && timestamp != this.lastTimeStampMovement) {
            hunger = AnimalHunger.HUNGRY;
        }
    }

    /**
     * method for the animal to eat the plant after movement
     * @param cell plant's cell
     * @param plant the plant it will eat
     */
    public void eatPlant(final Cell cell, final Plant plant) {
        super.setMass(super.getMass() + plant.getMass());
        this.hunger = AnimalHunger.WELL_FED;
        cell.setCellPlant(null);
    }

    /**
     * method for the animal to eat the animal after movement
     * @param cell animal's cell
     * @param prey the animal it will eat
     */
    public void eatAnimal(final Cell cell, final Animal prey) {
        super.setMass(super.getMass() + prey.getMass());
        this.hunger = AnimalHunger.WELL_FED;
        cell.setCellAnimal(null);
    }

    /**
     * method for the animal to drink water after movement
     * @param water water's cell
     */
    public void drinkWater(final Water water) {
        double waterToDrink = Math.min(super.getMass() * WATER_INTAKE_RATE, water.getMass());
        super.setMass(super.getMass() + waterToDrink);
        this.hunger = AnimalHunger.WELL_FED;
    }
}
