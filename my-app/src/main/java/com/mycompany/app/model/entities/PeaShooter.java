package com.mycompany.app.model.entities;


import java.awt.geom.Rectangle2D;
import com.mycompany.app.Interfaces.Shooter;
import com.badlogic.gdx.math.Vector2;

public class PeaShooter extends Plant implements Shooter {
    private final double cooldownSeconds = 1.5;
    private double timeSinceLastShot = 0;

    public PeaShooter(Vector2 position, int row, int column) {
        super(50, "PeaShooter", position, 100, 5, row,
                column,"pea_shooter.png");
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
            return new PeaProjectile(new Vector2(getPosition()));
        }
        return null;

    }
}
