// Varje Tile kan innehålla en Charactera och sol
package com.mycompany.app.model;

import com.mycompany.app.Interfaces.Placeable;

public class Tile {
    public Placeable placeable;

    public Tile() {
        //this.row = row;
        //this.column = column;
    }

    public Placeable getPlaceable() {
        return this.placeable;
    }

    public boolean is_occupied(){
        return !(this.placeable==null);
    }

    public boolean place(Placeable p) {
        if (placeable != null) {
            return false;
        } else {
            this.placeable = p;

            p.setTileRemovalListener(() -> {
                this.placeable = null;
            });
            return true;

        }
    }
}