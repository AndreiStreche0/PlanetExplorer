package terrabot;

import java.util.*;

public class TerraBot {
    private int energy;
    private int xCoord;
    private int yCoord;
    private boolean charging;
    private int chargeTime;
    private LinkedHashMap<String, List<String>> knowledgeBase;
    private Set<String> scannedItems;

    public TerraBot(int energy, int xCoord, int yCoord) {
        this.energy = energy;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        charging = false;
        this.chargeTime = 0;
        this.knowledgeBase = new LinkedHashMap<>();
        this.scannedItems = new HashSet<>();
    }
    public boolean isCharging() {
        return this.charging;
    }

    public void charge() {
        this.chargeTime--;
        this.energy++;

        if (this.chargeTime <= 0) {
            this.charging = false;
        }
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

    public int getChargeTime() {
        return chargeTime;
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

    public void setChargeTime(int chargeTime) {
        this.chargeTime = chargeTime;
    }

    public LinkedHashMap<String, List<String>> getKnowledgeBase() {
        return knowledgeBase;
    }

    public Set<String> getScannedItems() {
        return scannedItems;
    }

    public void scanItem(String item) {
        scannedItems.add(item);
    }

    public boolean hasScanned(String item) {
        return scannedItems.contains(item);
    }

    public void addFact(String item, String fact) {
        if (!knowledgeBase.containsKey(item)) {
            knowledgeBase.put(item, new ArrayList<>());
        }
        knowledgeBase.get(item).add(fact);
    }
}
