package com.mycompany.app.model;
import com.mycompany.app.model.entities.*;
import com.badlogic.gdx.math.Vector2;

public class EntityFactory {
    public static Plant createPlant(String plantType, float x, float y, int row, int col) {
        switch (plantType) {
            case "PeaShooter":
                return new PeaShooter(new Vector2(x, y), row, col);
            case "Sunflower":
                return new Sunflower(new Vector2(x, y), row, col);
            case "Wallnut":
                return new Wallnut(new Vector2(x, y), row, col);
            default:
                throw new IllegalArgumentException("Unknown plant type: " + plantType);
        }
    }
    public static Zombie createZombie(String zombieType, float x, float y, int row) {
        switch (zombieType) {
            case "NormalZombie":
                return new NormalZombie(new Vector2(x, y));
            // Add more zombie types as needed
            default:
                throw new IllegalArgumentException("Unknown zombie type: " + zombieType);
        }
    }
}
