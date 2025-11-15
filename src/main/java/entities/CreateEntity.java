package entities;

import entities.soils.DesertSoil;
import fileio.SoilInput;
import fileio.WaterInput;

public final class CreateEntity {
    public static Water createWater(WaterInput input) {
        return new Water(input.name, input.mass, input.purity,
                input.salinity, input.turbidity, input.contaminantIndex,
                input.pH, input.isFrozen);
    }
    public static Soil createSoil(SoilInput input) {
        switch (input.type) {
            case "DesertSoil":
                return new DesertSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.salinity);
            case "ForestSoil":
                return new DesertSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.leafLitter);
            case "GrasslandSoil":
                return new DesertSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.rootDensity);
            case "SwampSoil":
                return new DesertSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.waterLogging);
            case "TundraSoil":
                return new DesertSoil(input.name, input.mass, input.nitrogen, input.waterRetention,
                        input.soilpH, input.organicMatter, input.permafrostDepth);
            default:
                System.out.println("Wrong soil type");
                return null;
        }
    }
}
