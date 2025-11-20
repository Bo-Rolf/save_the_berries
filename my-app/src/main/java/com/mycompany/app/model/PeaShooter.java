package com.mycompany.app.model;

import com.badlogic.gdx.math.Vector2;

public class PeaShooter extends Plant {

    private int damage;
    private double attackSpeed;

    public PeaShooter(int health, String name, Vector2 position, int sunCost, int cooldown, int damage, double attackSpeed) {
        super(health, name, position, sunCost, cooldown);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public Vector2 getPosition() {
        return super.getPosition();
    }

}
