package com.mycompany.app.model.entities;

import com.badlogic.gdx.math.Vector2;
import java.awt.geom.Rectangle2D;

public class PeaProjectile extends Projectile {
    public PeaProjectile(Vector2 position) {
        super(1, "Pea", position, new Rectangle2D.Double(position.x, position.y, 20, 20), 500, 1000,"pea.png");
    }


}
