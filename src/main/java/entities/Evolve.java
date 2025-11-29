package entities;

import planet.Cell;

public interface Evolve {
    /**
     * evolution method for every entity that has evolution method
     * @param currentCell cell of entity
     * @param timeStamp time of simulation
     */
    void evolve(Cell currentCell, int timeStamp);
}
