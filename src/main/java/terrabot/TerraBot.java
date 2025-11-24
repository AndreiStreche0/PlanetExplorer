package terrabot;

import planet.Cell;

public class TerraBot {
    private int energy;
    private int xCoord;
    private int yCoord;
    private boolean charging;
    private int chargeTimeStamp;

    public TerraBot(int energy, int xCoord, int yCoord) {
        this.energy = energy;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        charging = false;
        this.chargeTimeStamp = 0;
    }
    public boolean isCharging() {
        return this.charging;
    }

    public int getEnergy() {
        return energy;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public int getChargeTimeStamp() {
        return chargeTimeStamp;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public void setChargeTimeStamp(int chargeTimeStamp) {
        this.chargeTimeStamp = chargeTimeStamp;
    }

}
