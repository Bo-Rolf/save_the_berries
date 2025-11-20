package com.mycompany.app.Interfaces;

import com.mycompany.app.Entities.Projectile;

public interface Shooter {
    // called by the game loop; return a new projectile or null if no shot fired
    Projectile shoot();
    boolean canShoot();
}