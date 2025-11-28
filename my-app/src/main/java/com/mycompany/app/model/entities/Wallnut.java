package com.mycompany.app.model.entities;

import com.badlogic.gdx.math.Vector2;
import java.awt.geom.Rectangle2D;

public class Wallnut extends Plant {
    public Wallnut(Vector2 position, int row, int column) { // borde ta in en grid
        super(2000, "wallnut", position, 50, 10, row,
                column,"wallnut.png");
    }
}
