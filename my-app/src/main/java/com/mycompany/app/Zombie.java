package com.mycompany.app;
import java.awt.geom.Point2D;
public abstract class Zombie extends Entity {
    private int moveSpeed;
    private int damage;
    private double attackSpeed;

    public Zombie(int health, String name, Point2D position, int moveSpeed, int damage, double attackSpeed){
        super(health, name, position);
        this.moveSpeed = moveSpeed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }
}
