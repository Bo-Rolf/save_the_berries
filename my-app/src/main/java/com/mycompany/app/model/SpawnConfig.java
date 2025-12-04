package com.mycompany.app.model;

public class SpawnConfig {
    public float startInterval;
    public float minInterval;
    public float rampSpeed;

    public SpawnConfig(float startInterval, float minInterval, float rampSpeed) {
        this.startInterval = startInterval;
        this.minInterval = minInterval;
        this.rampSpeed = rampSpeed;
    }

    public static SpawnConfig forDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> new SpawnConfig(6f, 3.5f, 0.01f);
            case NORMAL -> new SpawnConfig(4f, 2f, 0.05f);
            case HARD -> new SpawnConfig(2.8f, 1.1f, 0.07f);
        };
    }
}
