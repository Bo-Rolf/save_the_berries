package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.entities.Entity;
import com.mycompany.app.model.entities.NormalZombie;
import com.mycompany.app.model.entities.Plant;
import com.mycompany.app.model.entities.Projectile;
import com.mycompany.app.model.entities.Zombie;

public class Game {

    private boolean Playing;
    private boolean Paused;
    private boolean Over;

    private final List<Plant> plants = new ArrayList<>();

    private final List<Zombie> zombies = new ArrayList<>();

    private final List<Projectile> projectiles = new ArrayList<>();
    
    private double deltaTime=0.016;
    private long lastUpdateTime;
    private void startGame() {
        Playing = true;
        lastUpdateTime = System.nanoTime();
        while (Playing) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0; // till sekunder
            lastUpdateTime = currentTime;

            if (!Paused && !Over) {
                updateGameState(deltaTime);
            }
            drawGameState();
        }
    }

    private void endGame() {

    }

    private void updateGameState(double deltaTime) {
        updateProjectiles(deltaTime);
        updateZombies(deltaTime);
    }

    private void drawGameState() {

    }

    private void removeEntity(Entity e) {
        if (e instanceof Zombie)
            zombies.remove(e);
        else if (e instanceof Plant)
            plants.remove(e);
        else if (e instanceof Projectile)
            projectiles.remove(e);
        e = null;
    }

    private void updateZombies(double deltaTime) {
        for (Zombie zombie : zombies) {
            for (Plant plant : plants) {
                if (zombie.checkCollision(plant)) {
                    if (zombie.canEat()) {
                    zombie.eat(plant);
                    }
                }
                else {
                    zombie.update(deltaTime);
                } 
            }
        }
    }

    private void updateProjectiles(double deltaTime) {
        for (Projectile projectile : projectiles) {
            projectile.update((float) deltaTime);
            for (Zombie zombie : zombies) {
                if (projectile.checkCollision(zombie)) {
                    projectile.onHit(zombie);
                }
            }
        }
    }

    public void addZombie(Zombie zombie){
        zombies.add(zombie);
        addEntity(zombie);
    }

    public void addPlant(Plant plant){
        plants.add(plant);
        addEntity(plant);
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
