package planet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.*;
import fileio.*;
import planet.Cell;
import terrabot.TerraBot;

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
            for (PairInput coords : water.sections) {
                Water newWater = CreateEntity.createWater(water);
                cells[coords.y][coords.x].setCellWater(newWater);
            }
        }
        for (AnimalInput animals : inputParams.animals) {
            for (PairInput coords : animals.sections) {
                Animal newAnimal = CreateEntity.createAnimal(animals);
                cells[coords.y][coords.x].setCellAnimal(newAnimal);
            }
        }
        for (SoilInput soils : inputParams.soil) {
            for (PairInput coords : soils.sections) {
                Soil newSoil = CreateEntity.createSoil(soils);
                cells[coords.y][coords.x].setCellSoil(newSoil);
            }
        }
        for (AirInput airs : inputParams.air) {
            for (PairInput coords : airs.sections) {
                Air newAir = CreateEntity.createAir(airs);
                cells[coords.y][coords.x].setCellAir(newAir);
            }
        }
        for (PlantInput plants : inputParams.plants) {
            for (PairInput coords : plants.sections) {
                Plant newPlant = CreateEntity.createPlant(plants);
                cells[coords.y][coords.x].setCellPlant(newPlant);
            }
        }
    }

    public void evolve(int timeStamp) {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.cells[i][j].evolve(timeStamp);
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
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cells[y][x];
        } else {
            return null;
        }
    }

    public ArrayNode getMapOutput() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode outputArray = mapper.createArrayNode();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                outputArray.add(cells[i][j].getCellOutput());
            }
        }
        return outputArray;
    }

    public Cell findBestCell (int xCoordBot, int yCoordBot, int currEnergy) {
        Cell bestCell = null;
        double bestScore = Double.MAX_VALUE;
        int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        //up, right, down, left

        for (int i = 0; i < 4; i++) {
            int currX = xCoordBot + directions[i][0];
            int currY = yCoordBot + directions[i][1];
            if (currX >= 0 && currX < width && currY >= 0 && currY < height
                    && cells[currY][currX].getScore() < bestScore) {
                bestScore = cells[currY][currX].getScore();
                bestCell = cells[currY][currX];
            }
        }
        System.out.println("best score is:" + bestScore);
        int score = (int) Math.round(bestScore);
        if (currEnergy < score) {
            return null;
        }
        return bestCell;
    }
}
