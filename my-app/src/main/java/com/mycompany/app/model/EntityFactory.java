package com.mycompany.app.model;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.entities.Character;
import com.mycompany.app.model.entities.Enemy;
import com.mycompany.app.model.entities.EntityCfg;
import com.mycompany.app.model.entities.GameConfig;

public class EntityFactory {


    public Map<String,EntityCfg> character_cfgs;
    public Map<String,EntityCfg> enemy_cfgs;


    public EntityFactory(GameConfig gcfg){
        this.character_cfgs = new HashMap<>();
        this.enemy_cfgs = new HashMap<>();
        for(EntityCfg e : gcfg.characters){
            character_cfgs.put(e.name, e);
        }
        for(EntityCfg e2 : gcfg.enemys){
            enemy_cfgs.put(e2.name, e2);
        }



    }

    public Character createCharacter(EntityCfg cfg, float x, float y, int row, int col){
        try {
            //Class[] cArg = new Class[2]; //Our constructor has 3 arguments
            //cArg[0] = EntityCfg.class;
            //cArg[1] = Vector2.class; //First argument is of *object* type Long

            Character p =cfg.p_type.getDeclaredConstructor(EntityCfg.class,Vector2.class).newInstance(cfg,new Vector2(x,y));
            return p;
            //return this.character_cfgs.get(type).classtype.getDeclaredConstructor(cArg).newInstance(new Vector2(x,y),row,col);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown character type: " + cfg.class_type+", error: "+e);
        }
    }
    public Enemy createEnemy(String type, float x, float y) {
        try {
            
            EntityCfg cfg = this.enemy_cfgs.get(type);
            System.out.println("info:"+type+" "+cfg.name+" "+cfg.class_type+" ");
            Enemy z = cfg.z_type.getDeclaredConstructor(EntityCfg.class,Vector2.class).newInstance(cfg,new Vector2(x, y)); //Kör konstruktorn för typen
            return z;
            //return enemy_construct.get(type).getDeclaredConstructor(Vector2.class).newInstance(new Vector2(x, y)); //Kör konstruktorn för typen
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown enemy type: " + type+", error: "+e);
        }
    }
}
