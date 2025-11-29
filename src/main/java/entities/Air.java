package entities;

import planet.Cell;
import entities.enums.AirType;

/**
 * class that handles the air of each cell, its characteristics and evolving logic
 */
public abstract class Air extends Entity implements Evolve {
    private double humidity;
    private final double temperature;
    private double oxygenLevel;
    private final AirType airType;
    private double airQuality;
    private int timeUntilWeatherChange;
    private double weatherChangeAmount;

    private static final double POOR_QUALITY_THRESHOLD = 40;
    private static final double MODERATE_QUALITY_THRESHOLD = 70;
    private static final double ROUNDING_FACTOR = 100.0;
    private static final int WATER_SCAN_MODULO = 2;
    private static final double HUMIDITY_INCREASE = 0.1;

    public Air(final String name, final double mass, final double humidity,
               final double temperature, final double oxygenLevel, final AirType airType) {
        super(name, mass);
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
        this.airType = airType;
        this.airQuality = 0;
        this.timeUntilWeatherChange = -1;
    }

    /**
     * @return weatherChage
     */
    public double getWeatherChangeAmount() {
        return weatherChangeAmount;
    }

    /**
     * @param weatherChangeAmount sets it
     */
    public void setWeatherChangeAmount(final double weatherChangeAmount) {
        this.weatherChangeAmount = weatherChangeAmount;
    }
    /**
     * @param - sets it
     */
    public void setOxygenLevel(final double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }
    /**
     * @param - sets it
     */
    public void setHumidity(final double humidity) {
        this.humidity = humidity;
    }
    /**
     * @param - sets it
     */
    public void setTimeUntilWeatherChange(final int timeUntilWeatherChange) {
        this.timeUntilWeatherChange = timeUntilWeatherChange;
    }

    /**
     * @return display type
     */
    public String getDisplayType() {
        return airType.getDisplayType();
    }

    /**
     * abstract method to calculate the air quality
     */
    public abstract void calculateAirQuality();

    /**
     * @return oxygen level
     */
    public double getOxygenLevel() {
        return oxygenLevel;
    }

    /**
     * @return name
     */
    public String getName() {
        return super.getName();
    }

    /**
     * @return temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * @return air qualiy
     */
    public double getAirQuality() {
        return airQuality;
    }

    /**
     * abstract class that gets the air toxicity of each air type
     * @return air toxicity
     */
    public abstract double getAirToxicity();

    /**
     * checks if the air is toxic
     * @return if the air is toxic
     */
    public abstract boolean isToxic();

    /**
     * checks the quality of air
     * @return a string describing the air quality
     */
    public String getStringAirQuality() {
        this.calculateAirQuality();
        double currentAirQuality = this.getAirQuality();
        if (currentAirQuality < POOR_QUALITY_THRESHOLD) {
            return "poor";
        }
        if (currentAirQuality < MODERATE_QUALITY_THRESHOLD) {
            return "moderate";
        }
        return "good";
    }

    /**
     * abstract method for final output
     * @return specific characteristic of air type
     */
    public abstract double getSpecificCharacteristic();
    /**
     * abstract method for final output
     * @return the name of specific characteristic of air type
     */
    public abstract String getSpecificNameCharacteristic();

    /**
     * abstract method that calculates damage probability if the plant encounters terraBot
     * @return probability
     */
    public abstract double damageProbability();

    /**
     * @return air humidity
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * @param airQuality - sets it
     */
    public void setAirQuality(final double airQuality) {
        this.airQuality = airQuality;
    }

    /**
     * @return time until the next weather change
     */
    public int getTimeUntilWeatherChange() {
        return timeUntilWeatherChange;
    }

    /**
     * abstract method that starts weather event
     * @param weatherChangeName name of weather event
     * @param weatherChangeAmount the change it will have on the air quality
     */
    public abstract void startWeatherChange(String weatherChangeName, double weatherChangeAmount);
    /**
     * method for evolving of air based on other entities from the same cell
     * @param currentCell this air cell
     * @param timeStamp time of current simulation
     */
    public void evolve(final Cell currentCell, final int timeStamp) {
        if (currentCell.getCellPlant() != null && currentCell.getCellPlant().isScanned()) {
            this.oxygenLevel += currentCell.getCellPlant().calculateOxygenProduction();
            this.oxygenLevel = Math.round(this.oxygenLevel * ROUNDING_FACTOR) / ROUNDING_FACTOR;
        }

        if (currentCell.getCellWater() != null && currentCell.getCellWater().isScanned()
                && (timeStamp - currentCell.getCellWater().getScanTimeStamp())
                % WATER_SCAN_MODULO == 0) {
            this.humidity += HUMIDITY_INCREASE;
            this.humidity = (double) Math.round(this.humidity * ROUNDING_FACTOR) / ROUNDING_FACTOR;

        }
        this.calculateAirQuality();
        timeUntilWeatherChange--;
    }
}
