package entities;

public class Soil extends Entity{
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;

    public Soil(String name, double mass, double nitrogen, double waterRetention, double soilpH,
                double organicMatter) {
        super(name, mass);

        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
        super.setScanned(true);
    }
}
