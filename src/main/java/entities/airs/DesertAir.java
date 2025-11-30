package entities.airs;

import entities.Air;
import entities.enums.AirType;

public final class DesertAir extends Air {
    private static final double QUALITY_OXYGEN_MULTIPLIER = 2.0;
    private static final double QUALITY_TEMP_MULTIPLIER = 0.3;
    private static final double QUALITY_DUST_DIVISOR = 5.0;
    private static final double MAX_VALUE = 100.0;
    private static final double TOXICITY_MAX_SCORE = 65.0;
    private static final double TOXICITY_THRESHOLD = 0.8;
    private static final int WEATHER_CHANGE_TIME = 2;
    private static final double ROUNDER = 100.0;
    private static final double TOXICITY_MULTIPLIER = 100.0;
    private final double dustParticles;
    private boolean desertStorm;

    public DesertAir(final String name, final double mass, final double humidity,
                     final double temperature, final double oxygenLevel,
                     final double dustParticles) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.DESERT);
        desertStorm = false;
        this.dustParticles = dustParticles;
    }

    /**
     * calculates the air quality specific of this air type
     */
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * QUALITY_OXYGEN_MULTIPLIER;
        quality -= super.getTemperature() * QUALITY_TEMP_MULTIPLIER;
        quality -= this.dustParticles / QUALITY_DUST_DIVISOR;
        quality = Math.max(0, Math.min(quality, MAX_VALUE));
        if (this.isDustStorm() && super.getTimeUntilWeatherChange() > 0) {
            quality -= super.getWeatherChangeAmount();
        }
        quality = Math.max(0, Math.min(quality, MAX_VALUE));
        quality = (double) Math.round(quality * ROUNDER) / ROUNDER;

        super.setAirQuality(quality);
    }

    /**
     * @return specific characteristic value for this air type
     */
    public double getSpecificCharacteristic() {
        if (this.isDustStorm() && super.getTimeUntilWeatherChange() >= 0) {
            return 1.0;
        }
        return 0.0;
    }
    /**
     * @return air toxicity of current air type
     */
    public double getAirToxicity() {
        double toxicity = TOXICITY_MULTIPLIER * (1 - super.getAirQuality() / TOXICITY_MAX_SCORE);
        toxicity = Math.max(0, Math.min(TOXICITY_MULTIPLIER, toxicity));
        return (double) Math.round(toxicity * ROUNDER) / ROUNDER;
    }

    /**
     * probability of the air to cause damage to the robot
     * @return probability
     */
    public double damageProbability() {
        this.calculateAirQuality();
        return this.getAirToxicity();
    }

    /**
     * @return if the air is toxic
     */
    public boolean isToxic() {
        return this.getAirToxicity() > TOXICITY_THRESHOLD * TOXICITY_MAX_SCORE;
    }
    /**
     * @return specific characteristic name for this air type
     */
    public String getSpecificNameCharacteristic() {
        return "desertStorm";
    }
    public double getDustParticles() {
        return this.dustParticles;
    }
    public boolean isDustStorm() {
        return desertStorm;
    }
    /**
     * starts weather change for specific air type
     * @param weatherChangeName weather event
     * @param weatherChangeAmount the effect it will have
     */
    public void startWeatherChange(final String weatherChangeName,
                                   final double weatherChangeAmount) {
        if (weatherChangeName.equals("desertStorm")) {
            this.desertStorm = true;
            super.setTimeUntilWeatherChange(WEATHER_CHANGE_TIME);
            super.setWeatherChangeAmount(weatherChangeAmount);
        }
    }
}
