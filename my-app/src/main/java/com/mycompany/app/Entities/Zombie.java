package com.mycompany.app.Entities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.mycompany.app.Interfaces.Damageable;

public abstract class Zombie extends Entity {
    private int moveSpeed;
    private int damage;
    private double attackSpeed;

    public Zombie(int health, String name, Point2D position, Rectangle2D hitBox, int moveSpeed, int damage, double attackSpeed){
        super(health, name, position, hitBox);
        this.moveSpeed = moveSpeed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }
    public void move(){
        Point2D currentPosition=getPosition();
        currentPosition=new Point2D.Double(currentPosition.getX() - moveSpeed, currentPosition.getY());
    }

    private void attack(Damageable target){
        target.takeDamage(damage);
    }

    

}
