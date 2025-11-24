package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class TropicalAir extends Air {
    private double co2Level;

    public TropicalAir(String name, double mass, double humidity, double temperature,
                       double oxygenLevel, double co2Level) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.TROPICAL);
        this.co2Level = co2Level;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality += super.getHumidity() / 2;
        quality -= this.co2Level / 100;
        quality = (double) Math.round(quality * 100) / 100;
        super.setAirQuality(quality);
    }

    public double damageProbability() {
        this.calculateAirQuality();
        double toxicity = 100 * (1 - super.getAirQuality() / 82.0);
        return (double) Math.round(toxicity * 100.9) / 100.0;
    }

    public double getSpecificCharacteristic() {
        return co2Level;
    }

    public String getSpecificNameCharacteristic() {
        return "co2Level";
    }

    public double getCo2Level() {
        return co2Level;
    }
}
