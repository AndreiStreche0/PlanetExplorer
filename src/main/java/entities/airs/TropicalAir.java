package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class TropicalAir extends Air {
    private double co2Level;
    private double rainfall;
    private boolean rainfallEvent;

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

        if (this.isRainfall() && super.getTimeUntilWeatherChange() <= 0) {
            quality += this.rainfall * 0.3;
        }

        super.setAirQuality(quality);
    }

    public double damageProbability() {
        this.calculateAirQuality();
        double toxicity = Math.max(0, Math.min(100, super.getAirQuality()));
        toxicity = 100 * (1 - super.getAirQuality() / 82.0);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
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

    public boolean isRainfall() {
        return this.rainfallEvent;
    }

    public void startRainfall(double rainfall) {
        this.rainfallEvent = true;
        this.rainfall = rainfall;
        super.setTimeUntilWeatherChange(2);
    }

}
