package com.mycompany.app.model.entities;


import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.Interfaces.Shooter;

public class ShootingCharacter extends Character implements Shooter {
    private final double cooldownSeconds = 1.5;
    private double timeSinceLastShot = 0;

    public ShootingCharacter(EntityCfg cfg,Vector2 position) {
        super(cfg,position);

    }
    @Override
    public void update(double deltaTime) { // den räknar tid ifrån förra skottet
        timeSinceLastShot +=deltaTime;
    }

    @Override
    public boolean canShoot() {
        return timeSinceLastShot >= cooldownSeconds;
    }

    @Override
    public Projectile shoot() {
        if (canShoot()){
            timeSinceLastShot = 0;
            EntityCfg cfg = new EntityCfg();
            cfg.health = 1;
            cfg.name = "Pea";
            cfg.moveSpeed = 500;
            cfg.texture = "projectile.png";
            return new Projectile(cfg,new Vector2(getPosition()));
        }
        return null;

    }

    
}
