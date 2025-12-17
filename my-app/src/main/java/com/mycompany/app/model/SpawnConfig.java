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
            case EASY -> new SpawnConfig(15f, 1f, 0.01f);
            case NORMAL -> new SpawnConfig(15f, 0.5f, 0.02f);
            case HARD -> new SpawnConfig(10, 0.01f, 0.05f);
        };
    }
}
