package com.mycompany.app.Entities;
import java.awt.geom.Point2D;

import org.w3c.dom.css.Rect;

import java.awt.geom.*;
import com.mycompany.app.Interfaces.Damageable;

public abstract class Entity implements Damageable{
    private int health;
    private final String name;
    private Point2D position; // plantor kanske inte ska ha sån här position utan grid position?
    private Rectangle2D hitBox;
    private Runnable deathListener;

    public Entity(int health, String name, Point2D position, Rectangle2D hitBox){
        this.health = health;
        this.name = name;
        this.position = position;
        this.hitBox = hitBox;
    }
    //metoder
    public void takeDamage(int amount){
        health -= amount;
        if (health <= 0){
            die();
        }
    }
    

    protected void updateHitBox(){
        hitBox.setRect(position.getX(), position.getY(), hitBox.getWidth(), hitBox.getHeight());
    }

    protected void updatePosition(Point2D newPosition){
        this.position = newPosition;
        updateHitBox();
    }
    
    public Point2D getPosition(){
        return position;
    }

    public int getHealth(){
        return health;
    }
    public String getName(){
        return name;
    }
    public Rectangle2D getHitBox(){
        return hitBox;
    }

    public void setDeathListener(Runnable listener){
        this.deathListener = listener;
    }

    public void die(){
        if (deathListener != null){
            deathListener.run();
        }
    }
}
