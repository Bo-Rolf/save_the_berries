package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.entities.Entity;
import com.mycompany.app.model.entities.NormalZombie;
import com.mycompany.app.model.entities.Plant;
import com.mycompany.app.model.entities.Zombie;

public class Game {

    private boolean Playing;
    private boolean Paused;
    private boolean Over;

    private final List<Plant> plants = new ArrayList<>();

    private final List<Zombie> zombies = new ArrayList<>();
    
    public Game(){
        zombies.add(new NormalZombie(new Vector2(50,50)));
    }
    private long lastUpdateTime;
    private void startGame() {
        Playing = true;
        lastUpdateTime = System.nanoTime();
        while (Playing) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0; // till sekunder
            lastUpdateTime = currentTime;

            if (!Paused && !Over) {
                updateGameState();
            }
            drawGameState();
        }
    }

    private void endGame() {

    }

    private void updateGameState() {

    }

    private void drawGameState() {

    }

    private void removeEntity(Entity e) {
        e = null;
    }


    public void addZombie(Zombie zombie){
        zombies.add(zombie);
        addEntity(zombie);
    }

    private void addEntity(Entity e) {
        e.setDeathListener(() -> {
            // entities.remove(e);
            removeEntity(e);
        });
    }

    public List<Zombie> getZombies(){
        return this.zombies;
    }
    public List<Plant> getPlants(){
        return this.plants;
    }

}
