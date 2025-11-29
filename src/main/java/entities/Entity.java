package entities;

/**
 * abstract class for each entity
 */
public abstract class Entity {
    private String name;
    private double mass;
    private boolean isScanned;
    public Entity(final String name, final double mass) {
        this.name = name;
        this.mass = mass;
        this.isScanned = false;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return mass
     */
    public double getMass() {
        return mass;
    }

    /**
     * abstract method for entities
     * @return if the entity is scanned
     */
    public boolean isScanned() {
        return isScanned;
    }

    /**
     * @param name - sets it
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @param mass - sets it
     */
    public void setMass(final double mass) {
        this.mass = mass;
    }
    /**
     * @param scanned - sets it
     */
    public void setScanned(final boolean scanned) {
        isScanned = scanned;
    }
}
