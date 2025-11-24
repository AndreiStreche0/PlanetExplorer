package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class TemperateAir extends Air {
    private double pollenLevel;

    public TemperateAir(String name, double mass, double humidity, double temperature,
                        double oxygenLevel, double pollenLevel) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.TEMPERATE);
        this.pollenLevel = pollenLevel;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality += super.getHumidity() * 0.7;
        quality -= this.pollenLevel / 10;
        quality = (double) Math.round(quality * 100) / 100;
        super.setAirQuality(quality);
    }

    public double damageProbability() {
        this.calculateAirQuality();
        double toxicity = 100 * (1 - super.getAirQuality() / 84.0);
        return (double) Math.round(toxicity * 100.9) / 100.0;
    }

    public double getSpecificCharacteristic() {
        return pollenLevel;
    }

    public String getSpecificNameCharacteristic() {
        return "pollenLevel";
    }

    public double getPollenLevel() {
        return pollenLevel;
    }
}
