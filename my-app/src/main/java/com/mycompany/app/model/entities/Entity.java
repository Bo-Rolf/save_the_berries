package com.mycompany.app.model.entities;


import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.Interfaces.Damageable;

public abstract class Entity implements Damageable {
    private int health;
    private final String name;
    private Vector2 position; // characteror kanske inte ska ha sån här position utan grid position?
    private final Rectangle2D hitBox;
    public String textureString;

    

    public Entity(EntityCfg cfg,Vector2 position,Rectangle2D.Double hb) {
        this.health = cfg.health;
        this.name = cfg.name;
        this.position = position;
        this.hitBox = hb;
        this.textureString=cfg.texture;
    }

    public abstract void update(double deltaTime);

    // metoder
    @Override
    public void takeDamage(int amount) {
        health -= amount;
    }

    public boolean isAlive() {
        return health > 0;
    }

    protected void updateHitBox() {
        double x = (double) position.x;
        double y = (double) position.y;
        hitBox.setRect(x, y, hitBox.getWidth(), hitBox.getHeight());
    }

    protected void updatePosition(Vector2 newPosition) {
        this.position = newPosition;
        updateHitBox();
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public Rectangle2D getHitBox() {
        return hitBox;
    }
    @Override
    public void die() {
        takeDamage(this.health);
    }
    public String getTexturestring(){
        return this.textureString;
    }
    
}
