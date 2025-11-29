package entities.airs;

import entities.Air;
import entities.enums.AirType;

public class PolarAir extends Air {
    private final double iceCrystalConcentration;
    private double windSpeed;
    private boolean polarStorm;

    public PolarAir(String name, double mass, double humidity, double temperature,
                    double oxygenLevel, double iceCrystalConcentration) {
        super(name, mass, humidity, temperature, oxygenLevel, AirType.POLAR);
        this.iceCrystalConcentration = iceCrystalConcentration;
        this.windSpeed = 0;
        this.polarStorm = false;
    }
    public void calculateAirQuality() {
        double quality = super.getOxygenLevel() * 2;
        quality += (100 - Math.abs(super.getTemperature()));
        quality -= this.iceCrystalConcentration / 20;

        if (this.isPolarStorm() && super.getTimeUntilWeatherChange() > 0) {
            quality -= this.windSpeed / 5.0;
        }
        quality = Math.max(0, Math.min(quality, 100.0));
        quality = (double) Math.round(quality * 100) / 100;

        super.setAirQuality(quality);
    }

    public double getSpecificCharacteristic() {
        return iceCrystalConcentration;
    }

    public String getSpecificNameCharacteristic() {
        return "iceCrystalConcentration";
    }

    public double getAirToxicity() {
        double maxScore = 142.0;
        double toxicity = 100 * (1 - super.getAirQuality() / maxScore);
        toxicity = Math.max(0, Math.min(100, toxicity));
        return (double) Math.round(toxicity * 100.0) / 100.0;
    }

    public boolean isToxic() {
        double maxScore = 142.0;
        return this.getAirToxicity() > 0.8 * maxScore;
    }

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

    public void startPolarStorm(double windSpeed) {
        this.polarStorm = true;
        this.windSpeed = windSpeed;
        super.setTimeUntilWeatherChange(2);
    }
}
