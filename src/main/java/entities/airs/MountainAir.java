package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class MountainAir extends Air {
    private double altitude;

    public MountainAir(String name, double mass, double humidity, double temperature,
                       double oxygenLevel, double altitude) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.MOUNTAIN);
        this.altitude = altitude;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel();
        quality -= this.altitude / 2000;
        quality *= 2;
        quality += super.getHumidity() * 0.6;
        quality = (double) Math.round(quality * 100) / 100;
        super.setAirQuality(quality);
    }

    public double getSpecificCharacteristic() {
        return altitude;
    }

    public String getSpecificNameCharacteristic() {
        return "altitude";
    }

    public double damageProbability() {
        this.calculateAirQuality();
        double toxicity = 100 * (1 - super.getAirQuality() / 78.0);
        return (double) Math.round(toxicity * 100.9) / 100.0;
    }

    public double getAltitude() {
        return altitude;
    }
}
