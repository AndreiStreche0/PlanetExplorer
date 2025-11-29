package entities.enums;

public enum PlantType {
    FLOWERING("FloweringPlants"),
    GYMNOSPERMS("GymnospermsPlants"),
    FERNS("Ferns"),
    MOSSES("Mosses"),
    ALGAE("Algae");

    private final String displayType;

    PlantType(String displayType) {
        this.displayType = displayType;
    }

    public String getDisplayType() {
        return displayType;
    }
}
