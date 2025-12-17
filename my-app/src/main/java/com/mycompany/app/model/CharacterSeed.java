package com.mycompany.app.model;
import com.mycompany.app.model.entities.EntityCfg;

public class CharacterSeed {
    public EntityCfg type;
    public String t;
    public int cost;
    public double cooldown;
    public double cooldownLeft;

    

    public CharacterSeed(EntityCfg cfg){
        this.type=cfg;
        this.t=cfg.texture;
        this.cooldown=cfg.cooldown;
        this.cost = cfg.currencyCost;
    }
    
    public void update(double deltaTime){
        
        cooldownLeft-=deltaTime;
        if(cooldownLeft<0){
            cooldownLeft=0;
        }

    }
    
    public boolean ready_to_place(){
        return cooldownLeft<=0;
    }
    public void try_place(){
        if(ready_to_place()){
            this.cooldownLeft=this.cooldown;
        }
    }


    public String getTexturestring(){
        return this.t;
    }
    
}
