package com.mycompany.app.model;

import com.badlogic.gdx.math.Vector2;

public class BasicZombie extends Zombie {

    public BasicZombie(int health, String name, Vector2 position, float moveSpeed, int damage, float attackSpeed) {
        super(health, name, position, moveSpeed, damage, attackSpeed);
    }
}
