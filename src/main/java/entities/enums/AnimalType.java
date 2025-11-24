package entities.enums;

public enum AnimalType {
    HERBIVORES("Herbivores"),
    CARNIVORES("Carnivores"),
    OMNIVORES("Omnivores"),
    DETRITIVORES("Detritivores"),
    PARASITES("Parasites");

    private String displayType;

    AnimalType(String displayType) {
        this.displayType = displayType;
    }

    public String getDisplayType() {
        return displayType;
    }
}
