package com.mycompany.app.model;

import java.util.Random;

import com.mycompany.app.model.entities.Enemy;

public class EnemySpawner {

    private final SpawnConfig config;
    private final EntityFactory factory;

    private float spawnTimer = 0f;
    private float currentInterval;
    private float gameTime = 0f;

    private final Random random = new Random();

    public EnemySpawner(EntityFactory factory2,Difficulty difficulty) {
        this.factory = factory2;
        this.config = SpawnConfig.forDifficulty(difficulty);
        this.currentInterval = config.startInterval;
    }

    public Enemy update(float delta,float worldWidth, float gridY, float tileHeight, int rows) {
        this.gameTime += delta;
        spawnTimer += delta;


        
        // Ramping
        if (currentInterval > config.minInterval) {
            currentInterval -= config.rampSpeed * delta;
            if(currentInterval<config.minInterval){
                currentInterval=config.minInterval;
            }    
        }

        if (spawnTimer >= currentInterval) {
            spawnTimer = 0f;

            int row = random.nextInt(rows);
            float x = worldWidth + 20;
            float y = gridY + row * tileHeight;
            

            Enemy z = chooseEnemy(x, y);
            return z;
        }
        return null;
    }

    private Enemy chooseEnemy(float x, float y) {
        float r = random.nextFloat();

        float normalChance = 1f;
        float fastChance = 0.0f;
        //float tankChance = 0.0f;

        // More tank/fast later in the game
        if (gameTime > 30) {
            normalChance = 0.5f;
            fastChance = 0.35f;
            //tankChance = 0.15f;
        }
        if (gameTime > 60) {
            normalChance = 0.3f;
            fastChance = 0.45f;
            //tankChance = 0.25f;
        }
        

        if (r < normalChance) return this.factory.createEnemy("Normal Enemy",x,y);
        else if (r < normalChance + fastChance) return this.factory.createEnemy("Fast Enemy", x,y);
        else return this.factory.createEnemy("Tank Enemy", x,y);

    }
}
