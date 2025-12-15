package com.mycompany.app.model.entities;


import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.math.Vector2;

public class Projectile extends Entity {
    private final double MoveSpeed;
    private final int damage;

    public Projectile(EntityCfg cfg,Vector2 position) {
        super(cfg,position,new Rectangle2D.Double(position.x,position.y,20,20));
        this.MoveSpeed = cfg.moveSpeed;
        this.damage = cfg.damage;
    }
    private void move(double delta) {
        Vector2 pos = getPosition();
        pos.x += MoveSpeed * delta;
        updatePosition(pos);
    }

    public boolean checkCollision(Enemy enemy) {
        return getHitBox().intersects(enemy.getHitBox());
    }

    public void onHit(Enemy target) {
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
