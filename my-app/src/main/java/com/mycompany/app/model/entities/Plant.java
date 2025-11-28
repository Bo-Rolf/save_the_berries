package com.mycompany.app.model.entities;

import com.badlogic.gdx.math.Vector2;
import java.awt.geom.Rectangle2D;

import com.mycompany.app.Interfaces.Placeable;

public abstract class Plant extends Entity implements Placeable {
    private final int sunCost;
    private final int cooldown; // to place the plant again
    private final int row;
    private final int column;
    private Runnable tileRemovalListener;

    public Plant(int health, String name, Vector2 position, int sunCost, int cooldown, int row,
            int column,String textureString) {
        super(health, name, position, new Rectangle2D.Double(position.x-35,position.y-35,(float)70,(float)70),textureString);
        this.sunCost = sunCost;
        this.cooldown = cooldown;
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getCooldown() {
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
