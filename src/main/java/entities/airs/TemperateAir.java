package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class TemperateAir extends Air {
    private double pollenLevel;
    private String season;
    private boolean newSeason;

    public TemperateAir(String name, double mass, double humidity, double temperature,
                        double oxygenLevel, double pollenLevel) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.TEMPERATE);
        this.pollenLevel = pollenLevel;
        this.season = "";
        this.newSeason = false;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality += super.getHumidity() * 0.7;
        quality -= this.pollenLevel / 10;
        quality = (double) Math.round(quality * 100) / 100;

        if (this.isNewSeason() && super.getTimeUntilWeatherChange() <= 0) {
            quality -= (this.season.equalsIgnoreCase("Spring") ? 15 : 0);
        }

        super.setAirQuality(quality);
    }

    public double damageProbability() {
        this.calculateAirQuality();
        double toxicity = Math.max(0, Math.min(100, super.getAirQuality()));
        toxicity = 100 * (1 - super.getAirQuality() / 84.0);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
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

    public boolean isNewSeason() {
        return this.newSeason;
    }

    public void startNewSeason(String season) {
        this.newSeason = true;
        this.season = season;
        super.setTimeUntilWeatherChange(2);
    }

}
