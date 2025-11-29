package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class MountainAir extends Air {
    private final double altitude;
    private int numberOfHikers;
    private boolean peopleHiking;

    public MountainAir(String name, double mass, double humidity, double temperature,
                       double oxygenLevel, double altitude) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.MOUNTAIN);
        this.altitude = altitude;
        this.numberOfHikers = 0;
        this.peopleHiking = false;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel();
        quality -= this.altitude / 2000;
        quality *= 2;
        quality += super.getHumidity() * 0.6;
        quality = Math.max(0, Math.min(quality, 100.0));
        if (this.isPeopleHiking() && super.getTimeUntilWeatherChange() > 0) {
            quality -= this.numberOfHikers / 10.0;
        }
        quality = Math.max(0, Math.min(quality, 100.0));
        quality = (double) Math.round(quality * 100) / 100;

        super.setAirQuality(quality);
    }

    public double getSpecificCharacteristic() {
        return altitude;
    }

    public String getSpecificNameCharacteristic() {
        return "altitude";
    }

    public double getAirToxicity() {
        double maxScore = 78.0;
        double toxicity = 100 * (1 - super.getAirQuality() / maxScore);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
    }

    public boolean isToxic() {
        double maxScore = 78.0;
        return this.getAirToxicity() > 0.8 * maxScore;
    }

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

    public void startPeopleHiking(int numberOfHikers) {
        this.peopleHiking = true;
        this.numberOfHikers = numberOfHikers;
        super.setTimeUntilWeatherChange(2);
    }
}
