package com.mycompany.app.model;
import com.mycompany.app.model.entities.*;
import com.badlogic.gdx.math.Vector2;

public class EntityFactory {

    public static <T extends Plant> T createPlant(Class<T> type, float x, float y, int row, int col){
        try {
            Class[] cArg = new Class[3]; //Our constructor has 3 arguments
            cArg[0] = Vector2.class; //First argument is of *object* type Long
            cArg[1] = int.class; //Second argument is of *object* type String
            cArg[2] = int.class;
           
            return type.getDeclaredConstructor(cArg).newInstance(new Vector2(x, y), row, col);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown plant type: " + type+", error: "+e);
        }
    }
    public static <T extends Zombie> T  createZombie(Class<T> zombieType, float x, float y, int row) {
        try {
            //Skapar ett nytt objekt av typen
            Class[] cArg = new Class[3]; //
            cArg[0] = Vector2.class; //First argument is of *object* type Long
            return zombieType.getDeclaredConstructor(cArg).newInstance(new Vector2(x, y)); //Kör konstruktorn för typen
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown plant type: " + zombieType+", error: "+e);
        }
    }
}
