package com.mycompany.app.model;
import java.util.Vector;
import com.badlogic.gdx.math.Vector2;

import com.mycompany.app.model.entities.PeaShooter;
import com.mycompany.app.model.entities.EntityCfg;
import com.mycompany.app.model.entities.Plant;

public class PlantSeed {
    public EntityCfg type;
    public String t;
    public int cost;
    public double cooldown;
    public double cooldown_left;

    

    public PlantSeed(EntityCfg cfg){
        this.type=cfg;
        this.t=cfg.texture;
        this.cooldown=cfg.cooldown;
        this.cost = cfg.sunCost;
    }
    
    public void update(double deltaTime){
        
        cooldown_left-=deltaTime;
        if(cooldown_left<0){
            cooldown_left=0;
        }

    }
    
    public boolean ready_to_place(){
        return cooldown_left<=0;
    }
    public void try_place(){
        if(ready_to_place()){
            this.cooldown_left=this.cooldown;
        }
    }


    public String getTexturestring(){
        return this.t;
    }
    
}
