package entities.airs;

import entities.Air;
import entities.enums.AirType;

public final class MountainAir extends Air {
    private static final double QUALITY_OXYGEN_MULTIPLIER = 2.0;
    private static final double QUALITY_ALTITUDE_DIVISOR = 2000.0;
    private static final double QUALITY_HUMIDITY_MULTIPLIER = 0.6;
    private static final double MAX_VALUE = 100.0;
    private static final double TOXICITY_MAX_VALUE = 78.0;
    private static final double TOXICITY_THRESHOLD = 0.8;
    private static final int WEATHER_CHANGE_TIME = 2;
    private static final double ROUNDER = 100.0;
    private static final double TOXICITY_MULTIPLIER = 100.0;

    private final double altitude;
    private boolean peopleHiking;

    public MountainAir(final String name, final double mass, final double humidity,
                       final double temperature, final double oxygenLevel, final double altitude) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.MOUNTAIN);
        this.altitude = altitude;
        this.peopleHiking = false;
    }

    /**
     * calculates the air quality specific of this air type
     */
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel();
        quality -= this.altitude / QUALITY_ALTITUDE_DIVISOR;
        quality *= QUALITY_OXYGEN_MULTIPLIER;
        quality += super.getHumidity() * QUALITY_HUMIDITY_MULTIPLIER;
        quality = Math.max(0, Math.min(quality, MAX_VALUE));
        if (this.isPeopleHiking() && super.getTimeUntilWeatherChange() > 0) {
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
        return altitude;
    }
    /**
     * @return specific characteristic name for this air type
     */
    public String getSpecificNameCharacteristic() {
        return "altitude";
    }
    /**
     * @return air toxicity of current air type
     */
    public double getAirToxicity() {
        double toxicity = TOXICITY_MULTIPLIER * (1 - super.getAirQuality() / TOXICITY_MAX_VALUE);
        toxicity = Math.max(0, Math.min(TOXICITY_MULTIPLIER, toxicity));
        return (double) Math.round(toxicity * ROUNDER) / ROUNDER;
    }
    /**
     * @return if the air is toxic
     */
    public boolean isToxic() {
        return this.getAirToxicity() > TOXICITY_THRESHOLD * TOXICITY_MAX_VALUE;
    }

    /**
     * probability of the air to cause damage to the robot
     * @return probability
     */
    public double damageProbability() {
        this.calculateAirQuality();
        return this.getAirToxicity();
    }

    public double getAltitude() {
        return altitude;
    }

    public boolean isPeopleHiking() {
        return this.peopleHiking;
    }
    /**
     * starts weather change for specific air type
     * @param weatherChangeName weather event
     * @param weatherChangeAmount the effect it will have
     */
    public void startWeatherChange(final String weatherChangeName,
                                   final double weatherChangeAmount) {
        if (weatherChangeName.equals("peopleHiking")) {
            this.peopleHiking = true;
            super.setTimeUntilWeatherChange(WEATHER_CHANGE_TIME);
            super.setWeatherChangeAmount(weatherChangeAmount);
        }
    }
}
