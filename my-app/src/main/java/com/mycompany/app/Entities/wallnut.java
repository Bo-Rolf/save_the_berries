package com.mycompany.app.Entities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class wallnut extends Plant{
    public wallnut(Point2D position, int row, int column){ //borde ta in en grid
        super(200, "wallnut", position, new Rectangle2D.Double(position.getX(), position.getY(), 50, 50), 50, 10, row, column);
    }
}
