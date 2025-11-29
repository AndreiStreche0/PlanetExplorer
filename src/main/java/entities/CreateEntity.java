package entities;

import entities.airs.DesertAir;
import entities.airs.MountainAir;
import entities.airs.PolarAir;
import entities.airs.TemperateAir;
import entities.airs.TropicalAir;
import entities.animals.Carnivore;
import entities.animals.Detritivore;
import entities.animals.Herbivore;
import entities.animals.Omnivore;
import entities.animals.Parasite;
import entities.enums.AnimalHunger;
import entities.enums.PlantAge;
import entities.plants.Algae;
import entities.plants.Fern;
import entities.plants.FloweringPlant;
import entities.plants.GymnospermsPlant;
import entities.plants.Moss;
import entities.soils.DesertSoil;
import entities.soils.ForestSoil;
import entities.soils.GrasslandSoil;
import entities.soils.SwampSoil;
import entities.soils.TundraSoil;
import fileio.AirInput;
import fileio.AnimalInput;
import fileio.PlantInput;
import fileio.SoilInput;
import fileio.WaterInput;

public final class CreateEntity {
    private CreateEntity() {
    }

    /**
     * creates a new water cell
     * @param input data for water cell
     * @return the water entity
     */
    public static Water createWater(final WaterInput input) {
        return new Water(input.getName(), input.getMass(), input.getType(),
                input.getPurity(), input.getSalinity(), input.getTurbidity(),
                input.getContaminantIndex(), input.getpH(), input.isFrozen());
    }
    /**
     * creates a new soil cell
     * @param input data for soil cell
     * @return the soil entity
     */
    public static Soil createSoil(final SoilInput input) {
        return switch (input.getType()) {
            case "DesertSoil" -> new DesertSoil(input.getName(), input.getMass(),
                    input.getNitrogen(), input.getWaterRetention(),
                    input.getSoilpH(), input.getOrganicMatter(), input.getSalinity());
            case "ForestSoil" -> new ForestSoil(input.getName(), input.getMass(),
                    input.getNitrogen(), input.getWaterRetention(),
                    input.getSoilpH(), input.getOrganicMatter(), input.getLeafLitter());
            case "GrasslandSoil" -> new GrasslandSoil(input.getName(), input.getMass(),
                    input.getNitrogen(), input.getWaterRetention(),
                    input.getSoilpH(), input.getOrganicMatter(), input.getRootDensity());
            case "SwampSoil" -> new SwampSoil(input.getName(), input.getMass(),
                    input.getNitrogen(), input.getWaterRetention(),
                    input.getSoilpH(), input.getOrganicMatter(), input.getWaterLogging());
            case "TundraSoil" -> new TundraSoil(input.getName(), input.getMass(),
                    input.getNitrogen(), input.getWaterRetention(),
                    input.getSoilpH(), input.getOrganicMatter(),
                    input.getPermafrostDepth());
            default -> {
                System.out.println("Wrong soil type");
                yield null;
            }
        };
    }
    /**
     * creates a new air cell
     * @param input data for air cell
     * @return the air entity
     */
    public static Air createAir(final AirInput input) {
        return switch (input.getType()) {
            case "DesertAir" -> new DesertAir(input.getName(), input.getMass(),
                    input.getHumidity(), input.getTemperature(),
                    input.getOxygenLevel(), input.getDustParticles());
            case "MountainAir" -> new MountainAir(input.getName(), input.getMass(),
                    input.getHumidity(), input.getTemperature(),
                    input.getOxygenLevel(), input.getAltitude());
            case "PolarAir" -> new PolarAir(input.getName(), input.getMass(),
                    input.getHumidity(), input.getTemperature(),
                    input.getOxygenLevel(), input.getIceCrystalConcentration());
            case "TemperateAir" -> new TemperateAir(input.getName(), input.getMass(),
                    input.getHumidity(), input.getTemperature(),
                    input.getOxygenLevel(), input.getPollenLevel());
            case "TropicalAir" -> new TropicalAir(input.getName(), input.getMass(),
                    input.getHumidity(), input.getTemperature(),
                    input.getOxygenLevel(), input.getCo2Level());
            default -> {
                System.out.println("Wrong air type");
                yield null;
            }
        };
    }
    /**
     * creates a new animal cell
     * @param input data for animal cell
     * @return the animal entity
     */
    public static Animal createAnimal(final AnimalInput input) {
        return switch (input.getType()) {
            case "Carnivores" -> new Carnivore(input.getName(), input.getMass(),
                    AnimalHunger.HUNGRY);
            case "Detritivores" -> new Detritivore(input.getName(), input.getMass(),
                    AnimalHunger.HUNGRY);
            case "Herbivores" -> new Herbivore(input.getName(), input.getMass(),
                    AnimalHunger.HUNGRY);
            case "Omnivores" -> new Omnivore(input.getName(), input.getMass(),
                    AnimalHunger.HUNGRY);
            case "Parasites" -> new Parasite(input.getName(), input.getMass(),
                    AnimalHunger.HUNGRY);
            default -> {
                System.out.println("Wrong animal type");
                yield null;
            }
        };
    }
    /**
     * creates a new plant cell
     * @param input data for plant cell
     * @return the plant entity
     */
    public static Plant createPlant(final PlantInput input) {
        return switch (input.getType()) {
            case "Algae" -> new Algae(input.getName(), input.getMass(), PlantAge.YOUNG);
            case "Ferns" -> new Fern(input.getName(), input.getMass(), PlantAge.YOUNG);
            case "FloweringPlants" -> new FloweringPlant(input.getName(), input.getMass(),
                    PlantAge.YOUNG);
            case "GymnospermsPlants" -> new GymnospermsPlant(input.getName(), input.getMass(),
                    PlantAge.YOUNG);
            case "Mosses" -> new Moss(input.getName(), input.getMass(), PlantAge.YOUNG);
            default -> {
                System.out.println("Wrong plant type");
                yield null;
            }
        };
    }
}
