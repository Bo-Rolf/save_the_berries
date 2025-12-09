package com.mycompany.app.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mycompany.app.model.entities.Entitycfg;
import com.mycompany.app.model.entities.GameConfig;
import com.mycompany.app.model.entities.PeaShooter;
import com.mycompany.app.model.entities.Plant;
import com.mycompany.app.model.entities.Sunflower;
import com.mycompany.app.model.entities.Zombie;

public class json_reader {
    

    public List<Plant> p;
    public GameConfig config;
    public json_reader(){
        Json j = new Json();
        j.addClassTag("Zombie", Zombie.class);
        j.addClassTag("SunPlant", Sunflower.class);
        j.addClassTag("Shooter", PeaShooter.class);
        j.addClassTag("Plant", Plant.class);
        j.addClassTag("Entitycfg",Entitycfg.class);


        GameConfig gcfg = j.fromJson(GameConfig.class, Gdx.files.internal("entitys.json"));
        
        
        
        for(Entitycfg cfg : gcfg.plants){
            cfg.p_type = j.getClass(cfg.class_type);
            System.out.print(cfg.p_type);
            System.out.print(cfg.class_type);
        }
        for(Entitycfg cfg : gcfg.zombies){
           cfg.z_type = j.getClass(cfg.class_type);
        }


        this.config = gcfg;
    }
}
