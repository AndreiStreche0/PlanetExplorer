package entities;

import entities.Entity;
import planet.Cell;
import entities.enums.AirType;

public abstract class Air extends Entity implements Evolve{
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private AirType airType;
    private double airQuality;
    private int timeUntilWeatherChange;
    public Air(String name, double mass, double humidity, double temperature, double oxygenLevel, AirType airType) {
        super(name, mass);
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
        this.airType = airType;
        this.airQuality = 0;
        this.timeUntilWeatherChange = Integer.MAX_VALUE;
    }

    public void setTimeUntilWeatherChange(int timeUntilWeatherChange) {
        this.timeUntilWeatherChange = timeUntilWeatherChange;
    }

    public String getDisplayType() {
        return airType.getDisplayType();
    }

    public abstract void calculateAirQuality();

    public double getOxygenLevel() {
        return oxygenLevel;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getAirQuality() {
        return airQuality;
    }

    public String getStringAirQuality() {
        this.calculateAirQuality();
        double airQuality = this.getAirQuality();
        if (airQuality < 40) {
            return "poor";
        }
        if (airQuality < 70) {
            return "moderate";
        }
        return "good";
    }
    public abstract double getSpecificCharacteristic();
    public abstract String getSpecificNameCharacteristic();

    public abstract double damageProbability();

    public double getHumidity() {
        return humidity;
    }

    public void setAirQuality(double airQuality) {
        this.airQuality = airQuality;
    }

    public int getTimeUntilWeatherChange() {
        return timeUntilWeatherChange;
    }

    public void evolve(Cell currentCell, int timeStamp) {
        if (currentCell.getCellPlant() != null && currentCell.getCellPlant().isScanned()) {
            this.oxygenLevel += currentCell.getCellPlant().calculateOxygenProduction();
        }

        if (currentCell.getCellAir() != null && currentCell.getCellAir().isScanned() && timeStamp % 2 == 0) {
            this.humidity += 1;
        }
        this.calculateAirQuality();
        timeUntilWeatherChange--;
    }
}
