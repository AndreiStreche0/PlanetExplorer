package entities;

public abstract class Entity {
    private String name;
    private double mass;
    private boolean isScanned;
    public Entity(String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.isScanned = false;
    }

    public String getName() {
        return name;
    }
    public double getMass() {
        return mass;
    }
    public boolean isScanned() {
        return isScanned;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }
}
