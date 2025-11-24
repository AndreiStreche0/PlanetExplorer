package entities;

import planet.Cell;

public interface Evolve {
    public abstract void evolve(Cell currentCell, int timeStamp);
}
