package com.mycompany.app;
import java.awt.geom.Point2D;

public abstract class Plant extends Entity {
    private int sunCost;
    private int cooldown; //to place the plant again
    
    public Plant(int health, String name, Point2D position, int sunCost, int cooldown){
        super(health, name, position);
        this.sunCost = sunCost;
        this.cooldown = cooldown;
    }
}
