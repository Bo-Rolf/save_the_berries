package com.mycompany.app.model.entities;


import java.awt.geom.Rectangle2D;

import javax.swing.text.Position;

import com.mycompany.app.Interfaces.Shooter;
import com.badlogic.gdx.math.Vector2;

public class Sunflower extends Plant{
    private final double sunCooldown = 15;
    private double timeSinceLastSun = 0;


    public Sunflower(entitycfg cfg, Vector2 pos){
        super(cfg,pos);
    }
    

    @Override
    public void update(double deltaTime) { // den räknar tid ifrån förra skottet
        timeSinceLastSun +=deltaTime;
    }

    public boolean canSpawnSun() {
        return timeSinceLastSun >= sunCooldown;
    }

    
    public Sun spawnSun() {
        if (canSpawnSun()){
            timeSinceLastSun = 0;
            entitycfg cfg = new entitycfg();
            cfg.health=1;
            cfg.name="Sun";
            cfg.texture="sun.png";
            return new Sun(cfg,new Vector2(getPosition()));
        }
        return null;

    }
}
