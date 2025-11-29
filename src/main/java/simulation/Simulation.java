package simulation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.*;
import fileio.CommandInput;
import fileio.SimulationInput;
import planet.Cell;
import planet.Matrix;
import terrabot.TerraBot;

import java.sql.SQLOutput;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Simulation {
    private Matrix mapMatrix;
    private boolean running;
    private TerraBot terraBot;
    private int timeStamp;
    private final SimulationInput startSimulationData;
    private final ObjectMapper mapper = new ObjectMapper();
    public Simulation(SimulationInput startSimulationData) {
        this.startSimulationData = startSimulationData;
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public TerraBot getTerraBot() {
        return terraBot;
    }

    public void evolve() {
        this.timeStamp++;
        System.out.println("Timestamp of update is " + this.timeStamp);
        mapMatrix.evolve(this.timeStamp);
    }
    public void start() {
        this.running = true;
        this.timeStamp = 0;

        Scanner scanner = new Scanner(startSimulationData.territoryDim).useDelimiter("x");
        int height = scanner.nextInt();
        int width = scanner.nextInt();
        scanner.close();

        this.mapMatrix = new Matrix(width, height);
        this.terraBot = new TerraBot(startSimulationData.energyPoints, 0, 0);
        this.mapMatrix.setCells(startSimulationData.territorySectionParams);
    }

    public void stop() {
        this.running = false;
    }

    public ArrayNode printKnowledgeBase() {
        ObjectMapper mapper = new ObjectMapper();
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

    public String chargeRobot(int chargeTime) {
        this.terraBot.setChargeTime(chargeTime);
        this.terraBot.setCharging(true);
        return "Robot battery is charging.";
    }
    public String scanObject(String color, String smell, String sound) {
        int y = terraBot.getyCoord(), x = terraBot.getxCoord();
        int battery = terraBot.getEnergy();
        Cell currCell = mapMatrix.getCell(x, y);

        if (battery < 7) {
            return "ERROR: Not enough energy to perform action";
        }
        if (color.equals("none") && smell.equals("none") && sound.equals("none")
                && currCell.getCellWater() != null) {
            currCell.scanWater(this.timeStamp);
            terraBot.setEnergy(battery - 7);
            terraBot.scanItem(currCell.getCellWater().getName());
            return "The scanned object is water.";
        }
        if (color.compareTo("none") != 0 && smell.compareTo("none") != 0 && sound.equals("none")
                && currCell.getCellPlant() != null) {
            currCell.scanPlant(this.timeStamp);
            terraBot.setEnergy(battery - 7);
            terraBot.scanItem(currCell.getCellPlant().getName());
            return "The scanned object is a plant.";
        }
        if (color.compareTo("none") != 0 && smell.compareTo("none") != 0
                && sound.compareTo("none") != 0 && currCell.getCellAnimal() != null) {
            currCell.scanAnimal(this.timeStamp);
            terraBot.setEnergy(battery - 7);
            terraBot.scanItem(currCell.getCellAnimal().getName());
            return "The scanned object is an animal.";
        }

        return "ERROR: Object not found. Cannot perform action";
    }
    public String learnFact(String item, String fact) {
        if (terraBot.getEnergy() < 2) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        if (terraBot.hasScanned(item)) {
            terraBot.addFact(item, fact);
            terraBot.setEnergy(terraBot.getEnergy() - 2);
            return "The fact has been successfully saved in the database.";
        }
        return "ERROR: Subject not yet saved. Cannot perform action";
    }
    public String improveEnvironment(String improvementType, String itemName) {
        Set<String> itemSet = terraBot.getScannedItems();
        LinkedHashMap<String, List<String>> knowledgeBase = terraBot.getKnowledgeBase();
        if (!itemSet.contains(itemName)) {
            return "ERROR: Subject not yet saved. Cannot perform action";
        }
        if (terraBot.getEnergy() < 10) {
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
                        air.setOxygenLevel(air.getOxygenLevel() + 0.3);
                        terraBot.setEnergy(terraBot.getEnergy() - 10);
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
                        soil.setOrganicMatter(soil.getOrganicMatter() + 0.3);
                        terraBot.setEnergy(terraBot.getEnergy() - 10);
                        return "The soil was successfully fertilized using " + itemName;
                    }
                }
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
            case "increaseMoisture" -> {
                for (String fact : factList) {
                    if (fact.equalsIgnoreCase("Method to increaseMoisture")) {
                        Soil soil = currCell.getCellSoil();
                        soil.setWaterRetention(soil.getWaterRetention() + 0.2);
                        terraBot.setEnergy(terraBot.getEnergy() - 10);
                        return "The moisture was successfully increased using " + itemName;
                    }
                }
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
            case "increaseHumidity" -> {
                for (String fact : factList) {
                    if (fact.equalsIgnoreCase("Method to increase humidity.")) {
                        Air air = currCell.getCellAir();
                        air.setHumidity(air.getHumidity() + 0.2);
                        terraBot.setEnergy(terraBot.getEnergy() - 10);
                        return "The humidity was successfully increased using " + itemName;
                    }
                }
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
        }
        return "ERROR: Fact not yet saved. Cannot perform action";
    }
    public String getEnergyStatus() {
        return "TerraBot has " + terraBot.getEnergy() + " energy points left.";
    }

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
        soilNode.put(currentSoil.getSpecificNameCharacteristic(), currentSoil.getSpecificCharacteristic());
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
            airNode.put(currentAir.getSpecificNameCharacteristic(), currentAir.getSpecificCharacteristic() == 1.0);
        } else {
            airNode.put(currentAir.getSpecificNameCharacteristic(), currentAir.getSpecificCharacteristic());
        }
        cellNode.set("air", airNode);

        return cellNode;
    }

    public ArrayNode printMap() {
        return mapMatrix.getMapOutput();
    }

    public String changeWeather(CommandInput commandInput, String weatherChangeName) {
        boolean success = this.mapMatrix.startWeatherChange(commandInput, weatherChangeName);

        if (success) {
            return "The weather has changed.";
        }
        return "ERROR: The weather change does not affect the environment. Cannot perform action";
    }

    public String moveRobot() {
        Cell bestCell = mapMatrix.findBestCell(terraBot.getxCoord(), terraBot.getyCoord()
                , terraBot.getEnergy());

        if (bestCell == null) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        int score = bestCell.getScore();
        terraBot.setEnergy(terraBot.getEnergy() - score);
        terraBot.setxCoord(bestCell.getxCoordinate());
        terraBot.setyCoord(bestCell.getyCoordinate());

        return "The robot has successfully moved to position (" + bestCell.getxCoordinate() + ", "
                + bestCell.getyCoordinate() + ").";
    }
}
