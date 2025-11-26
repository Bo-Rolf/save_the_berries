package com.mycompany.app.model.entities;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class NormalZombie extends Zombie {
    public NormalZombie(Vector2 position) {
        super(270, "Zombie", position, new Rectangle2D.Double(position.x, position.x, 1000, 1000), 10, 10, 0.2);
    }

}
