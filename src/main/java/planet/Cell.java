package planet;

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

    public Cell(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
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
}
