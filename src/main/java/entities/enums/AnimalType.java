package entities.enums;

public enum AnimalType {
    HERBIVORES("Herbivores"),
    CARNIVORES("Carnivores"),
    OMNIVORES("Omnivores"),
    DETRITIVORES("Detritivores"),
    PARASITES("Parasites");

    private final String displayType;

    AnimalType(final String displayType) {
        this.displayType = displayType;
    }

    public String getDisplayType() {
        return displayType;
    }
}
