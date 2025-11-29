package entities.airs;

import entities.Air;
import entities.enums.AirType;

public final class DesertAir extends Air {
    private final double dustParticles;
    private boolean desertStorm;

    public DesertAir(String name, double mass, double humidity, double temperature,
                     double oxygenLevel, double dustParticles) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.DESERT);
        desertStorm = false;
        this.dustParticles = dustParticles;
    }

    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality -= super.getTemperature() * 0.3;
        quality -= this.dustParticles / 5;
        quality = Math.max(0, Math.min(quality, 100.0));
        if (this.isDustStorm() && super.getTimeUntilWeatherChange() > 0) {
            quality -= super.getWeatherChangeAmount();
        }
        quality = Math.max(0, Math.min(quality, 100.0));
        quality = (double) Math.round(quality * 100) / 100;

        super.setAirQuality(quality);
    }

    public double getSpecificCharacteristic() {
        if (this.isDustStorm() && super.getTimeUntilWeatherChange() >= 0) {
            return 1.0;
        }
        return 0.0;
    }

    public double getAirToxicity() {
        double maxScore = 65.0;
        double toxicity = 100 * (1 - super.getAirQuality() / maxScore);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
    }

    public double damageProbability() {
        this.calculateAirQuality();
        return this.getAirToxicity();
    }

    public boolean isToxic() {
        double maxScore = 65.0;
        return this.getAirToxicity() > 0.8 * maxScore;
    }

    public String getSpecificNameCharacteristic() {
        return "desertStorm";
    }

    public double getDustParticles() {
        return this.dustParticles;
    }

    public boolean isDustStorm() {
        return desertStorm;
    }

    public void startWeatherChange(String weatherChangeName, double weatherChangeAmount) {
        if (weatherChangeName.equals("desertStorm")) {
            this.desertStorm = true;
            super.setTimeUntilWeatherChange(2);
            super.setWeatherChangeAmount(weatherChangeAmount);
        }
    }
}
