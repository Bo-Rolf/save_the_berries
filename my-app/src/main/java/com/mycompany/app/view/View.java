package com.mycompany.app.view;

import com.mycompany.app.model.Model;
import com.mycompany.app.model.entities.*;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    private EntityView entityView;

    @Override
    public void create() {
        viewport = new FitViewport(800, 600); // Load texture
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture("board.png");
        entityView = new EntityView();
    }


    public View(Model model) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);
        this.model=model;
        Lwjgl3Application application =new Lwjgl3Application(this, config);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        
        List<Zombie> zombies = this.model.game.getZombies();
        List<Plant> plants = this.model.game.getPlants();




        float delta = Gdx.graphics.getDeltaTime();
        for (Zombie z : zombies) {
            z.update(delta);
        }
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        
        for (Zombie z : zombies) {
            entityView.draw(new Texture("zombie.png"),spriteBatch, z);
        }
        for (Plant p : plants) {
            entityView.draw(new Texture("pea_shooter.png"),spriteBatch, p);
        }

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
        entityView.dispose();
    }
}
