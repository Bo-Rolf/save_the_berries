package com.mycompany.app.model;

import java.awt.Point;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mycompany.app.model.entities.Currency;
import com.mycompany.app.view.CharacterSeedView;
import com.mycompany.app.view.json_reader;

public class Game {

    public int currency;
    private float elapsedTime = 0f;
    private Difficulty difficulty;
    private boolean goToMainMenu = false;
    private boolean gameOver = false;
    private boolean paused = false;
    private int finalTime = 0;
    private final json_reader js;
    private final Lawn lawn = new Lawn(5, 8);
    private final EntityManager entity_manager;
    private int selectedSeedIndex = -1;



    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.js = new json_reader();
        // Lägg till initiala enemys och characters här om det behövs
        this.currency = 200;
        this.entity_manager = new EntityManager(js.config,this.lawn,difficulty);
    }


    public void handleClick(Vector2 worldCoords, float startX, float startY, float tileWidth, float tileHeight,Viewport viewport){
        float seedMarginLeft = 10f;
        float seedMarginTop = 10f;
        float seedSize = 64f;
        float seedSpacing = 8f;
        collect_currency(new Point((int)worldCoords.x, (int)worldCoords.y));

        //int oldIndex = this.
        int clickedSeed = CharacterSeedView.getSeedIndexAt(worldCoords.x, worldCoords.y, viewport, this.entity_manager.getcharacterSeeds(),
                seedMarginLeft, seedMarginTop, seedSize, seedSpacing);
        if (clickedSeed != -1) {
            selectedSeedIndex = clickedSeed;
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
                Tile tile =this.getLawn().getTile(row, col);
                if(selectedSeedIndex!=-1){
                    if(currency>=entity_manager.getCharacterSeed(selectedSeedIndex).cost){
                        boolean result = entity_manager.placeCharacter(selectedSeedIndex,tile, row, col, x, y);
                        if(result==true){
                            currency -= entity_manager.getCharacterSeed(selectedSeedIndex).cost;
                        }
                    
                    }
                }
                selectedSeedIndex = -1; //oselecta seedet du använder
            }                        
        }

    
    }
    
    public EntityManager gEntityManager(){
        return this.entity_manager;
    }
    public void updateGameState(double deltaTime) {
        if (!gameOver) {        
            entity_manager.updateGameState(deltaTime);
            if (entity_manager.checkEnemy()) {
                setGameOver((int)this.elapsedTime);
            }
            else{
                elapsedTime+=deltaTime;
            }
        }
    }

    public double getelapsedtime(){
        return this.elapsedTime;
    }

    public int get_current_currency(){
        return this.currency;
    }

    //Funktion för att försöka kollekta sol, iterarar över varje sol och kollar ifall musen är på rätt plats
    //Vector2 strulade så jag konverterade det till point istället

    public void collect_currency(Point mouse){
        for(Currency s :this.entity_manager.getCurrencys()){
            if(s.getHitBox().contains(mouse)){
                currency += s.get_currency_value();
                s.die();
            }
        }
    }

    public Lawn getLawn() {
        return this.lawn;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isGoToMainMenu() {
        return goToMainMenu;
    }

    public void setGoToMainMenu(boolean value) {
        this.goToMainMenu = value;
    }

    public void setGameOver(int time) {
        this.finalTime = time;
        this.gameOver = true;
        
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getFinalTime() {
        return finalTime;
    }

    public int getSelectedSeedIndex(){
        return this.selectedSeedIndex;
    }
    

}
