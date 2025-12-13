package com.mycompany.app.controller;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mycompany.app.model.Game;
import com.mycompany.app.model.Lawn;
import com.mycompany.app.model.Tile;
import com.mycompany.app.view.CharacterSeedView;

public class Controller {

    private final Game game;
    private final Lawn lawn;
    private final Viewport viewport;
    private final CharacterSeedView seedView;
    private int selectedSeedIndex = -1;

    // UI layout for seeds (should match View.draw call)
    private final float seedMarginLeft = 10f;
    private final float seedMarginTop = 10f;
    private final float seedSize = 64f;
    private final float seedSpacing = 8f;

    public Controller(Game game, Viewport viewport, CharacterSeedView seedView) {
        this.game = game;
        this.lawn = this.game.getLawn();
        this.viewport = viewport;
        this.seedView = seedView;
    }

    public void handleInput(float startX, float startY, float tileWidth, float tileHeight) {
        if (Gdx.input.justTouched()) {

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();
            Vector2 worldCoords = viewport.unproject(new Vector2(mouseX, mouseY));

            this.game.collect_currency(new Point((int)worldCoords.x, (int)worldCoords.y));

            //int oldIndex = this.
            int clickedSeed = seedView.getSeedIndexAt(worldCoords.x, worldCoords.y, viewport, this.game.getcharacterSeeds(),
                    seedMarginLeft, seedMarginTop, seedSize, seedSpacing);

            if (clickedSeed != -1) {
                selectedSeedIndex = clickedSeed;
                System.out.println("Selected seed index " + selectedSeedIndex + " -> " + this.game.getCharacterSeed(selectedSeedIndex));
                return; // don't try to place on grid for this click
            }

            

            if (worldCoords.x >= startX && worldCoords.x <= startX-3 + tileWidth * this.lawn.getCols()
                    && worldCoords.y >= startY && worldCoords.y <= startY + tileHeight * this.lawn.getRows()) {
                int col = (int) ((worldCoords.x - startX) / tileWidth);
                int row = (int) ((worldCoords.y - startY) / tileHeight);
                Tile clickedTile = this.lawn.getTile(row, col);


                if (clickedTile != null) { //(clickedTile != null && clickedTile.placeable == null)
                    float x = startX + col * tileWidth + tileWidth / 2f;
                    float y = startY + row * tileHeight + tileHeight / 2f;
                    
                    
                    Tile tile =this.game.getLawn().getTile(row, col);
                    game.placeCharacter(selectedSeedIndex,tile, row, col, x,y);
                    selectedSeedIndex = -1; //oselecta seedet du använder
            }       
            
            else {
                System.out.println("Tile already has a character at row " + row + ", col " + col);
            }

                    
            }
                
            }
        }
    

    public int getSelectedSeedIndex() {
        return selectedSeedIndex;
    }
}
