package entities;

import entities.airs.*;
import entities.animals.*;
import entities.enums.AnimalHunger;
import entities.enums.PlantAge;
import entities.plants.*;
import entities.soils.*;
import fileio.*;

public final class CreateEntity {
    public static Water createWater(WaterInput input) {
        return new Water(input.name, input.mass, input.type, input.purity,
                input.salinity, input.turbidity, input.contaminantIndex,
                input.pH, input.isFrozen);
    }
    public static Soil createSoil(SoilInput input) {
        switch (input.type) {
            case "DesertSoil":
                return new DesertSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.salinity);
            case "ForestSoil":
                return new ForestSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.leafLitter);
            case "GrasslandSoil":
                return new GrasslandSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.rootDensity);
            case "SwampSoil":
                return new SwampSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.waterLogging);
            case "TundraSoil":
                return new TundraSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.permafrostDepth);
            default:
                System.out.println("Wrong soil type");
                return null;
        }
    }
    public static Air createAir(AirInput input) {
        switch (input.type) {
            case "DesertAir":
                return new DesertAir(input.name, input.mass, input.humidity, input.temperature,
                        input.oxygenLevel, input.dustParticles);
            case "MountainAir":
                return new MountainAir(input.name, input.mass, input.humidity, input.temperature,
                        input.oxygenLevel, input.altitude);
            case "PolarAir":
                return new PolarAir(input.name, input.mass, input.humidity, input.temperature,
                        input.oxygenLevel, input.iceCrystalConcentration);
            case "TemperateAir":
                return new TemperateAir(input.name, input.mass, input.humidity, input.temperature,
                        input.oxygenLevel, input.pollenLevel);
            case "TropicalAir":
                return new TropicalAir(input.name, input.mass, input.humidity, input.temperature,
                        input.oxygenLevel, input.co2Level);
            default:
                System.out.println("Wrong air type");
                return null;
        }
    }
    public static Animal createAnimal(AnimalInput input) {
        switch(input.type) {
            case "Carnivores":
                return new Carnivore(input.name, input.mass, AnimalHunger.HUNGRY);
            case "Detritivores":
                return new Detritivore(input.name, input.mass, AnimalHunger.HUNGRY);
            case "Herbivores":
                return new Herbivore(input.name, input.mass, AnimalHunger.HUNGRY);
            case "Omnivores":
                return new Omnivore(input.name, input.mass, AnimalHunger.HUNGRY);
            case "Parasites":
                return new Parasite(input.name, input.mass, AnimalHunger.HUNGRY);
            default:
                System.out.println("Wrong animal type");
                return null;
        }
    }
    public static Plant createPlant(PlantInput input) {
        switch(input.type) {
            case "Algae":
                return new Algae(input.name, input.mass, PlantAge.YOUNG);
            case "Ferns":
                return new Fern(input.name, input.mass, PlantAge.YOUNG);
            case "FloweringPlants":
                return new FloweringPlant(input.name, input.mass, PlantAge.YOUNG);
            case "GymnospermsPlants":
                return new GymnospermsPlant(input.name, input.mass, PlantAge.YOUNG);
            case "Mosses":
                return new Moss(input.name, input.mass, PlantAge.YOUNG);
            default:
                System.out.println("Wrong plant type");
                return null;
        }
    }

}
