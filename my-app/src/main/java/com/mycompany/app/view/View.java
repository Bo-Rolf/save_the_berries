package com.mycompany.app.view;

import com.mycompany.app.model.Model;
import com.mycompany.app.model.entities.*;
import com.mycompany.app.model.Lawn;
import com.mycompany.app.model.Tile;

import java.util.List;

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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class View implements ApplicationListener {

    private Model model;
    private Lawn lawn;
    private SpriteBatch spriteBatch;
    private Viewport viewport;

    private Texture backgroundTexture;
    private Texture zombieTexture;
    private Texture plantTexture;

    private EntityView entityView;
    private ShapeRenderer shapeRenderer;


    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);

        new Lwjgl3Application(new View(new Model()), config);
    }

    public View(Model model) {
        this.model = model;
        this.lawn = model.getLawn();
    }

    @Override
    public void create() {
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("board.png");
        zombieTexture = new Texture("Zombie.png");
        plantTexture = new Texture("pea_shooter.png");

        entityView = new EntityView();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {

        List<Zombie> zombies = model.game.getZombies();
        List<Plant> plants = model.game.getPlants();

        float delta = Gdx.graphics.getDeltaTime();
        for (Zombie z : zombies) z.update(delta);

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        for (Zombie z : zombies)
            entityView.draw(zombieTexture, spriteBatch, z);

        for (Plant p : plants)
            entityView.draw(plantTexture, spriteBatch, p);

        spriteBatch.end();

        drawGrid();
    }

    private void drawGrid() {
        int cols = lawn.getCols();
        int rows = lawn.getRows();
        float gridWidth = viewport.getWorldWidth() * 0.80f;
        float gridHeight = viewport.getWorldHeight() * 0.72f;
        float tileW = gridWidth / cols;
        float tileH = gridHeight / rows;
        float gridX = ((viewport.getWorldWidth() - gridWidth) / 2f)+ 15;
        float gridY = ((viewport.getWorldHeight() - gridHeight) / 2f)-50;

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        // vertical
        for (int i = 0; i <= cols; i++) {
            float x = gridX + i * tileW;
            shapeRenderer.line(x, gridY, x, gridY + gridHeight);
        }

        // horizontal
        for (int j = 0; j <= rows; j++) {
            float y = gridY + j * tileH;
            shapeRenderer.line(gridX, y, gridX + gridWidth, y);
        }

        shapeRenderer.end();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        zombieTexture.dispose();
        plantTexture.dispose();
        shapeRenderer.dispose();
    }
}
