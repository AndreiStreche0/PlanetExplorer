package planet;

import fileio.TerritorySectionParamsInput;
import fileio.WaterInput;
import planet.Cell;

public class Matrix {
    private final int width;
    private final int height;
    private Cell[][] cells;

    public Matrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void setCells(TerritorySectionParamsInput inputParams) {
        for (WaterInput water : inputParams.water) {
            cells[water.sections.]
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Cell getCell(int x, int y) {
        if (x >= 0 && x < height && y >= 0 && y < width) {
            return cells[x][y];
        } else {
            return null;
        }
    }
}
