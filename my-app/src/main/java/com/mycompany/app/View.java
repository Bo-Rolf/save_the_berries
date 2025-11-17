package com.mycompany.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;

public class View implements ApplicationListener {

    private Model model;
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private Texture backgroundTexture;

    @Override
    public void create() {
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();
        try {
            backgroundTexture = new Texture("board.png");
            System.out.println("Texture loaded!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);

        new Lwjgl3Application(new View(new Model()), config);
    }

    public View(Model model) {
        this.model = model;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //spriteBatch.draw(backgroundTexture, 0, 0);
        // Här ritar du dina sprites, t.ex:
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        spriteBatch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
    }
}
