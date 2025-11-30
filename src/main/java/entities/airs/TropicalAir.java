package entities.airs;

import entities.Air;
import entities.enums.AirType;

public final class TropicalAir extends Air {
    private static final double QUALITY_OXYGEN_MULTIPLIER = 2.0;
    private static final double QUALITY_HUMIDITY_DIVISOR = 2.0;
    private static final double QUALITY_CO2_DIVISOR = 100.0;
    private static final double MAX_VALUE = 100.0;
    private static final double TOXICITY_MAX_SCORE = 82.0;
    private static final double TOXICITY_THRESHOLD = 0.8;
    private static final int WEATHER_CHANGE_TIME = 2;
    private static final double ROUNDER = 100.0;
    private static final double TOXICITY_MULTIPLIER = 100.0;

    private final double co2Level;
    private boolean rainfallEvent;

    public TropicalAir(final String name, final double mass, final double humidity,
                       final double temperature, final double oxygenLevel, final double co2Level) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.TROPICAL);
        this.co2Level = co2Level;
    }

    /**
     * calculates the air quality specific of this air type
     */
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * QUALITY_OXYGEN_MULTIPLIER;
        quality += super.getHumidity() / QUALITY_HUMIDITY_DIVISOR;
        quality -= this.co2Level / QUALITY_CO2_DIVISOR;
        quality = Math.max(0, Math.min(quality, MAX_VALUE));
        if (this.isRainfall() && super.getTimeUntilWeatherChange() > 0) {
            quality += super.getWeatherChangeAmount();
        }
        quality = Math.max(0, Math.min(quality, MAX_VALUE));
        quality = (double) Math.round(quality * ROUNDER) / ROUNDER;

        super.setAirQuality(quality);
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
     * @return if the air is toxic
     */
    public boolean isToxic() {
        return this.getAirToxicity() > TOXICITY_THRESHOLD * TOXICITY_MAX_SCORE;
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
        return Math.round(co2Level * ROUNDER) / ROUNDER;
    }
    /**
     * @return specific characteristic name for this air type
     */
    public String getSpecificNameCharacteristic() {
        return "co2Level";
    }

    public double getCo2Level() {
        return Math.round(co2Level * ROUNDER) / ROUNDER;
    }

    public boolean isRainfall() {
        return this.rainfallEvent;
    }
    /**
     * starts weather change for specific air type
     * @param weatherChangeName weather event
     * @param weatherChangeAmount the effect it will have
     */
    public void startWeatherChange(final String weatherChangeName,
                                   final double weatherChangeAmount) {
        if (weatherChangeName.equals("rainfall")) {
            this.rainfallEvent = true;
            super.setTimeUntilWeatherChange(WEATHER_CHANGE_TIME);
            super.setWeatherChangeAmount(weatherChangeAmount);
        }
    }
}
