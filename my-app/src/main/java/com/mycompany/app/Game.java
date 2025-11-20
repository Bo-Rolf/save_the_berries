package com.mycompany.app;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.app.Entities.Plant;
import com.mycompany.app.Entities.Zombie;
import com.mycompany.app.Entities.Entity;


public class Game {
    
private boolean Playing;
private boolean Paused;
private boolean Over;

private final List<Plant> plants = new ArrayList<>();
private final List<Zombie> zombies = new ArrayList<>();

private long lastUpdateTime; 

private void startGame(){
    Playing = true;
    lastUpdateTime = System.nanoTime();
    while(Playing){
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0; // till sekunder
        lastUpdateTime = currentTime;

        if (!Paused && !Over){
            updateGameState();
            }
        drawGameState();
        }
    }
private void endGame(){

    }
private void updateGameState(){

    }
private void drawGameState(){

    }


    private void removeEntity(Entity e){
        e = null;
    }
    
    private void addEntity(Entity e){
        e.setDeathListener(() -> {
            //entities.remove(e);
            removeEntity(e);
        });
    }

}
