package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mycompany.app.Interfaces.Shooter;
import com.mycompany.app.model.entities.*;

import java.awt.Point;
import java.lang.System;

public class Game {

    private boolean Playing;
    private boolean Paused;
    private boolean Over;
    public int sun;

    private final List<Plant> plants = new ArrayList<>();

    private final List<Zombie> zombies = new ArrayList<>();

    private final List<Projectile> projectiles = new ArrayList<>();

    private final List<Sun> collectable_suns = new ArrayList<>();

    private final List<String> plantSeeds = new ArrayList<>();
    
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
        }
    }
    public Game() {
        // Lägg till initiala zombies och plants här om det behövs
        addZombie(new NormalZombie(new Vector2(800, 300)));
        addZombie(new NormalZombie(new Vector2(400, 300)));
        sun = 200;
        plantSeeds.add("PeaShooter");
        plantSeeds.add("Sunflower");
        plantSeeds.add("Wallnut");
    }

    private void endGame() {

    }

    public void updateGameState(double deltaTime) {
        updateProjectiles(deltaTime);
        updateZombies(deltaTime);
        updatePlants(deltaTime);
        updateDeathCheck();
        //System.out.println(+zombies.size()+collecable_suns.size()+projectiles.size());
        
        //System.out.println("plants "+ plants.size());
    }

    
    private void updateDeathCheck() {
        // Tar bort döda entities från listorna
        plants.removeIf(plant -> !plant.isAlive());
        zombies.removeIf(zombie -> !zombie.isAlive());
        projectiles.removeIf(projectile -> !projectile.isAlive());
        collectable_suns.removeIf(s -> !s.isAlive());
    }

    private void removeEntity(Entity e) {
        if (e instanceof Zombie)
            //zombies.remove(e);
            e=null;
        else if (e instanceof Plant)
           ((Plant) e).removeFromTile();
        else if (e instanceof Projectile)
            //projectiles.remove(e);
        e = null;
    }

    private void updatePlants(double deltaTime) {
        for (Plant plant : plants) {
            
            plant.update(deltaTime);
            if (plant instanceof Shooter shooter) { 
                Projectile p = shooter.shoot();
                if (p != null) {
                    addProjectile(p);
                }
            }
            if(plant instanceof Sunflower flower){
                Sun s = flower.spawnSun();
                if(s != null){
                    collectable_suns.add(s);
                }

            }
        }
    }

    private void updateZombies(double deltaTime) {
        for (Zombie zombie : zombies) {
            zombie.update(deltaTime);
            boolean hittatplanta = false;
            for (Plant plant : plants) {
                if (zombie.checkCollision(plant)) {
                    if (zombie.canEat()) {
                    zombie.eat(plant);
                    }
                    hittatplanta = true;
                }   
            }
                if(!hittatplanta){
                zombie.move(deltaTime);
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

    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
        addEntity(projectile);
    }

    public void addPlant(Plant plant){
        plants.add(plant);
        sun -= plant.getSunCost();
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
    public List<Projectile> getProjectiles(){
        return this.projectiles;
    }

    public List<Sun> getSuns(){
        return this.collectable_suns;
    }

    //Funktion för att försöka kollekta sol, iterarar över varje sol och kollar ifall musen är på rätt plats
    //Vector2 strulade så jag konverterade det till point istället
    public void collect_sun(Point mouse){
        System.out.print(mouse);
        for(Sun s :this.collectable_suns){
            System.out.print(s.getHitBox().getMinX());
            if(s.getHitBox().contains(mouse)){
                sun+=s.get_sun_value();
                //"döda" solen
                s.takeDamage(1000);
                System.out.print("du collectade");
            }
        }
    }
    public List<String> getplantSeeds(){
        return this.plantSeeds;
    }
    
    public String getPlantSeed(int index){
        if (index<0 || index>=plantSeeds.size()){
            return "PeaShooter";
        }
        return plantSeeds.get(index);
    }
}
