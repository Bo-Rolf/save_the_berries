package com.mycompany.app.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mycompany.app.model.entities.Character;
import com.mycompany.app.model.entities.CurrencyCharacter;
import com.mycompany.app.model.entities.Enemy;
import com.mycompany.app.model.entities.EntityCfg;
import com.mycompany.app.model.entities.GameConfig;
import com.mycompany.app.model.entities.ShootingCharacter;

public class json_reader {
    

    public List<Character> p;
    public GameConfig config;
    public json_reader(){
        Json j = new Json();
        j.addClassTag("Enemy", Enemy.class);
        j.addClassTag("CurrencyCharacter", CurrencyCharacter.class);
        j.addClassTag("Shooter", ShootingCharacter.class);
        j.addClassTag("Character", Character.class);
        j.addClassTag("EntityCfg",EntityCfg.class);


        GameConfig gcfg = j.fromJson(GameConfig.class, Gdx.files.internal("entitys.json"));
        
        
        
        for(EntityCfg cfg : gcfg.characters){
            cfg.p_type = j.getClass(cfg.class_type);
        }
        for(EntityCfg cfg : gcfg.enemys){
           cfg.z_type = j.getClass(cfg.class_type);
        }


        this.config = gcfg;
    }
}
