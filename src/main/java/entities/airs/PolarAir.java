package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class PolarAir extends Air {
    private double iceCrystalConcentration;
    private double windSpeed;
    private boolean polarStorm;

    public PolarAir(String name, double mass, double humidity, double temperature,
                    double oxygenLevel, double iceCrystalConcentration) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.POLAR);
        this.iceCrystalConcentration = iceCrystalConcentration;
        this.windSpeed = 0;
        this.polarStorm = false;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality += (100 - Math.abs(super.getTemperature()));
        quality -= this.iceCrystalConcentration / 20;
        quality = (double) Math.round(quality * 100) / 100;

        if (this.isPolarStorm() && super.getTimeUntilWeatherChange() <= 0) {
            quality -= this.windSpeed / 5.0;
        }
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
        double toxicity = Math.max(0, Math.min(100, super.getAirQuality()));
        toxicity = 100 * (1 - super.getAirQuality() / 142.0);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
    }

    public double getIceCrystalConcentration() {
        return iceCrystalConcentration;
    }

    public boolean isPolarStorm() {
        return this.polarStorm;
    }

    public void startPolarStorm(double windSpeed) {
        this.polarStorm = true;
        this.windSpeed = windSpeed;
        super.setTimeUntilWeatherChange(2);
    }
}
