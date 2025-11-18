package com.mycompany.app.model;

import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public abstract class Entity {

    private int health;
    private String name;
    private Vector2 position; // plantor kanske inte ska ha sån här position utan grid position?

    public Entity(int health, String name, Vector2 position) {
        this.health = health;
        this.name = name;
        this.position = position;
    }

    //metoder
    private void takeDamage(int damage) {

    }

    // private boolean isDead() {
    //     health -= amount;
    // }
    public Vector2 getPosition() {
        return position;

    }

}
