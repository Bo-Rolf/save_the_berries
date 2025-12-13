package com.mycompany.app.model.entities;

import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.Interfaces.Damageable;

public class Enemy extends Entity {
    private final float moveSpeed;
    private final int damage;
    private final double attackSpeed; //tiden mellan attacker i sekunder
    private double TimeSinceLastBite = 0.0;

    public Enemy(EntityCfg cfg,Vector2 position) {
        super(cfg,position,new Rectangle2D.Double(position.x, position.y, 60, 80));
        this.moveSpeed = cfg.moveSpeed;
        this.damage = cfg.damage;
        this.attackSpeed = cfg.attackSpeed;
    }

    public void move(double deltaTime) {

        Vector2 pos = getPosition();
        pos.x -= moveSpeed * deltaTime;
        this.updatePosition(pos);
    }

    public boolean canEat() {
        
        return (TimeSinceLastBite >= attackSpeed);
        }

    public boolean checkCollision(Damageable target) {
        return (getHitBox().intersects(((Entity) target).getHitBox()));
    }

    public void eat(Character target) {
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
    @Override
    public void update(double deltaTime) {
        TimeSinceLastBite += deltaTime;
        
    }
}
