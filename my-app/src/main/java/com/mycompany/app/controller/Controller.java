package com.mycompany.app.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mycompany.app.model.Game;

public class Controller {

    private final Game game;
    private final Viewport viewport;
    public Controller(Game game, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;
    }

    public void handleInput(float startX, float startY, float tileWidth, float tileHeight) {
        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();
            Vector2 worldCoords = viewport.unproject(new Vector2(mouseX, mouseY));
            this.game.handleClick(worldCoords,startX, startY, tileWidth, tileHeight, viewport);
        }
    }
    
    public void update(double deltaTime){
        this.game.updateGameState(deltaTime);
    }
}
