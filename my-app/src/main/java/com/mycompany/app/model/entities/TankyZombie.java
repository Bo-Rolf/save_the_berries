package com.mycompany.app.model.entities;

import com.badlogic.gdx.math.Vector2;

public class TankyZombie extends Zombie {

    public TankyZombie(Vector2 position) {
        super(
            300,
            "Tank Zombie",
            position,
            10f, 
            10,
            0.3,
            "zombie.png"
        );
    }
}
