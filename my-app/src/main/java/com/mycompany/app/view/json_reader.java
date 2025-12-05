package com.mycompany.app.view;

import com.badlogic.gdx.Gdx;
import com.mycompany.app.model.entities.*;
import com.badlogic.gdx.utils.Json;
import java.util.List;

import javax.swing.JApplet;

public class json_reader {
    

    public List<Plant> p;
    public List<Zombie> z;
    public json_reader(){
        Json j = new Json();
        j.addClassTag("Zombie", Zombie.class);

        j.addClassTag("Plant", Plant.class);
        j.addClassTag("SunPlant", Sunflower.class);
        j.addClassTag("Shooter", PeaShooter.class);


        entitycfg zombies = j.fromJson(entitycfg.class, Gdx.files.internal("zombies.json"));
        //String plant_json = Gdx.files.internal("plants.json").readString();

    }
}
