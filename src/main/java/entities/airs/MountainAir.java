package entities.airs;

import entities.Air;

public class MountainAir extends Air {
    private double altitude;

    public MountainAir(String name, double mass, double humidity, double temperature,
                       double oxygenLevel, double altitude) {
        super(name, mass, humidity, temperature, oxygenLevel);
        this.altitude = altitude;
    }
}
