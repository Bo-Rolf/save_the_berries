package com.mycompany.app.model.entities;

import com.badlogic.gdx.math.Vector2;
import java.awt.geom.Rectangle2D;

import com.mycompany.app.Interfaces.Placeable;

public class Plant extends Entity implements Placeable {
    private final int sunCost;

    private final double cooldown; // to place the plant again
    private final int row;
    private final int column;
    private Runnable tileRemovalListener;


    public Plant(entitycfg cfg, Vector2 position) {
        super(cfg, position, new Rectangle2D.Double(position.x-35,position.y-35,(float)70,(float)70));
        this.sunCost = cfg.sunCost;
        this.cooldown = cfg.cooldown;
        this.row = cfg.row;
        this.column = cfg.column;
        
    }


    
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public double getCooldown() {
        return cooldown;
    }

    public int getSunCost() {
        return sunCost;
    }

    @Override
    public void die() {
        super.die();

        removeFromTile();
        // Notify tile-listener that the plant has died
    }
    @Override
    public void setTileRemovalListener(Runnable listener) {
        this.tileRemovalListener = listener;
    }
    @Override
    public void removeFromTile() {
        if (tileRemovalListener != null) {
            tileRemovalListener.run();
        }
    }

    public void update(double deltaTime) {
        //kör ej update hitbox för plantor här, den ska skapas ibörjan och alldrig ändras efteråt

    }
    
}
