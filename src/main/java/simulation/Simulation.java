package simulation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.*;
import fileio.SimulationInput;
import planet.Cell;
import planet.Matrix;
import terrabot.TerraBot;

import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Simulation {
    private Matrix mapMatrix;
    private boolean running;
    private TerraBot terraBot;
    private int timeStamp;
    private SimulationInput startSimulationData;
    private ObjectMapper mapper = new ObjectMapper();
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

    public void evolve() {
        this.timeStamp++;
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

    public String chargeRobot() {
        return "";
    }
    public String scanObject() {
        return "";
    }
    public String learnFact() {
        return "";
    }
    public String improveEnvironment() {
        return "";
    }
    public String getEnergyStatus() {
        return "";
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
            waterNode.put("purity", currentWater.getPurity());
            waterNode.put("salinity", currentWater.getSalinity());
            waterNode.put("turbidity", currentWater.getTurbidity());
            waterNode.put("contaminantIndex", currentWater.getContaminantIndex());
            waterNode.put("pH", currentWater.getpH());
            waterNode.put("isFrozen", currentWater.isFrozen());
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
        airNode.put(currentAir.getSpecificNameCharacteristic(), currentAir.getSpecificCharacteristic());
        cellNode.set("air", airNode);

        return cellNode;
    }

    public ArrayNode printMap() {
        return mapMatrix.getMapOutput();
    }

    public String moveRobot() {
        if (terraBot.isCharging() && timeStamp < terraBot.getChargeTimeStamp()) {
            return "ERROR: Robot still charging. Cannot perform action";
        }
        Cell bestCell = mapMatrix.findBestCell(terraBot.getxCoord(), terraBot.getyCoord()
                , terraBot.getEnergy());

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
