package com.mycompany.app.model;

import java.awt.geom.Point2D;

public abstract class Projectile extends Entity {

    private double MoveSpeed;
    private int damage;

    public Projectile(int health, String name, Point2D position, double MoveSpeed, int damage) {
        super(health, name, position);
        this.MoveSpeed = MoveSpeed;
        this.damage = damage;
    }
}
