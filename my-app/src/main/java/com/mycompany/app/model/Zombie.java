package com.mycompany.app.model;

import com.badlogic.gdx.math.Vector2;

public abstract class Zombie extends Entity {

    private float moveSpeed;
    private int damage;
    private float attackSpeed;

    public Zombie(int health, String name, Vector2 position, float moveSpeed, int damage, float attackSpeed) {
        super(health, name, position);
        this.moveSpeed = moveSpeed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public void move(float delta) {
        Vector2 pos = getPosition();
        pos.x -= moveSpeed * delta;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public int getDamage() {
        return damage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void update(float delta) {
        move(delta);
    }

}
