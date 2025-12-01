package com.mycompany.app.model;

import com.mycompany.app.Game;
import com.mycompany.app.model.entities.Plant;

public class Model {
    private final Lawn lawn = new Lawn(5, 8);
    public Game game;
    // Model class implementation
    public Model() {
        this.game = new Game();
        // Model initialization code
    }   

    public void placePlant(Plant plant, int row, int col) {
        if (game.sun >= plant.getSunCost() && lawn.place(plant, row, col)) {
            game.addPlant(plant);
        } else {
            System.out.println("Not enough sun or invalid position!");
        }
    }

    public Lawn getLawn() {
        return lawn;
    }

}
