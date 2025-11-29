package entities.airs;

import entities.Air;
import entities.enums.AirType;

public final class TemperateAir extends Air {
    private final double pollenLevel;
    private boolean newSeason;

    public TemperateAir(String name, double mass, double humidity, double temperature,
                        double oxygenLevel, double pollenLevel) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.TEMPERATE);
        this.pollenLevel = pollenLevel;
        this.newSeason = false;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality += super.getHumidity() * 0.7;
        quality -= this.pollenLevel / 10;
        quality = Math.max(0, Math.min(quality, 100.0));
        if (this.isNewSeason() && super.getTimeUntilWeatherChange() >= 0) {
            quality -= super.getWeatherChangeAmount();
        }
        quality = Math.max(0, Math.min(quality, 100.0));
        quality = (double) Math.round(quality * 100) / 100;

        super.setAirQuality(quality);
    }

    public double getAirToxicity() {
        double maxScore = 84.0;
        double toxicity = 100 * (1 - super.getAirQuality() / maxScore);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
    }

    public boolean isToxic() {
        double maxScore = 84.0;
        return this.getAirToxicity() > 0.8 * maxScore;
    }

    public double damageProbability() {
        this.calculateAirQuality();
        return this.getAirToxicity();
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

    public void startWeatherChange(String weatherChangeName, double weatherChangeAmount) {
        if (weatherChangeName.equals("newSeason")) {
            this.newSeason = true;
            super.setTimeUntilWeatherChange(2);
            super.setWeatherChangeAmount(weatherChangeAmount);
        }
    }
}
