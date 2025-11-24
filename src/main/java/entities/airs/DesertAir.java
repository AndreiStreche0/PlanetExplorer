package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class DesertAir extends Air {
    private double dustParticles;

    public DesertAir(String name, double mass, double humidity, double temperature,
                     double oxygenLevel, double dustParticles) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.DESERT);
        this.dustParticles = dustParticles;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality -= super.getTemperature() * 0.3;
        quality -= this.dustParticles / 5;
        quality = (double) Math.round(quality * 100) / 100;
        super.setAirQuality(quality);
    }

    public double getSpecificCharacteristic() {
        return dustParticles;
    }

    public double damageProbability() {
        this.calculateAirQuality();
        double toxicity = 100 * (1 - super.getAirQuality() / 65.0);
        return (double) Math.round(toxicity * 100.9) / 100.0;
    }

    public String getSpecificNameCharacteristic() {
        return "dustParticles";
    }

    public double getDustParticles() {
        return dustParticles;
    }
}
