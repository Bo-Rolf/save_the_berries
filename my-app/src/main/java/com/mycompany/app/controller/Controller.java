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
import com.mycompany.app.Game;
import com.mycompany.app.view.PlantSeedView;

public class Controller {

    private final Model model;
    private final Game game;
    private final Lawn lawn;
    private final Viewport viewport;
    private final PlantSeedView seedView;
    private int selectedSeedIndex = 0;

    // UI layout for seeds (should match View.draw call)
    private final float seedMarginLeft = 10f;
    private final float seedMarginTop = 10f;
    private final float seedSize = 64f;
    private final float seedSpacing = 8f;

    public Controller(Model model, Viewport viewport, PlantSeedView seedView) {
        this.model = model;
        this.game = model.game;
        this.lawn = model.getLawn();
        this.viewport = viewport;
        this.seedView = seedView;
    }

    public void handleInput(float startX, float startY, float tileWidth, float tileHeight) {
        if (Gdx.input.justTouched()) {

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();
            Vector2 worldCoords = viewport.unproject(new Vector2(mouseX, mouseY));

            model.game.collect_sun(new Point((int)worldCoords.x, (int)worldCoords.y));
            int clickedSeed = seedView.getSeedIndexAt(worldCoords.x, worldCoords.y, viewport, model.game.getplantSeeds(),
                    seedMarginLeft, seedMarginTop, seedSize, seedSpacing);

            if (clickedSeed != -1) {
                selectedSeedIndex = clickedSeed;
                System.out.println("Selected seed index " + selectedSeedIndex + " -> " + this.game.getPlantSeed(selectedSeedIndex));
                return; // don't try to place on grid for this click
            }

            

            if (worldCoords.x >= startX && worldCoords.x <= startX + tileWidth * lawn.getCols()
                    && worldCoords.y >= startY && worldCoords.y <= startY + tileHeight * lawn.getRows()) {
                int col = (int) ((worldCoords.x - startX) / tileWidth);
                int row = (int) ((worldCoords.y - startY) / tileHeight);
                Tile clickedTile = lawn.getTile(row, col);


                if (clickedTile != null) { //(clickedTile != null && clickedTile.placeable == null)
                    float x = startX + col * tileWidth + tileWidth / 2f;
                    float y = startY + row * tileHeight + tileHeight / 2f;
                    
                    
                    Tile tile =model.getLawn().getTile(row, col);
                    game.placePlant(selectedSeedIndex,tile, row, col, x,y);
                    selectedSeedIndex = -1; //oselecta seedet du använder
            }       
            
            else {
                System.out.println("Tile already has a plant at row " + row + ", col " + col);
            }

                    
            }
                
            }
        }
    

    public int getSelectedSeedIndex() {
        return selectedSeedIndex;
    }
}
