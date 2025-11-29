package planet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.Air;
import entities.Plant;
import entities.Animal;
import entities.Water;
import entities.Soil;
import entities.CreateEntity;
import entities.enums.AnimalHunger;
import fileio.TerritorySectionParamsInput;
import fileio.WaterInput;
import fileio.PairInput;
import fileio.AnimalInput;
import fileio.SoilInput;
import fileio.PlantInput;
import fileio.AirInput;
import fileio.CommandInput;

/**
 * handles the map logic by managing the cells
 */
public class Matrix {
    private static final int NUMBER_OF_NEIGHBOURS = 4;
    private static final double NUTRITION_BOTH_ENTITIES = 0.8;
    private static final double NUTRITION_ONE_ENTITY = 0.5;
    private static final double NUMBER_OF_HIKERS_MULTIPLIER = 0.1;
    private static final double POLAR_STORM_MULTIPLIER = 0.2;
    private static final double RAINFALL_MULTIPLIER = 0.3;
    private static final double SPRING_VALUE = 15.0;
    private static final double DESERT_STORM_VALUE = 30.0;
    private final int width;
    private final int height;
    private final Cell[][] cells;

    public Matrix(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * a setter that sets all the cells with their entities
     * @param inputParams parameters from input
     */
    public void setCells(final TerritorySectionParamsInput inputParams) {
        for (WaterInput water : inputParams.getWater()) {
            for (PairInput coords : water.getSections()) {
                Water newWater = CreateEntity.createWater(water);
                cells[coords.getY()][coords.getX()].setCellWater(newWater);
            }
        }
        for (AnimalInput animals : inputParams.getAnimals()) {
            for (PairInput coords : animals.getSections()) {
                Animal newAnimal = CreateEntity.createAnimal(animals);
                cells[coords.getY()][coords.getX()].setCellAnimal(newAnimal);
            }
        }
        for (SoilInput soils : inputParams.getSoil()) {
            for (PairInput coords : soils.getSections()) {
                Soil newSoil = CreateEntity.createSoil(soils);
                cells[coords.getY()][coords.getX()].setCellSoil(newSoil);
            }
        }
        for (AirInput airs : inputParams.getAir()) {
            for (PairInput coords : airs.getSections()) {
                Air newAir = CreateEntity.createAir(airs);
                cells[coords.getY()][coords.getX()].setCellAir(newAir);
            }
        }
        for (PlantInput plants : inputParams.getPlants()) {
            for (PairInput coords : plants.getSections()) {
                Plant newPlant = CreateEntity.createPlant(plants);
                cells[coords.getY()][coords.getX()].setCellPlant(newPlant);
            }
        }
    }

    /**
     * implements evolution logic for each cell and animal movement
     * @param timeStamp time of current simulation
     */
    public void evolve(final int timeStamp) {
        for (int j = 0; j < this.width; j++) {
            for (int i = 0; i < this.height; i++) {
                this.cells[i][j].evolve(timeStamp);
                if (this.cells[i][j].getCellAnimal() != null
                        && this.cells[i][j].getCellAnimal().isScanned()) {
                    Animal animal = this.cells[i][j].getCellAnimal();
                    int time = timeStamp - animal.getScanTimeStamp();
                    if (time % 2 == 0 && animal.getHunger() == AnimalHunger.HUNGRY
                            && animal.getScanTimeStamp() >= 2
                            && animal.getLastTimeStampMovement() != timeStamp) {
                        this.moveAnimal(i, j, timeStamp);
                    }
                }
            }
        }
    }

    /**
     * finds the best cell for animal movement and moves the animal
     * @param i current row coordinate
     * @param j current column coordinate
     * @param timeStamp time of current simulation
     */
    private void moveAnimal(final int i, final int j, final int timeStamp) {
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int bestNrEntities = 0;
        double bestWaterQuality = -1;
        Animal currAnimal = cells[i][j].getCellAnimal();
        boolean eatsAnimals = currAnimal.eatsAnimals(), cellHasPlant = false, bestHasPlant = false;
        Cell bestCell = null;
        for (int q = 0; q < NUMBER_OF_NEIGHBOURS; q++) {
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
            } else if (nrEntities == bestNrEntities && !bestHasPlant
                    && waterQuality > bestWaterQuality) {
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
            currAnimal.eatAnimal(bestCell, bestCell.getCellAnimal());
        } else {
            Plant plant = bestCell.getCellPlant();
            Water water = bestCell.getCellWater();
            if (bestNrEntities == 2) {
                currAnimal.drinkWater(bestCell.getCellWater());
                currAnimal.eatPlant(bestCell, bestCell.getCellPlant());
                currAnimal.setNutritionalAddition(NUTRITION_BOTH_ENTITIES);
            } else if (bestNrEntities == 1 && plant != null && plant.isScanned()) {
                currAnimal.eatPlant(bestCell, bestCell.getCellPlant());
                currAnimal.setNutritionalAddition(NUTRITION_ONE_ENTITY);
            } else if (bestNrEntities == 1 && water != null && water.isScanned()) {
                currAnimal.drinkWater(bestCell.getCellWater());
                currAnimal.setNutritionalAddition(NUTRITION_ONE_ENTITY);
            }
        }
        bestCell.setCellAnimal(currAnimal);
        bestCell.getCellAnimal().setLastTimeStampMovement(timeStamp);
        cells[i][j].setCellAnimal(null);
    }

    /**
     * @return the cell of given coordinates
     */
    public Cell getCell(final int x, final int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cells[y][x];
        } else {
            return null;
        }
    }

