package com.mycompany.app.Entities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import com.mycompany.app.Interfaces.Placeable;
import com.mycompany.app.Interfaces.Shooter;


public class PeaShooter extends Plant implements Shooter {
    private final double cooldownSeconds = 1.5;
    private double timeSinceLastShot = 0.0;

    public PeaShooter(Point2D position, int row, int column){
        super(50, "PeaShooter", position, new Rectangle2D.Double(position.getX(), position.getY(), 50,50), 100, 5, row, column);
    }

    
    public void update(double delta) { // den räknar tid ifrån förra skottet
        timeSinceLastShot += delta;
    }

    @Override
    public boolean canShoot() {
        return timeSinceLastShot >= cooldownSeconds;
    }

    @Override
    public Projectile shoot() {
        if (!canShoot()) return null;
        timeSinceLastShot = 0.0;
        return new PeaProjectile(getPosition());
    }
}
