package entities.airs;

import entities.Air;

public class DesertAir extends Air {
    private double dustParticles;

    public DesertAir(String name, double mass, double humidity, double temperature,
                     double oxygenLevel, double dustParticles) {
        super(name, mass, humidity, temperature, oxygenLevel);
        this.dustParticles = dustParticles;
    }
}
