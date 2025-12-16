package com.mycompany.app.view;


import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

public class Texturemanager {


    private final Map<String, Texture> textures;
    public Texturemanager(){
        this.textures = new HashMap<>();
    }

    public Texture get_Texture(String texString){
        if(!this.textures.containsKey(texString)){
            this.textures.put(texString, new Texture(texString));
        }
       
        return this.textures.get(texString);
    }

    //
    public void Disposetextures(){
        for (Texture tex : this.textures.values()) {
            tex.dispose();
        }
    }
}
