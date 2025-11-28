package com.mycompany.app.controller;

import java.awt.Point;

import javax.crypto.spec.PBEKeySpec;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mycompany.app.model.Lawn;
import com.mycompany.app.model.Tile;
import com.mycompany.app.model.Model;
import com.mycompany.app.model.entities.*;


public class Controller {

    private final Model model;
    private final Lawn lawn;
    private final Viewport viewport;

    public Controller(Model model, Viewport viewport) {
        this.model = model;
        this.lawn = model.getLawn();
        this.viewport = viewport;
    }

    public void handleInput(float startX, float startY, float tileWidth, float tileHeight) {
        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();

           
            

            Vector2 worldCoords = viewport.unproject(new Vector2(mouseX, mouseY));

            model.game.collect_sun(new Point((int)worldCoords.x, (int)worldCoords.y));

            if (worldCoords.x >= startX && worldCoords.x <= startX + tileWidth * lawn.getCols()
                    && worldCoords.y >= startY && worldCoords.y <= startY + tileHeight * lawn.getRows()) {

                int col = (int) ((worldCoords.x - startX) / tileWidth);
                int row = (int) ((worldCoords.y - startY) / tileHeight);

                Tile clickedTile = lawn.getTile(row, col);

                if (clickedTile != null && clickedTile.placeable == null) {
                    float x = startX + col * tileWidth + tileWidth / 2f;
                    float y = startY + row * tileHeight + tileHeight / 2f;
                    Plant newPlant = new Sunflower(new Vector2(x, y), row, col);
                    model.game.addPlant(newPlant);
                    clickedTile.place(newPlant);
                    System.out.println("Placed "+newPlant.getName()+" at row " + row + ", col " + col);
            } 
            else {
                System.out.println("Tile already has a plant at row " + row + ", col " + col);
            }

                    
            }
                
            }
        }
    }

