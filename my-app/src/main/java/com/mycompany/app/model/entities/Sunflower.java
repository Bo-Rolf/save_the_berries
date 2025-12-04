package com.mycompany.app.model.entities;


import java.awt.geom.Rectangle2D;
import com.mycompany.app.Interfaces.Shooter;
import com.badlogic.gdx.math.Vector2;

public class Sunflower extends Plant{
    private final double sunCooldown = 15;
    private double timeSinceLastSun = 0;

    

    public Sunflower(Vector2 position, int row, int column) {
        super(50, "Sunflower", position, 50, 7.5, row,
                column,"sunflower.png");
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
            return new Sun(new Vector2(getPosition()),25);
        }
        return null;

    }
}
