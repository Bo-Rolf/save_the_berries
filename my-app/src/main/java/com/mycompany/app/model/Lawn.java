// En matrix som representerar rutnätet för gräsmattan
// Varje cell i matrisen innehåller ett Tile-objekt
package com.mycompany.app.model;

import com.mycompany.app.Interfaces.Placeable;

public class Lawn {
    private final Tile[][] grid;
    public Lawn(int rows, int cols) {
        grid = new Tile[rows][cols];
        // Initialisera varje cell i gräsmattan med ett Tile-objekt
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Tile();
            }
        }
    }

    public Tile getTile(int row, int col) {
        return grid[row][col];
    }

    public boolean place(Placeable p, int row, int col) {
        return getTile(row, col).place(p);
    }

    public boolean check_tile(int row, int col) {
        return getTile(row, col).getPlaceable()==null;
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }
    
}