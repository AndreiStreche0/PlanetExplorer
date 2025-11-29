package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class CommandInput {
    private String command;
    private String smell;
    private String color;
    private String sound;
    private int timestamp;
    private int timeToCharge;
    private String direction;
    private String subject;
    private String type;
    private String components;
    private double rainfall;
    private double windSpeed;
    private boolean desertStorm;
    private String season;
    private int numberOfHikers;
    private String improvementType;
    private String name;

    public String getCommand() {
        return command;
    }

    public String getSmell() {
        return smell;
    }

    public String getColor() {
        return color;
    }

    public String getSound() {
        return sound;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getTimeToCharge() {
        return timeToCharge;
    }

    public String getSubject() {
        return subject;
    }

    public String getDirection() {
        return direction;
    }

    public String getType() {
        return type;
    }

    public String getComponents() {
        return components;
    }

    public double getRainfall() {
        return rainfall;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public boolean isDesertStorm() {
        return desertStorm;
    }

    public String getSeason() {
        return season;
    }

    public int getNumberOfHikers() {
        return numberOfHikers;
    }

    public String getImprovementType() {
        return improvementType;
    }

    public String getName() {
        return name;
    }
}

