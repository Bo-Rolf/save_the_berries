// En matrix som representerar rutnätet för gräsmattan
// Varje cell i matrisen innehåller ett Tile-objekt
package com.mycompany.app;



public class Lawn {

    private final int rows;
    private final int cols;
    private Tile[][] grid;

    public Lawn(int rows, int cols) {
        this.rows=rows;
        this.cols=cols;
        grid = new Tile[rows][cols];
        // Initialisera varje cell i gräsmattan med ett Tile-objekt
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Tile(i, j);
            }
        }
    }

    public Tile getTile(int row, int col) {
        return grid[row][col];
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }
    
}