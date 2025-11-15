package entities;

public class Soil extends Entity{
    public double nitrogen;
    public double waterRetention;
    public double soilpH;
    public double organicMatter;

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
