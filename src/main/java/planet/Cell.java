package planet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import entities.Plant;
import entities.Animal;
import entities.Soil;
import entities.Water;

public class Cell {
    private int xCoordinate;
    private int yCoordinate;
    private Air cellAir;
    private Soil cellSoil;
    private Water cellWater;
    private Animal cellAnimal;
    private Plant cellPlant;

    public Cell(int row, int column) {
        this.xCoordinate = column;
        this.yCoordinate = row;
        this.cellAir = null;
        this.cellSoil = null;
        this.cellWater = null;
        this.cellAnimal = null;
        this.cellPlant = null;
    }

    public void setCoordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    public void setCellAir(Air cellAir) {
        this.cellAir = cellAir;
    }
    public void setCellSoil(Soil cellSoil) {
        this.cellSoil = cellSoil;
    }
    public void setCellWater(Water cellWater) {
        this.cellWater = cellWater;
    }
    public void setCellAnimal(Animal cellAnimal) {
        this.cellAnimal = cellAnimal;
    }
    public void setCellPlant(Plant cellPlant) {
        this.cellPlant = cellPlant;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }
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

    public void evolve(int timeStamp) {
        if (this.cellAir != null) {
            this.cellAir.evolve(this, timeStamp);
        }
        if (this.cellSoil != null) {
            this.cellSoil.evolve(this, timeStamp);
        }
        if (this.cellWater != null && this.cellWater.isScanned()) {
            this.cellWater.evolve(this, timeStamp);
        }
        if (this.cellAnimal != null && this.cellAnimal.isScanned()) {
            this.cellAnimal.evolve(this, timeStamp);
        }
        if (this.cellPlant != null && this.cellPlant.isScanned()) {
            this.cellPlant.evolve(this, timeStamp);
        }
    }

    public double getScore() {
        System.out.println("the cell cordonates are:" + this.getxCoordinate() + ", " + this.getyCoordinate());
        double sum = cellSoil.blockingProbability();
        System.out.println("Soil probability is " + cellSoil.blockingProbability());
        sum += cellAir.damageProbability();
        System.out.println("Air probability is " + cellAir.damageProbability());

        if (cellAnimal != null) {
            sum += cellAnimal.attackProbability();
            System.out.println("Animal probability is " + cellAnimal.attackProbability());
        }
        if (cellPlant != null) {
            sum += cellPlant.stuckProbability();
            System.out.println("Plant probability is " + cellPlant.stuckProbability());
        }
        return (double) Math.abs(sum / 4.0);
    }

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
