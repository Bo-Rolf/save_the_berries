// Varje Tile kan innehålla en Planta och sol
package com.mycompany.app.model;

import com.mycompany.app.Interfaces.Placeable;

public class Tile {
    private int row;
    private int column;

    private boolean isWater;
    public Placeable placeable;

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.isWater = false;
    }

    public Placeable getPlaceable() {
        return this.placeable;
    }

    public void place(Placeable p) {
        if (placeable != null) {
            throw new IllegalArgumentException("Tile already has a plant!");
        } else {
            this.placeable = p;

            p.setTileRemovalListener(() -> {
                this.placeable = null;
            });

        }
    }
}