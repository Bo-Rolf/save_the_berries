package com.mycompany.app.model.entities;


import java.awt.geom.Rectangle2D;
import com.badlogic.gdx.math.Vector2;

public abstract class Projectile extends Entity {
    private final double MoveSpeed;
    private final int damage;

    public Projectile(int health, String name, Vector2 position, Rectangle2D hitBox, float MoveSpeed, int damage,String textureString) {
        super(health, name, position, hitBox,textureString);
        this.MoveSpeed = MoveSpeed;
        this.damage = damage;
    }
    private void move(double delta) {
        Vector2 pos = getPosition();
        pos.x += MoveSpeed * delta;
        updatePosition(pos);
    }

    public boolean checkCollision(Zombie zombie) {
        return getHitBox().intersects(zombie.getHitBox());
    }

    public void onHit(Zombie target) {
        if (checkCollision(target)==true) {
        target.takeDamage(damage);
        takeDamage(1); // så den dödar sig själv
        }
    }

    public int getDamage() {
        return damage;
    }

    public double getSpeed() {
        return MoveSpeed;
    }
    @Override
    public void update(double deltaTime) {
        Vector2 pos = getPosition();

        if(pos.x>1000 || pos.x<-200 ||pos.y>700 || pos.y <-200){
            takeDamage(1000);
        }
        move(deltaTime);
    }
}
