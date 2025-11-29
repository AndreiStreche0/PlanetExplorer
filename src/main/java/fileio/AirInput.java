package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class AirInput {
    private String type;
    private String name;
    private double mass;
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double altitude;
    private double pollenLevel;
    private double co2Level;
    private double iceCrystalConcentration;
    private double dustParticles;
    private List<PairInput> sections;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getOxygenLevel() {
        return oxygenLevel;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getPollenLevel() {
        return pollenLevel;
    }

    public double getCo2Level() {
        return co2Level;
    }

    public double getIceCrystalConcentration() {
        return iceCrystalConcentration;
    }

    public double getDustParticles() {
        return dustParticles;
    }

    public List<PairInput> getSections() {
        return sections;
    }
}

