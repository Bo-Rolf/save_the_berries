package com.mycompany.app.model;

import com.mycompany.app.view.json_reader;

public class Model {
    private Lawn lawn;
    public Game game;
    private Difficulty difficulty;
    private boolean goToMainMenu = false;
    private boolean gameOver = false;
    private int finalTime = 0;
    private boolean paused = false;
    private json_reader js;


    public Model() {
        this.lawn = new Lawn(5, 8); 
        this.js = new json_reader();
        this.game = new Game(js.config);
    }

    public boolean isPaused() {
    return paused;
    }

    public void setPaused(boolean paused) {
    this.paused = paused;
    }

    public Lawn getLawn() {
        return lawn;
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
        this.gameOver = true;
        this.finalTime = time;
    }

    public void setGameOver(boolean value) {
        this.gameOver = value;
        if (value && finalTime == 0) {
            this.finalTime = 0;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getFinalTime() {
        return finalTime;
    }
}
