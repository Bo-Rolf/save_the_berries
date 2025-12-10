package com.mycompany.app.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mycompany.app.controller.Controller;
import com.mycompany.app.controller.ZombieSpawner;
import com.mycompany.app.model.Difficulty;
import com.mycompany.app.model.Lawn;
import com.mycompany.app.model.Model;
import com.mycompany.app.model.Tile;
import com.mycompany.app.model.entities.Plant;
import com.mycompany.app.model.entities.Projectile;
import com.mycompany.app.model.entities.Sun;
import com.mycompany.app.model.entities.Zombie;

public class View {

    private Model model;
    private Lawn lawn;
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private float gameTime = 0;
    private Boolean gameOver = false;

    private Texture backgroundTexture;
    private EntityView entityView;
    private ShapeRenderer shapeRenderer;
    private Controller controller;
    private ZombieSpawner zombieSpawner;
    private Texture whiteTexture;
    private Difficulty difficulty;

    private Texturemanager t = new Texturemanager();
    private PlantSeedView plantSeedView;

    private BitmapFont font;

    public View(Model model) {
        this.model = model;
        this.lawn = model.getLawn();
        t = new Texturemanager();
        this.difficulty = model.getDifficulty();
    }

    
    public void create() {
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("board.png");

        entityView = new EntityView();
        shapeRenderer = new ShapeRenderer();
        zombieSpawner = new ZombieSpawner(model, difficulty);
        plantSeedView = new PlantSeedView(this.t,this.whiteTexture);

        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.WHITE);
        pm.fill();
        this.whiteTexture = new Texture(pm);
        pm.dispose();
        plantSeedView = new PlantSeedView(t,this.whiteTexture);



        font = new BitmapFont();
        controller = new Controller(model, viewport, plantSeedView);

        font = new BitmapFont();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        List<Zombie> zombies = model.game.getZombies();
        List<Projectile> projectiles = model.game.getProjectiles();
        List<Sun> suns = model.game.getSuns();

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        
        
        plantSeedView.draw(spriteBatch, viewport, model.game.getplantSeeds(), 10, 10, 64, 8, controller.getSelectedSeedIndex());

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
                }
            }
        }

        // Draw zombies
        for (Zombie z : zombies) {
            Vector2 pos = z.getPosition();
            entityView.draw(t.get_Texture(z.getTexturestring()), spriteBatch, z, pos.x, pos.y, tileW, tileH);
        }

        for (Projectile p : projectiles) {
            Vector2 pPos = p.getPosition();
            entityView.draw(t.get_Texture(p.getTexturestring()), spriteBatch, p, pPos.x, pPos.y, 50, 50);
        }

        for (Sun s : suns) {
            Vector2 pPos = s.getPosition();
            entityView.draw(t.get_Texture(s.getTexturestring()), spriteBatch, s, pPos.x, pPos.y, 50, 50);
        }
        font.draw(spriteBatch,"Sun:"+model.game.get_current_sun(),500,500);

        font.draw(spriteBatch, "Time: " + (int)gameTime, viewport.getWorldWidth() - 100, viewport.getWorldHeight() - 10);
        shapeRenderer.end();
        spriteBatch.end();

        // Draw grid
        drawGrid(gridX, gridY, tileW, tileH, lawn.getCols(), lawn.getRows());

        if (!gameOver) {
            gameTime += delta;

            model.game.updateGameState(delta);
            zombieSpawner.update(delta, viewport.getWorldWidth(), gridY, tileH, lawn.getRows());
            if (checkZombie()) {
                gameOver = true;
                model.setGameOver(true);
                model.setGameOver((int) gameTime);
            }
        }
    }

    private boolean checkZombie(){
        for(Zombie z : model.game.getZombies()){
            if(z.getPosition().x <= 0){
                return true;
            }
        }
        return false;
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


    public void pause() {}


    public void resume() {}


    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        shapeRenderer.dispose();
        plantSeedView.dispose();
        font.dispose();
    }
}
