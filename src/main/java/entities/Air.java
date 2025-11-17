package entities;

import entities.Entity;

public abstract class Air extends Entity{
    private double humidity;
    private double temperature;
    private double oxygenLevel;

    public Air(String name, double mass, double humidity, double temperature, double oxygenLevel) {
        super(name, mass);
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }
}
