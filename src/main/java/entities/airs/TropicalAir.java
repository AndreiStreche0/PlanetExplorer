package entities.airs;

import entities.Air;

public class TropicalAir extends Air {
    private double co2Level;

    public TropicalAir(String name, double mass, double humidity, double temperature,
                       double oxygenLevel, double co2Level) {
        super(name, mass, humidity, temperature, oxygenLevel);
        this.co2Level = co2Level;
    }
}
