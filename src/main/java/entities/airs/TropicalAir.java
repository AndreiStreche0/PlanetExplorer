package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class TropicalAir extends Air {
    private final double co2Level;
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
        quality = Math.max(0, Math.min(quality, 100.0));
        if (this.isRainfall() && super.getTimeUntilWeatherChange() > 0) {
            quality += this.rainfall * 0.3;
        }
        quality = Math.max(0, Math.min(quality, 100.0));
        quality = (double) Math.round(quality * 100) / 100;

        super.setAirQuality(quality);
    }

    public double getAirToxicity() {
        double maxScore = 82.0;
        double toxicity = 100 * (1 - super.getAirQuality() / maxScore);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
    }

    public boolean isToxic() {
        double maxScore = 82.0;
        return this.getAirToxicity() > 0.8 * maxScore;
    }

    public double damageProbability() {
        this.calculateAirQuality();
        return this.getAirToxicity();
    }

    public double getSpecificCharacteristic() {
        return Math.round(co2Level * 100.0) / 100.0;
    }

    public String getSpecificNameCharacteristic() {
        return "co2Level";
    }

    public double getCo2Level() {
        return Math.round(co2Level * 100.0) / 100.0;
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
