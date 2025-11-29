package simulation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import entities.Soil;
import entities.Plant;
import entities.Animal;
import entities.Water;
import fileio.CommandInput;
import fileio.SimulationInput;
import planet.Cell;
import planet.Matrix;
import terrabot.TerraBot;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Manages the current Simulation, the map and the terraBot
 */
public class Simulation {
    private static final int SCAN_ENERGY_COST = 7;
    private static final int LEARN_ENERGY_COST = 2;
    private static final int IMPROVE_ENERGY_COST = 10;
    private static final double PLANTING_IMPROVEMENT = 0.3;
    private static final double FERTILIZE_IMPROVEMENT = 0.3;
    private static final double HUMIDITY_IMPROVEMENT = 0.2;
    private static final double MOISTURE_IMPROVEMENT = 0.2;

    private Matrix mapMatrix;
    private boolean running;
    private TerraBot terraBot;
    private int timeStamp;
    private final SimulationInput startSimulationData;
    private final ObjectMapper mapper = new ObjectMapper();
    public Simulation(final SimulationInput startSimulationData) {
        this.startSimulationData = startSimulationData;
        this.running = false;
    }

    /**
     * @return if the current simulation is running
     */
    public boolean isRunning() {
        return this.running;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return terraBot
     */
    public TerraBot getTerraBot() {
        return terraBot;
    }

    /**
     * Implements evolution logic for the map
     */
    public void evolve() {
        this.timeStamp++;
        mapMatrix.evolve(this.timeStamp);
    }

    /**
     * Starts the current Simulation, generates the map and robot
     */
    public void start() {
        this.running = true;
        this.timeStamp = 0;

        Scanner scanner = new Scanner(startSimulationData.getTerritoryDim()).useDelimiter("x");
        int height = scanner.nextInt();
        int width = scanner.nextInt();
        scanner.close();

        this.mapMatrix = new Matrix(width, height);
        this.terraBot = new TerraBot(startSimulationData.getEnergyPoints(), 0, 0);
        this.mapMatrix.setCells(startSimulationData.getTerritorySectionParams());
    }

    /**
     * stops the simulation
     */
    public void stop() {
        this.running = false;
    }

    /**
     * gets the knowledge base of the robot
     * @return that an Array with that knowledge for final output
     */
    public ArrayNode printKnowledgeBase() {
        final ObjectMapper mapper = new ObjectMapper();
        ArrayNode outputArray = mapper.createArrayNode();

        LinkedHashMap<String, List<String>> knowledgeBase = terraBot.getKnowledgeBase();
        for (Map.Entry<String, List<String>> entry : knowledgeBase.entrySet()) {
            ObjectNode itemNode = mapper.createObjectNode();
            itemNode.put("topic", entry.getKey());

            ArrayNode factArray = mapper.createArrayNode();
            for (String fact : entry.getValue()) {
                factArray.add(fact);
            }
            itemNode.set("facts", factArray);
            outputArray.add(itemNode);
        }
        return outputArray;
    }

    /**
     * charges the robot
     * @param chargeTime is the charge time
     * @return returns message for final output
     */
    public String chargeRobot(final int chargeTime) {
        this.terraBot.setChargeTime(chargeTime);
        this.terraBot.setCharging(true);
        return "Robot battery is charging.";
    }

    /**
     * scans the cell the robot is on
     * @param color found color
     * @param smell found smell
     * @param sound found sound
     * @return String for final output
     */
    public String scanObject(final String color, final String smell, final String sound) {
        int y = terraBot.getyCoord(), x = terraBot.getxCoord();
        int battery = terraBot.getEnergy();
        Cell currCell = mapMatrix.getCell(x, y);

        if (battery < SCAN_ENERGY_COST) {
            return "ERROR: Not enough energy to perform action";
        }
        if (color.equals("none") && smell.equals("none") && sound.equals("none")
                && currCell.getCellWater() != null) {
            currCell.scanWater(this.timeStamp);
            terraBot.setEnergy(battery - SCAN_ENERGY_COST);
            terraBot.scanItem(currCell.getCellWater().getName());
            return "The scanned object is water.";
        }
        if (color.compareTo("none") != 0 && smell.compareTo("none") != 0 && sound.equals("none")
                && currCell.getCellPlant() != null) {
            currCell.scanPlant();
            terraBot.setEnergy(battery - SCAN_ENERGY_COST);
            terraBot.scanItem(currCell.getCellPlant().getName());
            return "The scanned object is a plant.";
        }
        if (color.compareTo("none") != 0 && smell.compareTo("none") != 0
                && sound.compareTo("none") != 0 && currCell.getCellAnimal() != null) {
            currCell.scanAnimal(this.timeStamp);
            terraBot.setEnergy(battery - SCAN_ENERGY_COST);
            terraBot.scanItem(currCell.getCellAnimal().getName());
            return "The scanned object is an animal.";
        }

        return "ERROR: Object not found. Cannot perform action";
    }

    /**
     * for the robot to learn new fact
     * @param item the object the fact is about
     * @param fact the fact
     * @return string for final output
     */
    public String learnFact(final String item, final String fact) {
        if (terraBot.getEnergy() < LEARN_ENERGY_COST) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        if (terraBot.hasScanned(item)) {
            terraBot.addFact(item, fact);
            terraBot.setEnergy(terraBot.getEnergy() - LEARN_ENERGY_COST);
            return "The fact has been successfully saved in the database.";
        }
        return "ERROR: Subject not yet saved. Cannot perform action";
    }

    /**
     * method for environment improvement of current cell
     * @param improvementType the type
     * @param itemName item the improvement relates
     * @return string for final output
     */
    public String improveEnvironment(final String improvementType, final String itemName) {
        Set<String> itemSet = terraBot.getScannedItems();
        LinkedHashMap<String, List<String>> knowledgeBase = terraBot.getKnowledgeBase();
        if (!itemSet.contains(itemName)) {
            return "ERROR: Subject not yet saved. Cannot perform action";
        }
        if (terraBot.getEnergy() < IMPROVE_ENERGY_COST) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        Cell currCell = mapMatrix.getCell(terraBot.getxCoord(), getTerraBot().getyCoord());
        List<String> factList = knowledgeBase.get(itemName);
        if (factList == null) {
            return "ERROR: Fact not yet saved. Cannot perform action";
        }

        switch (improvementType) {
            case "plantVegetation" -> {
                for (String fact : factList) {
                    if (fact.equalsIgnoreCase("Method to plant " + itemName)) {
                        Air air = currCell.getCellAir();
                        air.setOxygenLevel(air.getOxygenLevel() + PLANTING_IMPROVEMENT);
                        terraBot.setEnergy(terraBot.getEnergy() - IMPROVE_ENERGY_COST);
                        terraBot.getScannedItems().remove(itemName);
                        return "The " + itemName + " was planted successfully.";
                    }
                }
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
            case "fertilizeSoil" -> {
                for (String fact : factList) {
                    if (fact.equalsIgnoreCase("Method to fertilize with " + itemName)) {
                        Soil soil = currCell.getCellSoil();
                        soil.setOrganicMatter(soil.getOrganicMatter() + FERTILIZE_IMPROVEMENT);
                        terraBot.setEnergy(terraBot.getEnergy() - IMPROVE_ENERGY_COST);
                        return "The soil was successfully fertilized using " + itemName;
                    }
                }
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
            case "increaseMoisture" -> {
                for (String fact : factList) {
                    if (fact.equalsIgnoreCase("Method to increaseMoisture")) {
                        Soil soil = currCell.getCellSoil();
                        soil.setWaterRetention(soil.getWaterRetention() + MOISTURE_IMPROVEMENT);
                        terraBot.setEnergy(terraBot.getEnergy() - IMPROVE_ENERGY_COST);
                        return "The moisture was successfully increased using " + itemName;
                    }
                }
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
            case "increaseHumidity" -> {
                for (String fact : factList) {
                    if (fact.equalsIgnoreCase("Method to increase humidity.")) {
                        Air air = currCell.getCellAir();
                        air.setHumidity(air.getHumidity() + HUMIDITY_IMPROVEMENT);
                        terraBot.setEnergy(terraBot.getEnergy() - IMPROVE_ENERGY_COST);
                        return "The humidity was successfully increased using " + itemName;
                    }
                }
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
            default -> {
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
        }
    }

    /**
     * robot energy level
     * @return string for final output
     */
    public String getEnergyStatus() {
        return "TerraBot has " + terraBot.getEnergy() + " energy points left.";
    }

    /**
     * collects data of the current cell, including every entity that is on that cell
     * @return node for final output
     */
    public ObjectNode printEnvConditions() {
        ObjectNode cellNode = mapper.createObjectNode();

        Cell currentCell = mapMatrix.getCell(terraBot.getxCoord(), terraBot.getyCoord());

        Soil currentSoil = currentCell.getCellSoil();
        Water currentWater = currentCell.getCellWater();
        Animal currentAnimal = currentCell.getCellAnimal();
        Plant currentPlant = currentCell.getCellPlant();
        Air currentAir = currentCell.getCellAir();

        currentSoil.calculateSoilQuality();
        ObjectNode soilNode = mapper.createObjectNode();
        soilNode.put("type", currentSoil.getDisplayType());
        soilNode.put("name", currentSoil.getName());
        soilNode.put("mass", currentSoil.getMass());
        soilNode.put("nitrogen", currentSoil.getNitrogen());
        soilNode.put("waterRetention", currentSoil.getWaterRetention());
        soilNode.put("soilpH", currentSoil.getSoilpH());
        soilNode.put("organicMatter", currentSoil.getOrganicMatter());
        soilNode.put("soilQuality", currentSoil.getSoilQuality());
        soilNode.put(currentSoil.getSpecificNameCharacteristic(),
                currentSoil.getSpecificCharacteristic());
        cellNode.set("soil", soilNode);

        if (currentPlant != null) {
            ObjectNode plantNode = mapper.createObjectNode();
            plantNode.put("type", currentPlant.getDisplayType());
            plantNode.put("name", currentPlant.getName());
            plantNode.put("mass", currentPlant.getMass());
            cellNode.set("plants", plantNode);
        }

        if (currentAnimal != null) {
            ObjectNode animalNode = mapper.createObjectNode(); // Note: "animals" key
            animalNode.put("type", currentAnimal.getDisplayType());
            animalNode.put("name", currentAnimal.getName());
            animalNode.put("mass", currentAnimal.getMass());
            cellNode.set("animals", animalNode);
        }

        if (currentWater != null) {
            ObjectNode waterNode = mapper.createObjectNode();
            waterNode.put("type", currentWater.getType());
            waterNode.put("name", currentWater.getName());
            waterNode.put("mass", currentWater.getMass());
            cellNode.set("water", waterNode);
        }

        currentAir.calculateAirQuality();
        ObjectNode airNode = mapper.createObjectNode();
        airNode.put("type", currentAir.getDisplayType());
        airNode.put("name", currentAir.getName());
        airNode.put("mass", currentAir.getMass());
        airNode.put("humidity", currentAir.getHumidity());
        airNode.put("temperature", currentAir.getTemperature());
        airNode.put("oxygenLevel", currentAir.getOxygenLevel());
        airNode.put("airQuality", currentAir.getAirQuality());
        if (currentAir.getDisplayType().equals("DesertAir")) {
            airNode.put(currentAir.getSpecificNameCharacteristic(),
                    currentAir.getSpecificCharacteristic() == 1.0);
        } else {
            airNode.put(currentAir.getSpecificNameCharacteristic(),
                    currentAir.getSpecificCharacteristic());
        }
        cellNode.set("air", airNode);

        return cellNode;
    }

    /**
     * method for printing the map
     * @return array with every cell's information
     */
    public ArrayNode printMap() {
        return mapMatrix.getMapOutput();
    }

    /**
     * changes the weather
     * @param commandInput the command input
     * @param weatherChangeName name of the event
     * @return message for final output
     */
    public String changeWeather(final CommandInput commandInput, final String weatherChangeName) {
        boolean success = this.mapMatrix.startWeatherChange(commandInput, weatherChangeName);

        if (success) {
            return "The weather has changed.";
        }
        return "ERROR: The weather change does not affect the environment. Cannot perform action";
    }

    /**
     * function that implements robot movement
     * @return  message for final output
     */
    public String moveRobot() {
        Cell bestCell = mapMatrix.findBestCellForRobot(terraBot.getxCoord(), terraBot.getyCoord(),
                terraBot.getEnergy());

        if (bestCell == null) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        int score = (int) Math.round(bestCell.getScore());
        terraBot.setEnergy(terraBot.getEnergy() - score);
        terraBot.setxCoord(bestCell.getxCoordinate());
        terraBot.setyCoord(bestCell.getyCoordinate());

        return "The robot has successfully moved to position (" + bestCell.getxCoordinate() + ", "
                + bestCell.getyCoordinate() + ").";
    }
}
