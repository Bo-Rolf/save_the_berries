package com.mycompany.app.model.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import com.badlogic.gdx.math.Vector2;

public abstract class Projectile extends Entity {
    private double MoveSpeed;
    private int damage;

    public Projectile(int health, String name, Vector2 position, Rectangle2D hitBox, float MoveSpeed, int damage) {
        super(health, name, position, hitBox);
        this.MoveSpeed = MoveSpeed;
        this.damage = damage;
    }

    public void move(float delta) {
        Vector2 pos = getPosition();
        pos.x -= MoveSpeed * delta;
        updatePosition(pos);
    }

    public boolean checkCollision(Zombie zombie) {
        return getHitBox().intersects(zombie.getHitBox());
    }

    private void onHit(Zombie target) {
        target.takeDamage(damage);
        takeDamage(1); // så den dödar sig själv
    }

    public int getDamage() {
        return damage;
    }

    public double getSpeed() {
        return MoveSpeed;
    }
}
