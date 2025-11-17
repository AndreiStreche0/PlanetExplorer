package entities.airs;

import entities.Air;

public class TemperateAir extends Air {
    private double pollenLevel;

    public TemperateAir(String name, double mass, double humidity, double temperature,
                        double oxygenLevel, double pollenLevel) {
        super(name, mass, humidity, temperature, oxygenLevel);
        this.pollenLevel = pollenLevel;
    }
}
