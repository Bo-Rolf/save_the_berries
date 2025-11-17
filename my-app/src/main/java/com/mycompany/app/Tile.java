// Varje Tile kan innehålla en Planta och sol
package com.mycompany.app;


public class Tile {
    private int row;
    private int column;

    private boolean sunOnTile;
    private boolean plantOnTile;
    private boolean isWater;
    private Plant plant;
    
    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.sunOnTile = false;
        this.plantOnTile = false;
        this.isWater = false;
    }

    public boolean getPlantOnTile(){
        return plantOnTile;
    }
    public boolean getSunOnTile(){
        return sunOnTile;
    }
    
    private void setPlantOnTile(boolean bool){
        this.plantOnTile = bool;
    }

    private void setSunOnTile(boolean bool){
        this.sunOnTile = bool;
    }

    public boolean canPlant(){
        return !plantOnTile;
    }

    public boolean canRemovePlant(){
        return plantOnTile;
    }

    public void plantPlant(){
        setPlantOnTile(true);
    }

    public void removePlant(){
        setPlantOnTile(false);
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


    
}