package com.mycompany.app.model.entities;


import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.Interfaces.Shooter;

public class ShootingCharacter extends Character implements Shooter {
    private double cooldownSeconds;
    private double timeSinceLastShot = 0;
    private EntityCfg projectile_cfg;

    public ShootingCharacter(EntityCfg cfg,Vector2 position) {
        super(cfg,position);
        this.cooldownSeconds = cfg.cooldownSeconds;
        this.projectile_cfg = cfg.SpawnedEntityCfg;

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

            return new Projectile(this.projectile_cfg,new Vector2(getPosition()));
        }
        return null;

    }

    
}
