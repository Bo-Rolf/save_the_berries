package com.mycompany.app.model;

import com.badlogic.gdx.math.Vector2;

public abstract class Plant extends Entity {

    private int sunCost;
    private int cooldown; //to place the plant again

    public Plant(int health, String name, Vector2 position, int sunCost, int cooldown) {
        super(health, name, position);
        this.sunCost = sunCost;
        this.cooldown = cooldown;
    }

    public Vector2 getPosition() {
        return super.getPosition();
    }

}
