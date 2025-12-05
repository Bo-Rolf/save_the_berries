//Sak som spawnar sol
package com.mycompany.app;

import java.util.Random;

import com.mycompany.app.model.entities.Sun;
import com.mycompany.app.model.entities.entitycfg;
import com.badlogic.gdx.math.Vector2;

import java.util.random.*;

public class Sunspawner {
    private final double sunCooldown = 15;
    private double timeSinceLastSun = 0;
    private final Random r = new Random();

    public Sunspawner() {

    }
    public void update(double deltaTime) { //Räknar tid från förra solen
        timeSinceLastSun +=deltaTime;
    }

    public boolean canSpawnSun() {
        return timeSinceLastSun >= sunCooldown;
    }

    
    public Sun spawnSun() {
        if (canSpawnSun()){
            timeSinceLastSun = 0;
            Vector2 sunpos = new Vector2(this.r.nextInt(100,700),this.r.nextInt(100,500));
                    entitycfg cfg = new entitycfg();
        cfg.health=1;
        cfg.name="Sun";
            cfg.texture="sun.png";
            return new Sun(cfg,sunpos);
        }
        return null;

    }
}
