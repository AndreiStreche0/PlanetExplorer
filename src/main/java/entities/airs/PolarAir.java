package entities.airs;

import entities.Air;
import entities.enums.AirType;

public final class PolarAir extends Air {
    private static final double QUALITY_OXYGEN_MULTIPLIER = 2.0;
    private static final double QUALITY_TEMP_MAX = 100.0;
    private static final double QUALITY_CRYSTAL_DIVISOR = 20.0;
    private static final double MAX_VALUE = 100.0;
    private static final double TOXICITY_MAX_VALUE = 142.0;
    private static final double TOXICITY_THRESHOLD = 0.8;
    private static final int WEATHER_CHANGE_TIME = 2;
    private static final double ROUNDER = 100.0;
    private static final double TOXICITY_MULTIPLIER = 100.0;

    private final double iceCrystalConcentration;
    private boolean polarStorm;

    public PolarAir(final String name, final double mass, final double humidity,
                    final double temperature, final double oxygenLevel,
                    final double iceCrystalConcentration) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.POLAR);
        this.iceCrystalConcentration = iceCrystalConcentration;
        this.polarStorm = false;
    }
     /**
     * calculates the air quality specific of this air type
     */
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * QUALITY_OXYGEN_MULTIPLIER;
        quality += (QUALITY_TEMP_MAX - Math.abs(super.getTemperature()));
        quality -= this.iceCrystalConcentration / QUALITY_CRYSTAL_DIVISOR;

        if (this.isPolarStorm() && super.getTimeUntilWeatherChange() > 0) {
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
        return iceCrystalConcentration;
    }
    /**
     * @return specific characteristic name for this air type
     */
    public String getSpecificNameCharacteristic() {
        return "iceCrystalConcentration";
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

    public double getIceCrystalConcentration() {
        return iceCrystalConcentration;
    }

    public boolean isPolarStorm() {
        return this.polarStorm;
    }
    /**
     * starts weather change for specific air type
     * @param weatherChangeName weather event
     * @param weatherChangeAmount the effect it will have
     */
    public void startWeatherChange(final String weatherChangeName,
                                   final double weatherChangeAmount) {
        if (weatherChangeName.equals("polarStorm")) {
            this.polarStorm = true;
            super.setTimeUntilWeatherChange(WEATHER_CHANGE_TIME);
            super.setWeatherChangeAmount(weatherChangeAmount);
        }
    }
}
