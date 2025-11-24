package entities.enums;

public enum SoilType {
    FOREST("ForestSoil"),
    SWAMP("SwampSoil"),
    DESERT("DesertSoil"),
    GRASSLAND("GrasslandSoil"),
    TUNDRA("TundraSoil");

    private String displayType;

    SoilType(String displayType) {
        this.displayType = displayType;
    }

    public String getDisplayType() {
        return displayType;
    }
}
