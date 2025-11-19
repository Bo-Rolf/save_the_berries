package com.mycompany.app.Entities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PeaProjectile extends Projectile {
    public PeaProjectile(Point2D position){
        super(1, "Pea", position, new Rectangle2D.Double(position.getX(), position.getY(), 50,50), 20.0, 5);
    }
}
