package com.mycompany.app.model.entities;


import java.awt.geom.Rectangle2D;
import com.mycompany.app.Interfaces.Shooter;
import com.badlogic.gdx.math.Vector2;

public class Sunflower extends Plant{
    private final double cooldownSeconds = 10;
    private double timeSinceLastSun = 0;

    public Sunflower(Vector2 position, int row, int column) {
        super(50, "Sunflower", position, new Rectangle2D.Double(position.x, position.x, 100, 100), 50, 5, row,
                column,"sunflower.png");
    }
    @Override
    public void update(double deltaTime) { // den räknar tid ifrån förra skottet
        timeSinceLastSun +=deltaTime;
    }

    public boolean canSpawnSun() {
        return timeSinceLastSun >= cooldownSeconds;
    }

    
    public Sun spawnSun() {
        if (canSpawnSun()){
            timeSinceLastSun = 0;
            return new Sun(new Vector2(getPosition()));
        }
        return null;

    }
}
