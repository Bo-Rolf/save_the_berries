package com.mycompany.app;
import java.awt.geom.Point2D;

public abstract class Entity {
    private int health;
    private String name;
    private Point2D position; // plantor kanske inte ska ha sån här position utan grid position?

    public Entity(int health, String name, Point2D position){
        this.health = health;
        this.name = name;
        this.position = position;
    }

    //metoder
    private void takeDamage(int damage){

    }
    private void Die(){
        
    }
}
