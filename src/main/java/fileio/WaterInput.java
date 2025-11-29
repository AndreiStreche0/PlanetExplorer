package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class WaterInput {
    private String type;
    private String name;
    private double mass;
    private double purity;
    private double salinity;
    private double turbidity;
    private double contaminantIndex;
    private double pH;
    private boolean isFrozen;
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

    public double getPurity() {
        return purity;
    }

    public double getSalinity() {
        return salinity;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public double getContaminantIndex() {
        return contaminantIndex;
    }

    /**
     * @return water ph
     */
    public double getpH() {
        return pH;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public List<PairInput> getSections() {
        return sections;
    }
}

