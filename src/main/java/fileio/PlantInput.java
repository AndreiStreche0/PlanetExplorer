package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class PlantInput {
    private String type;
    private String name;
    private double mass;
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

    public List<PairInput> getSections() {
        return sections;
    }
}

