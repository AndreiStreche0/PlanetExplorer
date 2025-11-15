package entities;

public class Water extends Entity{

    public double purity;
    public double salinity;
    public int turbidity;
    public double contaminantIndex;
    public double pH;
    public boolean isFrozen;

    public Water(String name, double mass, double purity, double salinity,
                 int turbidity, double contaminantIndex, double pH, boolean isFrozen) {
        super(name, mass);
        this.purity = purity;
        this.salinity = salinity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.pH = pH;
        this.isFrozen = isFrozen;
    }
}
