package entities;

import entities.enums.PlantAge;
import entities.enums.PlantType;
import planet.Cell;
/**
 * class that handles plants, their characteristics, types and evolving logic
 */
public abstract class Plant extends Entity implements Evolve {
    private static final double SOIL_GROWTH_BONUS = 0.2;
    private static final double WATER_SCAN_GROWTH_BONUS = 0.2;
    private static final double AGE_THRESHOLD = 1.0;
    private static final double RESET_GROWTH_RATE = 0;
    private PlantAge plantAge;
    private final PlantType plantType;
    private boolean scanned;
    private double growthRate;

    public Plant(final String name, final double mass, final PlantAge plantAge,
                 final PlantType plantType) {
        super(name, mass);
        this.plantAge = plantAge;
        this.plantType = plantType;
        this.scanned = false;
        this.growthRate = 0;
    }

    /**
     * @param plantAge sets it
     */
    public void setPlantAge(final PlantAge plantAge) {
        this.plantAge = plantAge;
    }

    /**
     * @param scanned - sets it
     */
    @Override
    public void setScanned(final boolean scanned) {
        this.scanned = scanned;
    }

    /**
     * @return displayType
     */
    public String getDisplayType() {
        return plantType.getDisplayType();
    }

    /**
     * @return PlantAge
     */
    public PlantAge getPlantAge() {
        return plantAge;
    }

    /**
     * abstract method to calculate the amount of oxygen the plant will produce based on its type
     * @return oxygen amount
     */
    public abstract double calculateOxygenProduction();

    /**
     * evolving logic and how the plant will grow based on the other entities
     * @param currentCell the cell the plant is on
     * @param timeStamp timestamp of current simulation
     */
    public void evolve(final Cell currentCell, final int timeStamp) {
        if (currentCell.getCellSoil() != null) {
            this.grow(currentCell, SOIL_GROWTH_BONUS);
        }
        if (currentCell.getCellWater() != null && currentCell.getCellWater().isScanned()) {
            this.grow(currentCell, WATER_SCAN_GROWTH_BONUS);
        }
    }

    /**
     * grows plant and changes age
     * @param currCell the cell the plant is on
     * @param growth how much the plant will grow
     */
    public void grow(final Cell currCell, final double growth) {
        if (growthRate + growth >= AGE_THRESHOLD) {
            growthRate = RESET_GROWTH_RATE;
            switch (plantAge) {
                case YOUNG:
                    this.setPlantAge(PlantAge.MATURE);
                    break;
                case MATURE:
                    this.setPlantAge(PlantAge.OLD);
                    break;
                case OLD:
                    currCell.setCellPlant(null);
                    break; // Adăugat 'break' pentru a îndeplini Checkstyle
                default: // Adăugat clauza 'default' pentru a îndeplini Checkstyle
                    break;
            }
        } else {
            growthRate += growth;
        }
    }

    /**
     * abstract method to check for the probability of the robot getting stuck in this plant
     * @return the probability
     */
    public abstract double stuckProbability();

    /**
     * @return if the plant is scanned
     */
    public boolean isScanned() {
        return this.scanned;
    }
}
