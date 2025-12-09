package com.mycompany.app.model;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.entities.Entitycfg;
import com.mycompany.app.model.entities.GameConfig;
import com.mycompany.app.model.entities.Plant;
import com.mycompany.app.model.entities.Zombie;

public class EntityFactory {


    public Map<String,Entitycfg> plant_cfgs;
    public Map<String,Entitycfg> zombie_cfgs;


    public EntityFactory(GameConfig gcfg){
        this.plant_cfgs = new HashMap<>();
        this.zombie_cfgs = new HashMap<>();
        for(Entitycfg e : gcfg.plants){
            plant_cfgs.put(e.name, e);
        }
        for(Entitycfg e2 : gcfg.zombies){
            zombie_cfgs.put(e2.name, e2);
        }



    }

    public Plant createPlant(Entitycfg cfg, float x, float y, int row, int col){
        try {
            //Class[] cArg = new Class[2]; //Our constructor has 3 arguments
            //cArg[0] = Entitycfg.class;
            //cArg[1] = Vector2.class; //First argument is of *object* type Long

            Plant p =cfg.p_type.getDeclaredConstructor(Entitycfg.class,Vector2.class).newInstance(cfg,new Vector2(x,y));
            return p;
            //return this.plant_cfgs.get(type).classtype.getDeclaredConstructor(cArg).newInstance(new Vector2(x,y),row,col);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown plant type: " + cfg.class_type+", error: "+e);
        }
    }
    public Zombie createZombie(String type, float x, float y) {
        try {
            
            Entitycfg cfg = this.zombie_cfgs.get(type);
            System.out.println("info:"+type+" "+cfg.name+" "+cfg.class_type+" ");
            Zombie z = cfg.z_type.getDeclaredConstructor(Entitycfg.class,Vector2.class).newInstance(cfg,new Vector2(x, y)); //Kör konstruktorn för typen
            return z;
            //return zombie_construct.get(type).getDeclaredConstructor(Vector2.class).newInstance(new Vector2(x, y)); //Kör konstruktorn för typen
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown zombie type: " + type+", error: "+e);
        }
    }
}
