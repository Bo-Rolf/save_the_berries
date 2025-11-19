// Varje Tile kan innehålla en Planta och sol
package com.mycompany.app;

import com.mycompany.app.Entities.Plant;
import com.mycompany.app.Interfaces.Placeable;

public class Tile {
    private int row;
    private int column;

    private boolean sunOnTile;
    private boolean isWater;
    private Placeable placeable;

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.sunOnTile = false;
        this.isWater = false;
    }

    public boolean getSunOnTile(){
        return sunOnTile;
    }

    private void setSunOnTile(boolean bool){
        this.sunOnTile = bool;
    }


    public boolean canPlaceSun(){
        return !sunOnTile;
    }

    public boolean canRemoveSun(){
        return sunOnTile;
    }

    public void placeSun(){
        setSunOnTile(true);
    }

    public void removeSun(){
        setSunOnTile(false);
    }

    public void place(Placeable p){
        if (placeable != null){
            throw new IllegalArgumentException("TIle already has a plant!");
        } else {

        this.placeable = p;

        p.setTileRemovalListener(() -> {
            this.placeable = null;
        });

        }
    }
}