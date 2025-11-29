package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class TerritorySectionParamsInput {
    private List<SoilInput> soil;
    private List<PlantInput> plants;
    private List<AnimalInput> animals;
    private List<WaterInput> water;
    private List<AirInput> air;

    public List<SoilInput> getSoil() {
        return soil;
    }

    public List<PlantInput> getPlants() {
        return plants;
    }

    public List<AnimalInput> getAnimals() {
        return animals;
    }

    public List<WaterInput> getWater() {
        return water;
    }

    public List<AirInput> getAir() {
        return air;
    }
}


