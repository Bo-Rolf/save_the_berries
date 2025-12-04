package com.mycompany.app.controller;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.*;
import com.mycompany.app.model.entities.NormalZombie;
import com.mycompany.app.model.entities.TankyZombie;
import com.mycompany.app.model.entities.FastZombie;
import com.mycompany.app.model.entities.Zombie;

import java.util.Random;

public class ZombieSpawner {

    private final Model model;
    private final SpawnConfig config;

    private float spawnTimer = 0f;
    private float currentInterval;
    private float gameTime = 0f;

    private Random random = new Random();

    public ZombieSpawner(Model model, Difficulty difficulty) {
        this.model = model;
        this.config = SpawnConfig.forDifficulty(difficulty);
        this.currentInterval = config.startInterval;
    }

    public void update(float delta, float worldWidth, float gridY, float tileHeight, int rows) {
    gameTime += delta;
    spawnTimer += delta;

    // Ramping
    if (currentInterval > config.minInterval) {
        currentInterval -= config.rampSpeed * delta;
    }

    if (spawnTimer >= currentInterval) {
        spawnTimer = 0f;

        int row = random.nextInt(rows);
        float x = worldWidth + 20;
        float y = gridY + row * tileHeight;

        Zombie z = chooseZombie(row, x, y);
        model.game.addZombie(z);
    }
}

private Zombie chooseZombie(int row, float x, float y) {
    float r = random.nextFloat();

    float normalChance = 0.7f;
    float fastChance = 0.2f;
    float tankyChance = 0.1f;

    // More tank/fast later in the game
    if (gameTime > 30) {
        normalChance = 0.5f;
        fastChance = 0.35f;
        tankyChance = 0.15f;
    }
    if (gameTime > 60) {
        normalChance = 0.3f;
        fastChance = 0.45f;
        tankyChance = 0.25f;
    }

    if (r < normalChance) return new NormalZombie(new Vector2(x, y));
    else if (r < normalChance + fastChance) return new FastZombie(new Vector2(x, y));
    else return new TankyZombie(new Vector2(x, y));

}
}
