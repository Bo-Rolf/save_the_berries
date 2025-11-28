package com.mycompany.app.model.entities;



import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Rectangle2D;


public class Sun extends Entity{
    public Sun(Vector2 vector) {
        super(100, "Sun", vector, new Rectangle2D.Double(vector.x, vector.y, 100, 100),"sun.png");
    }



    
}
