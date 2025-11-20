package com.mycompany.app.Entities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
public abstract class Projectile extends Entity {
    private double MoveSpeed;
    private int damage;

    public Projectile(int health, String name, Point2D position, Rectangle2D hitBox, double MoveSpeed, int damage){
        super(health, name, position, hitBox);
        this.MoveSpeed = MoveSpeed;
        this.damage = damage;
    }

    public void update(double deltaTime) {
        Point2D pos = getPosition();
        Point2D newPos = new Point2D.Double(pos.getX() + MoveSpeed * deltaTime, pos.getY());
        updatePosition(newPos);
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
