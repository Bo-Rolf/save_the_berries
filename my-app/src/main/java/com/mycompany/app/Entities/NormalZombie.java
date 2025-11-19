package com.mycompany.app.Entities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class NormalZombie extends Zombie {
    public NormalZombie(Point2D position){
        super(270, "Zombie", position, new Rectangle2D.Double(position.getX(), position.getY(), 50, 50), 10, 10, 5.0);
    }

}
