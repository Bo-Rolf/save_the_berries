package com.mycompany.app.model;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.Game;
import com.mycompany.app.Lawn;
import com.mycompany.app.model.entities.NormalZombie;
import com.mycompany.app.model.entities.PeaShooter;
import com.mycompany.app.model.entities.Wallnut;

public class Model {
    private final Lawn lawn = new Lawn(5, 8);
    public Game game;
    // Model class implementation
    public Model() {
        this.game = new Game();
        Lawn lawn = new Lawn(5, 9);
        NormalZombie zombie1 = new NormalZombie(new Vector2(100,1));
        Wallnut walnut1 = new Wallnut(new Vector2(1,1), 1, 1);
        lawn.getTile(1,1).place(walnut1);
        game.addZombie(zombie1);
        game.addPlant(walnut1);
        System.out.println(game.getPlants());
        System.out.println(game.getZombies());
        System.out.printf("Wallnut health %d", walnut1.getHealth());
        zombie1.eat(walnut1);
        System.out.printf("Wallnut health after zombie eat %d ", walnut1.getHealth());
        walnut1.takeDamage(1000);
        System.out.printf("Wallnut health after damage taken %d ", walnut1.getHealth());
        System.out.println(lawn.getTile(1,1).getPlaceable());
        walnut1.takeDamage(1000);
        System.out.printf("Wallnut health after damage taken %d ", walnut1.getHealth());
        System.out.printf("Wallnut should be dead now");
        System.out.println(game.getPlants());
        System.out.println(lawn.getTile(1,1).getPlaceable());
        lawn.getTile(1,1).place(new PeaShooter(new Vector2(1,1), 1, 1));


        
        

        // Model initialization code
    }

    public Lawn getLawn() {
        return lawn;
    }
}
