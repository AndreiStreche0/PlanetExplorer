package entities;

import planet.Cell;
import entities.enums.AirType;

public abstract class Air extends Entity implements Evolve{
    private double humidity;
    private final double temperature;
    private double oxygenLevel;
    private final AirType airType;
    private double airQuality;
    private int timeUntilWeatherChange;
    public Air(String name, double mass, double humidity, double temperature, double oxygenLevel, AirType airType) {
        super(name, mass);
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
        this.airType = airType;
        this.airQuality = 0;
        this.timeUntilWeatherChange = -1;
    }

    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
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
    public String getName () {return super.getName();}
    public double getTemperature() {
        return temperature;
    }

    public double getAirQuality() {
        return airQuality;
    }

    public abstract double getAirToxicity();

    public abstract boolean isToxic();

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
            this.oxygenLevel = Math.round(this.oxygenLevel * 100.0) / 100.0;
        }

        if (currentCell.getCellWater() != null && currentCell.getCellWater().isScanned()
                && (timeStamp - currentCell.getCellWater().getScanTimeStamp()) % 2 == 0) {
            this.humidity += 0.1;
            this.humidity = (double) Math.round(this.humidity * 100.0) / 100.0;

        }
        this.calculateAirQuality();
        timeUntilWeatherChange--;
    }
}
