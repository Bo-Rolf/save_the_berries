package com.mycompany.app.view;

import com.mycompany.app.model.*;
import com.mycompany.app.model.entities.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mycompany.app.controller.Controller;

import java.util.List;
import java.util.Vector;

public class View implements ApplicationListener {

    private Model model;
    private Lawn lawn;
    private SpriteBatch spriteBatch;
    private Viewport viewport;

    private Texture backgroundTexture;
    private Texture zombieTexture;
    private Texture plantTexture;
    private Texture peaTexture;

    private EntityView entityView;
    private ShapeRenderer shapeRenderer;
    private Controller controller;


    public View(Model model) {
        this.model = model;
        this.lawn = model.getLawn();
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);

        new Lwjgl3Application(this, config);
    }

    @Override
    public void create() {
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("board.png");
        zombieTexture = new Texture("Zombie.png");
        plantTexture = new Texture("pea_shooter.png");
        peaTexture = new Texture("pea.png");

        entityView = new EntityView();
        shapeRenderer = new ShapeRenderer();
        controller = new Controller(model, viewport);
        
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }



    @Override
    public void render() {
        
        float delta = Gdx.graphics.getDeltaTime();

        model.game.updateGameState(delta);
        List<Zombie> zombies = model.game.getZombies();
        List<Projectile> projectiles = model.game.getProjectiles();
        

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Grid
        float tileW = viewport.getWorldWidth() * 0.80f / lawn.getCols();
        float tileH = viewport.getWorldHeight() * 0.72f / lawn.getRows();
        float gridX = (viewport.getWorldWidth() - tileW * lawn.getCols()) / 2f + 15;
        float gridY = (viewport.getWorldHeight() - tileH * lawn.getRows()) / 2f - 50;

        controller.handleInput(gridX, gridY, tileW, tileH);

        // Draw plants
        for (int r = 0; r < lawn.getRows(); r++) {
            for (int c = 0; c < lawn.getCols(); c++) {
                Tile tile = lawn.getTile(r, c);
                if (tile.getPlaceable() instanceof Plant plant) {
                    float x = gridX + c * tileW;
                    float y = gridY + r * tileH;
                    entityView.draw(plantTexture, spriteBatch, plant, x, y, tileW, tileH);
                }
            }
        }

        // Draw zombies
        for (Zombie z : zombies) {
            Vector2 pos = z.getPosition();
            entityView.draw(zombieTexture, spriteBatch, z, pos.x, pos.y, tileW, tileH);
        }
        for(Projectile p : projectiles){
            Vector2 pPos = p.getPosition();
            entityView.draw(peaTexture,spriteBatch,p,pPos.x,pPos.y,50,50);
        }



        spriteBatch.end();

        // Draw grid
        drawGrid(gridX, gridY, tileW, tileH, lawn.getCols(), lawn.getRows());
    }



    
    private void drawGrid(float gridX, float gridY, float tileW, float tileH, int cols, int rows) {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        // Vertical lines
        for (int i = 0; i <= cols; i++) {
            float x = gridX + i * tileW;
            shapeRenderer.line(x, gridY, x, gridY + rows * tileH);
        }

        // Horizontal lines
        for (int j = 0; j <= rows; j++) {
            float y = gridY + j * tileH;
            shapeRenderer.line(gridX, y, gridX + cols * tileW, y);
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
        peaTexture.dispose();
        shapeRenderer.dispose();
        
    }
}
