package com.mycompany.app.model;
import com.mycompany.app.model.entities.EntityCfg;

public class CharacterSeed {
    public EntityCfg type;
    public String t;
    public int cost;
    public double cooldown;
    public double cooldown_left;

    

    public CharacterSeed(EntityCfg cfg){
        this.type=cfg;
        this.t=cfg.texture;
        this.cooldown=cfg.cooldown;
        this.cost = cfg.currencyCost;
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
