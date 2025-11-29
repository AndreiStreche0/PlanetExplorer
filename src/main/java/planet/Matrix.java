package planet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.*;
import entities.airs.*;
import entities.animals.Carnivore;
import entities.enums.AnimalHunger;
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
        for (int j = 0; j < this.width; j++) {
            for (int i = 0; i < this.height; i++) {
                this.cells[i][j].evolve(timeStamp);
                if (this.cells[i][j].getCellAnimal() != null
                        && this.cells[i][j].getCellAnimal().isScanned()) {
                    Animal animal = this.cells[i][j].getCellAnimal();
                    int time = timeStamp - animal.getScanTimeStamp();
                    System.out.println("Timestamp " + timeStamp + " " + animal.getName() + " at (" + i + "," + j +
                            ") - scanned: " + cells[i][j].getCellAnimal().isScanned() +
                            ", time since scan: " + animal.getScanTimeStamp() +
                            ", hunger: " + cells[i][j].getCellAnimal().getHunger());
                    if (time % 2 == 0 && animal.getHunger() == AnimalHunger.HUNGRY
                            && animal.getScanTimeStamp() >= 2
                            && animal.getLastTimeStampMovement() != timeStamp) {
                        this.moveAnimal(i, j, timeStamp);
                    }
                }
            }
        }
    }

    private void moveAnimal(int i, int j, int timeStamp) {
        int[][] directions = {{1,0}, {0,1}, {-1,0}, {0,-1}};
        int bestNrEntities = 0;
        double bestWaterQuality = -1;
        Animal currAnimal = cells[i][j].getCellAnimal();
        boolean eatsAnimals = currAnimal.eatsAnimals(), cellHasPlant = false, bestHasPlant = false;
        Cell bestCell = null;
        for (int q = 0; q < 4; q++) {
            int newRow = i + directions[q][0];
            int newColumn = j + directions[q][1];
            if (newRow < 0 || newRow >= this.height || newColumn < 0 || newColumn >= this.width) {
                continue;
            }
            Cell newCell = cells[newRow][newColumn];
            if (!eatsAnimals && newCell.getCellAnimal() != null) {
                continue;
            }
            int nrEntities = 0;
            double waterQuality = 0.0;
            if (newCell.getCellWater() != null && newCell.getCellWater().isScanned()) {
                nrEntities++;
                waterQuality += newCell.getCellWater().getWaterQuality();
            }
            if (newCell.getCellPlant() != null && newCell.getCellPlant().isScanned()) {
                nrEntities++;
                cellHasPlant = true;
            }
            boolean newBest = false;
            if (bestCell == null || nrEntities > bestNrEntities) {
                newBest = true;
            } else if (nrEntities == bestNrEntities && !bestHasPlant && cellHasPlant) {
                newBest = true;
            } else if (nrEntities == bestNrEntities && !bestHasPlant && !cellHasPlant && waterQuality > bestWaterQuality) {
                newBest = true;
            }
            if (newBest) {
                bestCell = newCell;
                bestNrEntities = nrEntities;
                bestWaterQuality = waterQuality;
                if (cellHasPlant) {
                    bestHasPlant = true;
                }
            }
        }
        if (bestCell == null) {
            return;
        }
        if (currAnimal.eatsAnimals() && bestCell.getCellAnimal() != null) {
            Animal prey = bestCell.getCellAnimal();
            currAnimal.setMass(currAnimal.getMass() + prey.getMass());
            currAnimal.setHunger(AnimalHunger.WELL_FED);
            bestCell.setCellAnimal(null);
            System.out.println("Ate animal from coords in first if: " + bestCell.getxCoordinate() + " " + bestCell.getyCoordinate());
        } else {
            if (bestNrEntities == 2 && bestCell.getCellWater().getScanTimeStamp()
                    == bestCell.getCellPlant().getScanTimeStamp()) {
                Water water = bestCell.getCellWater();
                Plant plant = bestCell.getCellPlant();
                double intakeRate = 0.08;
                double waterToDrink = Math.min(currAnimal.getMass() * intakeRate, water.getMass());
                water.setMass(water.getMass() - waterToDrink);
                currAnimal.setMass(currAnimal.getMass() + plant.getMass() + waterToDrink);
                currAnimal.setNutritionalAddition(0.8);
                currAnimal.setHunger(AnimalHunger.WELL_FED);
                bestCell.setCellPlant(null);
                System.out.println("Ate plant from coords in first if: " + bestCell.getxCoordinate() + " " + bestCell.getyCoordinate());
            } else if (bestNrEntities == 1 && bestCell.getCellPlant() != null && bestCell.getCellPlant().isScanned()
                    || bestNrEntities == 2 && bestCell.getCellWater().getScanTimeStamp()
                    < bestCell.getCellPlant().getScanTimeStamp()) {
                Plant plant = bestCell.getCellPlant();
                currAnimal.setMass(currAnimal.getMass() + plant.getMass());
                currAnimal.setNutritionalAddition(0.5);
                currAnimal.setHunger(AnimalHunger.WELL_FED);
                bestCell.setCellPlant(null);
                System.out.println("Ate plant from coords in second if: " + bestCell.getxCoordinate() + " " + bestCell.getyCoordinate());
            } else if (bestNrEntities == 1 && bestCell.getCellWater() != null && bestCell.getCellWater().isScanned() || bestNrEntities == 2
                    && bestCell.getCellWater().getScanTimeStamp()
                    > bestCell.getCellPlant().getScanTimeStamp()) {
                Water water = bestCell.getCellWater();
                double intakeRate = 0.08;
                double waterToDrink = Math.min(currAnimal.getMass() * intakeRate, water.getMass());
                water.setMass(water.getMass() - waterToDrink);
                currAnimal.setMass(currAnimal.getMass() + waterToDrink);
                currAnimal.setNutritionalAddition(0.5);
                currAnimal.setHunger(AnimalHunger.WELL_FED);
            }
        }
        System.out.println(currAnimal.getName() + " moved from " + "row = " + i + "column = " + j + "to: newRow = " + bestCell.getyCoordinate() + "newColumn = " + bestCell.getxCoordinate() + "at :" + timeStamp);
        bestCell.setCellAnimal(currAnimal);
        bestCell.getCellAnimal().setLastTimeStampMovement(timeStamp);
        cells[i][j].setCellAnimal(null);
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
        int bestScore = Integer.MAX_VALUE;
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        //up, right, down, left

        for (int i = 0; i < 4; i++) {
            int currY = yCoordBot + directions[i][0];
            int currX = xCoordBot + directions[i][1];
            if (currX >= 0 && currX < width && currY >= 0 && currY < height
                    && cells[currY][currX].getScore() < bestScore) {
                bestScore = cells[currY][currX].getScore();
                bestCell = cells[currY][currX];
            }
        }
        int score = (int) Math.round(bestScore);
        if (currEnergy < score) {
            return null;
        }
        return bestCell;
    }

    public boolean startWeatherChange (CommandInput commandInput, String weatherEventName) {
        boolean success = false;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Air air = cells[i][j].getCellAir();

                switch (weatherEventName) {
                    case "peopleHiking":
                        if (air.getDisplayType().equals("MountainAir")) {
                            success = true;
                            ((MountainAir)air).startPeopleHiking(commandInput.numberOfHikers);
                        }
                        break;
                    case "desertStorm":
                        if (air.getDisplayType().equals("DesertAir")) {
                            success = true;
                            ((DesertAir)air).startDesertStorm(commandInput.desertStorm);
                        }
                        break;
                    case "newSeason":
                        if (air.getDisplayType().equals("TemperateAir")) {
                            success = true;
                            ((TemperateAir)air).startNewSeason(commandInput.season);
                        }
                        break;
                    case "polarStorm":
                        if (air.getDisplayType().equals("PolarAir")) {
                            success = true;
                            ((PolarAir)air).startPolarStorm(commandInput.windSpeed);
                        }
                        break;
                    case "rainfall":
                        if (air.getDisplayType().equals("TropicalAir")) {
                            success = true;
                            ((TropicalAir)air).startRainfall(commandInput.rainfall);
                        }
                        break;
                    default:
                        return false;
                }

            }
        }


        return success;
    }
}
