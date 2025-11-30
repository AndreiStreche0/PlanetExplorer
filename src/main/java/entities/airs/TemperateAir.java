package entities.airs;

import entities.Air;
import entities.enums.AirType;

public final class TemperateAir extends Air {
    private static final double QUALITY_OXYGEN_MULTIPLIER = 2.0;
    private static final double QUALITY_HUMIDITY_MULTIPLIER = 0.7;
    private static final double QUALITY_POLLEN_DIVIDER = 10.0;
    private static final double MAX_VALUE = 100.0;
    private static final double TOXICITY_MAX = 84.0;
    private static final double TOXICITY_THRESHOLD = 0.8;
    private static final int WEATHER_CHANGE_TIME = 2;
    private static final double ROUNDER = 100.0;
    private static final double TOXICITY_MULTIPLIER = 100.0;

    private final double pollenLevel;
    private boolean newSeason;

    public TemperateAir(final String name, final double mass, final double humidity,
                        final double temperature, final double oxygenLevel,
                        final double pollenLevel) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.TEMPERATE);
        this.pollenLevel = pollenLevel;
        this.newSeason = false;
    }

    /**
     * calculates the air quality specific of this air type
     */
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * QUALITY_OXYGEN_MULTIPLIER;
        quality += super.getHumidity() * QUALITY_HUMIDITY_MULTIPLIER;
        quality -= this.pollenLevel / QUALITY_POLLEN_DIVIDER;
        quality = Math.max(0, Math.min(quality, MAX_VALUE));
        if (this.isNewSeason() && super.getTimeUntilWeatherChange() >= 0) {
            quality -= super.getWeatherChangeAmount();
        }
        quality = Math.max(0, Math.min(quality, MAX_VALUE));
        quality = (double) Math.round(quality * ROUNDER) / ROUNDER;

        super.setAirQuality(quality);
    }
    /**
     * @return air toxicity of current air type
     */
    public double getAirToxicity() {
        double toxicity = TOXICITY_MULTIPLIER * (1 - super.getAirQuality() / TOXICITY_MAX);
        toxicity = Math.max(0, Math.min(TOXICITY_MULTIPLIER, toxicity));
        return (double) Math.round(toxicity * ROUNDER) / ROUNDER;
    }
    /**
     * @return if the air is toxic
     */
    public boolean isToxic() {
        return this.getAirToxicity() > TOXICITY_THRESHOLD * TOXICITY_MAX;
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
     * @return specific characteristic for air type
     */
    public double getSpecificCharacteristic() {
        return pollenLevel;
    }
    /**
     * @return specific characteristic name for this air type
     */
    public String getSpecificNameCharacteristic() {
        return "pollenLevel";
    }

    public double getPollenLevel() {
        return pollenLevel;
    }

    public boolean isNewSeason() {
        return this.newSeason;
    }
    /**
     * starts weather change for specific air type
     * @param weatherChangeName weather event
     * @param weatherChangeAmount the effect it will have
     */
    public void startWeatherChange(final String weatherChangeName,
                                   final double weatherChangeAmount) {
        if (weatherChangeName.equals("newSeason")) {
            this.newSeason = true;
            super.setTimeUntilWeatherChange(WEATHER_CHANGE_TIME);
            super.setWeatherChangeAmount(weatherChangeAmount);
        }
    }
}
