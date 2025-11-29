package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class AnimalInput {
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

    public List<PairInput> getSections() {
        return sections;
    }

    public double getMass() {
        return mass;
    }
}

