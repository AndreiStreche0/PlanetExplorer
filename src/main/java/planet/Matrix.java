package planet;

import entities.*;
import fileio.*;
import planet.Cell;

public class Matrix {
    private final int width;
    private final int height;
    private Cell[][] cells;

    public Matrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void setCells(TerritorySectionParamsInput inputParams) {
        for (WaterInput water : inputParams.water) {
            Water newWater = CreateEntity.createWater(water);
            for (PairInput coords : water.sections) {
                cells[coords.x][coords.y].setCellWater(newWater);
            }
        }
        for (AnimalInput animals : inputParams.animals) {
            Animal newAnimal = CreateEntity.createAnimal(animals);
            for (PairInput coords : animals.sections) {
                cells[coords.x][coords.y].setCellAnimal(newAnimal);
            }
        }
        for (SoilInput soils : inputParams.soil) {
            Soil newSoil = CreateEntity.createSoil(soils);
            for (PairInput coords : soils.sections) {
                cells[coords.x][coords.y].setCellSoil(newSoil);
            }
        }
        for (AirInput airs : inputParams.air) {
            Air newAir = CreateEntity.createAir(airs);
            for (PairInput coords : airs.sections) {
                cells[coords.x][coords.y].setCellAir(newAir);
            }
        }
        for (PlantInput plants : inputParams.plants) {
            Plant newPlant = CreateEntity.createPlant(plants);
            for (PairInput coords : plants.sections) {
                cells[coords.x][coords.y].setCellPlant(newPlant);
            }
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Cell getCell(int x, int y) {
        if (x >= 0 && x < height && y >= 0 && y < width) {
            return cells[x][y];
        } else {
            return null;
        }
    }
}
