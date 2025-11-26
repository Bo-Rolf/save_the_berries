package com.mycompany.app.model.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.Interfaces.Damageable;

public abstract class Zombie extends Entity {
    private float moveSpeed;
    private int damage;
    private double attackSpeed; //tiden mellan attacker i sekunder
    private double TimeSinceLastBite = 0.0;

    public Zombie(int health, String name, Vector2 position, Rectangle2D hitBox, float moveSpeed, int damage,
            double attackSpeed) {
        super(health, name, position, hitBox);
        this.moveSpeed = moveSpeed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public void move(double deltaTime) {

        Vector2 pos = getPosition();
        pos.x -= moveSpeed * deltaTime;
    }

    public boolean canEat() {
        
        return (TimeSinceLastBite >= attackSpeed);
        }

    public boolean checkCollision(Damageable target) {
        return (getHitBox().intersects(((Entity) target).getHitBox()));
    }

    public void eat(Plant target) {
        target.takeDamage(damage);
        System.out.println("health "+target.getHealth());
        TimeSinceLastBite = 0.0;
    }

    public int getDamage() {
        return damage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void update(double deltaTime) {
        TimeSinceLastBite += deltaTime;
        
    }
}
