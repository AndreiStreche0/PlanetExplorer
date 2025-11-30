package terrabot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Class that handles the terraBot, it s position, knowledge, scanning and charging
 */
public class TerraBot {
    private int energy;
    private int xCoord;
    private int yCoord;
    private boolean charging;
    private int chargeTime;
    private final LinkedHashMap<String, List<String>> knowledgeBase;
    private final Set<String> scannedItems;

    public TerraBot(final int energy, final int xCoord, final int yCoord) {
        this.energy = energy;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        charging = false;
        this.chargeTime = 0;
        this.knowledgeBase = new LinkedHashMap<>();
        this.scannedItems = new HashSet<>();
    }
    /**
     *
     * @return if the robot is charging
     */
    public boolean isCharging() {
        return this.charging;
    }
    /**
     * charges the robot battery for one timeStamp
     */
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
    public void setEnergy(final int energy) {
        this.energy = energy;
    }

    public void setxCoord(final int xCoord) {
        this.xCoord = xCoord;
    }
    public void setyCoord(final int yCoord) {
        this.yCoord = yCoord;
    }

    public void setCharging(final boolean charging) {
        this.charging = charging;
    }
    public void setChargeTime(final int chargeTime) {
        this.chargeTime = chargeTime;
    }
    public LinkedHashMap<String, List<String>> getKnowledgeBase() {
        return knowledgeBase;
    }

    public Set<String> getScannedItems() {
        return scannedItems;
    }

    /**
     * scans item
     * @param item the item
     */
    public void scanItem(final String item) {
        scannedItems.add(item);
    }

    /**
     * @param item the item
     * @return checks if the item is scanned
     */
    public boolean hasScanned(final String item) {
        return scannedItems.contains(item);
    }

    /**
     * adds a new fact in the robot knowledge base
     * @param item the item the fact is about
     * @param fact the fact
     */
    public void addFact(final String item, final String fact) {
        if (!knowledgeBase.containsKey(item)) {
            knowledgeBase.put(item, new ArrayList<>());
        }
        knowledgeBase.get(item).add(fact);
    }
}
