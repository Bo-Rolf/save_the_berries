package com.mycompany.app.Entities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.mycompany.app.Interfaces.Placeable;

public abstract class Plant extends Entity implements Placeable{
    private final int sunCost;
    private final int cooldown; //to place the plant again
    private final int row;
    private final int column;
    private Runnable tileRemovalListener;
    
    public Plant(int health, String name, Point2D position, Rectangle2D hitBox, int sunCost, int cooldown, int row, int column){
        super(health, name, position, hitBox);
        this.sunCost = sunCost;
        this.cooldown = cooldown;
        this.row = row;
        this.column = column;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public int getCooldown(){
        return cooldown;
    }

    public int getSunCost(){
        return sunCost;
    }

    @Override
    public void die(){
        super.die();

        removeFromTile();
        //Notify tile-listener that the plant has died
    }

    public void setTileRemovalListener(Runnable listener){
        this.tileRemovalListener = listener;
    }

    public void removeFromTile(){
        if (tileRemovalListener != null){
            tileRemovalListener.run();
        }
        
        super.die();
    }
}
