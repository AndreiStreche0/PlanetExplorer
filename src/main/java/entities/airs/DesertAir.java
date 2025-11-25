package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class DesertAir extends Air {
    private double dustParticles;
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
        quality = (double) Math.round(quality * 100) / 100;

        if (this.isDustStorm() && super.getTimeUntilWeatherChange() <= 0) {
            quality -= (this.desertStorm ? 30 : 0);
        }

        super.setAirQuality(quality);
    }

    public double getSpecificCharacteristic() {
        if ((super.getTimeUntilWeatherChange() <= 0) && this.isDustStorm()) {
            return 1.0;
        }
        return 0.0;
    }

    public double damageProbability() {
        this.calculateAirQuality();
        double toxicity = Math.max(0, Math.min(100, super.getAirQuality()));
        toxicity = 100 * (1 - super.getAirQuality() / 65.0);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
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

    public void startDesertStorm(boolean desertStorm){
        this.desertStorm = desertStorm;
        super.setTimeUntilWeatherChange(2);
    }
}
