package com.mycompany.app.model;

import com.mycompany.app.Game;

public class Model {
    private final Lawn lawn = new Lawn(5, 8);
    public Game game;
    // Model class implementation
    public Model() {
        this.game = new Game();
        // Model initialization code
    }

    public Lawn getLawn() {
        return lawn;
    }
}
