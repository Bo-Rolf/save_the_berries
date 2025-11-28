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

import com.badlogic.gdx.utils.PerformanceCounter;

public class View implements ApplicationListener {

    private Model model;
    private Lawn lawn;
    private SpriteBatch spriteBatch;
    private Viewport viewport;

    private Texture backgroundTexture;
    private EntityView entityView;
    private ShapeRenderer shapeRenderer;
    private Controller controller;

    private Texturemanager t = new Texturemanager();






    public View(Model model) {
        this.model = model;
        this.lawn = model.getLawn();
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);
        t = new Texturemanager();
        new Lwjgl3Application(this, config);
    }

    @Override
    public void create() {
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("board.png");

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
        List<Sun> suns = model.game.getSuns();
        

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


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        // Draw plants
        for (int r = 0; r < lawn.getRows(); r++) {
            for (int c = 0; c < lawn.getCols(); c++) {
                Tile tile = lawn.getTile(r, c);
                if (tile.getPlaceable() instanceof Plant plant) {
                    float x = gridX + c * tileW;
                    float y = gridY + r * tileH;
                    entityView.draw(t.get_Texture(plant.getTexturestring()), spriteBatch, plant, x, y, tileW, tileH);
                    
                    //shapeRenderer.line((float)plant.getHitBox().getMinX(),(float)plant.getHitBox().getMinY(),(float)plant.getHitBox().getMaxX(),(float)plant.getHitBox().getMaxY());
                }
            }
        }
        

        // Draw zombies
        for (Zombie z : zombies) {
            Vector2 pos = z.getPosition();
            entityView.draw(t.get_Texture(z.getTexturestring()), spriteBatch, z, pos.x, pos.y, tileW, tileH);
        }

        
        for(Projectile p : projectiles){
            Vector2 pPos = p.getPosition();
            entityView.draw(t.get_Texture(p.getTexturestring()),spriteBatch,p,pPos.x,pPos.y,50,50);
        }

        for(Sun s :suns){
            Vector2 pPos = s.getPosition();
            //shapeRenderer.line((float)s.getHitBox().getMinX(),(float)s.getHitBox().getMinY(),(float)s.getHitBox().getMaxX(), (float)s.getHitBox().getMaxY());
            entityView.draw(t.get_Texture(s.getTexturestring()),spriteBatch,s,pPos.x,pPos.y,50,50);
        }

        shapeRenderer.end();
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

        //Horizontal lines
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
        shapeRenderer.dispose();
        
    }
}
