package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class PolarAir extends Air {
    private double iceCrystalConcentration;

    public PolarAir(String name, double mass, double humidity, double temperature,
                    double oxygenLevel, double iceCrystalConcentration) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.POLAR);
        this.iceCrystalConcentration = iceCrystalConcentration;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality += (100 - Math.abs(super.getTemperature()));
        quality -= this.iceCrystalConcentration / 20;
        quality = (double) Math.round(quality * 100) / 100;
        super.setAirQuality(quality);
    }

    public double getSpecificCharacteristic() {
        return iceCrystalConcentration;
    }

    public String getSpecificNameCharacteristic() {
        return "iceCrystalConcentration";
    }

    public double damageProbability() {
        this.calculateAirQuality();
        double toxicity = 100 * (1 - super.getAirQuality() / 142.0);
        return (double) Math.round(toxicity * 100.9) / 100.0;
    }

    public double getIceCrystalConcentration() {
        return iceCrystalConcentration;
    }
}
