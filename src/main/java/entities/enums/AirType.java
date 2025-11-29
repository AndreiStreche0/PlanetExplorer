package entities.enums;

public enum AirType {
    TROPICAL("TropicalAir"),
    POLAR("PolarAir"),
    TEMPERATE("TemperateAir"),
    DESERT("DesertAir"),
    MOUNTAIN("MountainAir");

    private final String displayType;

    AirType(String displayType) {
        this.displayType = displayType;
    }

    public String getDisplayType() {
        return displayType;
    }
}
