package com.mycompany.app.model.entities;

//När programmet läser ut json så behövs en class den konverterar till
//Varenda fält i json filen blir konverterade till saken med samma namn.
//Det här är en class som sparar all info om objektet

public class EntityCfg {
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

    
    //Dom här används för att kalla på deras konstruktor, tex om jag vill s
    public Class<Plant> p_type;
    public Class<Zombie> z_type;
}
