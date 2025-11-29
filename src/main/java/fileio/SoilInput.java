package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class SoilInput {
    private String type;
    private String name;
    private double mass;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double leafLitter;
    private double waterLogging;
    private double permafrostDepth;
    private double rootDensity;
    private double salinity;
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

    public double getNitrogen() {
        return nitrogen;
    }

    public double getWaterRetention() {
        return waterRetention;
    }

    public double getSoilpH() {
        return soilpH;
    }

    public double getOrganicMatter() {
        return organicMatter;
    }

    public double getLeafLitter() {
        return leafLitter;
    }

    public double getWaterLogging() {
        return waterLogging;
    }

    public double getPermafrostDepth() {
        return permafrostDepth;
    }

    public double getRootDensity() {
        return rootDensity;
    }

    public double getSalinity() {
        return salinity;
    }

    public List<PairInput> getSections() {
        return sections;
    }
}

