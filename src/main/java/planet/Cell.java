package planet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import entities.Plant;
import entities.Animal;
import entities.Soil;
import entities.Water;

/**
 * Manages an individual cell and the entities on it
 */
public final class Cell {
    private final int xCoordinate;
    private final int yCoordinate;
    private Air cellAir;
    private Soil cellSoil;
    private Water cellWater;
    private Animal cellAnimal;
    private Plant cellPlant;

    public Cell(final int row, final int column) {
        this.xCoordinate = column;
        this.yCoordinate = row;
        this.cellAir = null;
        this.cellSoil = null;
        this.cellWater = null;
        this.cellAnimal = null;
        this.cellPlant = null;
    }

    public void setCellAir(final Air cellAir) {
        this.cellAir = cellAir;
    }
    public void setCellSoil(final Soil cellSoil) {
        this.cellSoil = cellSoil;
    }
    public void setCellWater(final Water cellWater) {
        this.cellWater = cellWater;
    }
    public void setCellAnimal(final Animal cellAnimal) {
        this.cellAnimal = cellAnimal;
    }
    public void setCellPlant(final Plant cellPlant) {
        this.cellPlant = cellPlant;
    }

    /**
     * @return x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }
    /**
     * @return y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    public Air getCellAir() {
        return cellAir;
    }
    public Soil getCellSoil() {
        return cellSoil;
    }
    public Water getCellWater() {
        return cellWater;
    }
    public Animal getCellAnimal() {
        return cellAnimal;
    }
    public Plant getCellPlant() {
        return cellPlant;
    }

    /**
     * used for the evolution of each entity
     * @param timeStamp current simulation timeStamp
     */
    public void evolve(final int timeStamp) {
        if (this.cellPlant != null && this.cellPlant.isScanned()) {
            this.cellPlant.evolve(this, timeStamp);
        }
        if (this.cellAir != null) {
            this.cellAir.evolve(this, timeStamp);
        }
        if (this.cellSoil != null) {
            this.cellSoil.evolve(this, timeStamp);
        }
        if (this.cellWater != null && this.cellWater.isScanned()) {
            this.cellWater.evolve();
        }
        if (this.cellAnimal != null && this.cellAnimal.isScanned()) {
            this.cellAnimal.evolve(this, timeStamp);
        }

    }

    /**
     * scans plant of current cell
     */
    public void scanPlant() {
        this.cellPlant.setScanned(true);
    }

    /**
     * scans water of current cell
     * @param timeStamp time of scan
     */
    public void scanWater(final int timeStamp) {
        this.cellWater.setScanned(true);
        this.cellWater.setScanTimeStamp(timeStamp);
    }

    /**
     * scans animal of current cell
     * @param timeStamp time of scan
     */
    public void scanAnimal(final int timeStamp) {
        this.cellAnimal.setScanned(true);
        this.cellAnimal.setScanTimeStamp(timeStamp);
    }

    /**
     * gets the score for robot movement
     * @return the score
     */
    public double getScore() {
        double sum = cellSoil.blockingProbability();
        sum += cellAir.damageProbability();
        int numberEntity = 2;

        if (cellAnimal != null) {
            sum += cellAnimal.attackProbability();
            numberEntity++;
        }
        if (cellPlant != null) {
            sum += cellPlant.stuckProbability();
            numberEntity++;
        }
        return (int) Math.round(Math.abs(sum / numberEntity));
    }

    /**
     * gets data of each cell for map output
     * @return a node with the data
     */
    public ObjectNode getCellOutput() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        int foundEntities = 0;

        ArrayNode coordonates = mapper.createArrayNode();
        coordonates.add(xCoordinate);
        coordonates.add(yCoordinate);
        node.set("section", coordonates);

        if (this.cellPlant != null) {
            foundEntities++;
        }
        if (this.cellAnimal != null) {
            foundEntities++;
        }
        if (this.cellWater != null) {
            foundEntities++;
        }

        node.put("totalNrOfObjects", foundEntities);
        node.put("airQuality", this.getCellAir().getStringAirQuality());
        node.put("soilQuality", this.getCellSoil().getStringSoilQuality());

        return node;
    }
}
