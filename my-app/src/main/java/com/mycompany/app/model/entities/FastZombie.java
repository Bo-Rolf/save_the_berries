package com.mycompany.app.model.entities;

import com.badlogic.gdx.math.Vector2;

public class FastZombie extends Zombie {

    public FastZombie(Vector2 position) {
        super(
            150,           
            "Fast Zombie", 
            position,
            25f, 
            7,
            0.2,          
            "zombie.png" 
        );
    }
}
