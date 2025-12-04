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

import com.mycompany.app.model.EntityFactory;
import com.mycompany.app.model.PlantSeed;
import com.mycompany.app.model.Tile;

public class Game {

    private boolean Playing;
    private boolean Paused;
    private boolean Over = false;
    public int sun;
    private float elapsedTime = 0f;

    private final List<Plant> plants = new ArrayList<>();
    private final List<Zombie> zombies = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<Sun> collectable_suns = new ArrayList<>();

    


    private final List<PlantSeed> plantSeeds = new ArrayList<>();
    
    private Sunspawner sunspawner = new Sunspawner();

    private long lastUpdateTime;
    // private void startGame() {
    //     Playing = true;
    //     lastUpdateTime = System.nanoTime();
    //     while (Playing) {
    //         long currentTime = System.nanoTime();
    //         double deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0; // till sekunder
    //         lastUpdateTime = currentTime;

    //         if (!Paused && !Over) {
    //             updateGameState(deltaTime);
    //         }
    //     }
    // }
    public Game() {
        // Lägg till initiala zombies och plants här om det behövs
        this.sun = 200;
        this.plantSeeds.add(new PlantSeed(PeaShooter.class));
        this.plantSeeds.add(new PlantSeed(Sunflower.class));
        this.plantSeeds.add(new PlantSeed(Wallnut.class));
    }

    private void endGame() {

    }

    public void updateGameState(double deltaTime) {
        updateProjectiles(deltaTime);
        updateZombies(deltaTime);
        updatePlants(deltaTime);
        updatePlantSeeds(deltaTime);
        updateSunSpawner(deltaTime);
        updateDeathCheck();

        if(!Over){
            elapsedTime += deltaTime;
        }
        
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


    private void updateSunSpawner(double deltaTime) {
        this.sunspawner.update(deltaTime);
        Sun s = sunspawner.spawnSun();
        if(s != null){
            collectable_suns.add(s);
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
    private void updatePlantSeeds(double deltaTime) {
        for (PlantSeed plantseed : this.plantSeeds) {
            plantseed.update((float) deltaTime);
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


    public void placePlant(int plantseedIndex,Tile tile, int row, int col,float x, float y) {
        PlantSeed p =getPlantSeed(plantseedIndex);
        if(p==null){
            System.out.print("No plantseed selected");
        }
        else{
            Plant newPlant = EntityFactory.createPlant(getPlantSeed(plantseedIndex).type, x, y, row, col);
            if(newPlant==null){
                System.out.print("Plant error, plant does not exist");
            }
            else if(this.sun<newPlant.getSunCost()){
                System.out.println("Not enough sun for "+newPlant.getName()+", current:"+this.sun+" "+newPlant.getSunCost());
            }
            else if (!this.getPlantSeed(plantseedIndex).ready_to_place()) {
                System.out.print(newPlant.getName()+" Is not ready yet,"+getPlantSeed(plantseedIndex).cooldown_left+" seconds left");
            }   
            else if(tile.is_occupied()){
                System.out.print("That tile is already occupied");
            }
            else{
                this.plants.add(newPlant);
                sun -= newPlant.getSunCost();
                addEntity(newPlant);
                tile.place(newPlant);
                this.getPlantSeed(plantseedIndex).try_place();
                System.out.println("Placed "+newPlant.getName()+" at row " + row + ", col " + col);
            }
        }
        
        
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
    public int get_current_sun(){
        return this.sun;
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
    public  List<PlantSeed> getplantSeeds(){
        return this.plantSeeds;
    }
    


    public PlantSeed getPlantSeed(int index){
        if (index<0 || index>=this.plantSeeds.size()){
            return null;
        }
        return this.plantSeeds.get(index);
    }
}
