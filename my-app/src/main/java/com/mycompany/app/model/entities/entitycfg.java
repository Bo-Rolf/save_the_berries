package com.mycompany.app.model.entities;

public class Entitycfg {
    public String type;   // "normal", "fast", …
    public int health;
    public String name;
    public float moveSpeed;
    public int damage;
    public float attackSpeed;
    public String texture;
    public String class_type;
    //Plant saker
    public int sunCost;
    public double cooldown;
    public int row;
    public int column;

    

    public Class<Plant> p_type;
    public Class<Zombie> z_type;
}