    /**
     * gets the data of each cell for map output
     * @return array of nodes for final output
     */
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

    /**
     * finds the best cell for robot movement
     * @param xCoordBot yCoordBot current coordinates
     * @param currEnergy current robot energy
     * @return the Cell it found for movement
     */
    public Cell findBestCellForRobot(final int xCoordBot, final int yCoordBot,
                                     final int currEnergy) {
        Cell bestCell = null;
        double bestScore = Double.MAX_VALUE;
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        //up, right, down, left

        for (int i = 0; i < NUMBER_OF_NEIGHBOURS; i++) {
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
    /**
     *starts weather change for each cell and calculates the effects it will have
     * @param commandInput the command input
     * @param weatherEventName name of weather event
     * @return if the weather changed in any cell
     */
    public boolean startWeatherChange(final CommandInput commandInput,
                                      final String weatherEventName) {
        boolean success = false;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Air air = cells[i][j].getCellAir();
                double weatherChangeAmount = 0.0;
                switch (weatherEventName) {
                    case "peopleHiking":
                        if (air.getDisplayType().equals("MountainAir")) {
                            success = true;
                            weatherChangeAmount = commandInput.getNumberOfHikers()
                                    * NUMBER_OF_HIKERS_MULTIPLIER;
                        }
                        break;
                    case "desertStorm":
                        if (air.getDisplayType().equals("DesertAir")) {
                            success = true;
                            weatherChangeAmount = commandInput.isDesertStorm()
                                    ? DESERT_STORM_VALUE : 0;
                        }
                        break;
                    case "newSeason":
                        if (air.getDisplayType().equals("TemperateAir")) {
                            success = true;
                            weatherChangeAmount = commandInput.getSeason().equals("Spring")
                                    ? SPRING_VALUE : 0;
                        }
                        break;
                    case "polarStorm":
                        if (air.getDisplayType().equals("PolarAir")) {
                            success = true;
                            weatherChangeAmount = commandInput.getWindSpeed()
                                    * POLAR_STORM_MULTIPLIER;
                        }
                        break;
                    case "rainfall":
                        if (air.getDisplayType().equals("TropicalAir")) {
                            success = true;
                            weatherChangeAmount = commandInput.getRainfall()
                                    * RAINFALL_MULTIPLIER;
                        }
                        break;
                    default:
                        break;
                }
                if (success) {
                    air.startWeatherChange(weatherEventName, weatherChangeAmount);
                }
            }
        }
        return success;
    }
}
