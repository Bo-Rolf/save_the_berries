package com.mycompany.app.view;

import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
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
import com.mycompany.app.model.Game;
import com.mycompany.app.model.Lawn;
import com.mycompany.app.model.Tile;
import com.mycompany.app.model.entities.Plant;
import com.mycompany.app.model.entities.Projectile;
import com.mycompany.app.model.entities.Sun;
import com.mycompany.app.model.entities.Zombie;

public class View implements ApplicationListener {

    private Game game;
    private Lawn lawn;
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private float gameTime = 0;
    private Boolean gameOver = false;
    private json_reader js;
    private Texture backgroundTexture;
    private EntityView entityView;
    private ShapeRenderer shapeRenderer;
    private Controller controller;
    private Texture whiteTexture;

    private Texturemanager t = new Texturemanager();
    private PlantSeedView plantSeedView;

    private BitmapFont font;

    public View() {
        

        
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);
        t = new Texturemanager();
        new Lwjgl3Application(this, config);
        
    }

    @Override
    public void create() {
        font = new BitmapFont();
        this.js = new json_reader();
        this.game = new Game(js.config);
        this.lawn = game.getLawn();
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("board.png");

        entityView = new EntityView();
        shapeRenderer = new ShapeRenderer();
        
        plantSeedView = new PlantSeedView(this.t,this.whiteTexture,font);

        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.WHITE);
        pm.fill();
        this.whiteTexture = new Texture(pm);
        
        pm.dispose();
        plantSeedView = new PlantSeedView(t,this.whiteTexture,this.font);



        controller = new Controller(this.game, viewport, plantSeedView);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        
        //System.out.print(this.gameOver);
        
        float delta = Gdx.graphics.getDeltaTime();

        List<Zombie> zombies = this.game.getZombies();
        List<Projectile> projectiles = this.game.getProjectiles();
        List<Sun> suns = this.game.getSuns();

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        
        // draw the plant seed icons in the top-left of the screen (pass viewport and selected index)
        plantSeedView.draw(spriteBatch, viewport, this.game.getplantSeeds(), 10, 10, 64, 8, controller.getSelectedSeedIndex());

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

        for (Projectile p : projectiles) {
            Vector2 pPos = p.getPosition();
            entityView.draw(t.get_Texture(p.getTexturestring()), spriteBatch, p, pPos.x, pPos.y, 50, 50);
        }

        for (Sun s : suns) {
            Vector2 pPos = s.getPosition();
            //shapeRenderer.line((float)s.getHitBox().getMinX(),(float)s.getHitBox().getMinY(),(float)s.getHitBox().getMaxX(), (float)s.getHitBox().getMaxY());
            entityView.draw(t.get_Texture(s.getTexturestring()), spriteBatch, s, pPos.x, pPos.y, 50, 50);
        }
        font.draw(spriteBatch,"Sun:"+this.game.get_current_sun(),500,500);

        font.draw(spriteBatch, "Time: " + (int)gameTime, viewport.getWorldWidth() - 100, viewport.getWorldHeight() - 10);
        shapeRenderer.end();
        spriteBatch.end();

        //System.out.println(delta+ " "+viewport.getWorldWidth()+" " + gridY+" "+ tileH+" ");
        // Draw grid
        drawGrid(gridX, gridY, tileW, tileH, lawn.getCols(), lawn.getRows());

        if (!gameOver) {
            gameTime += delta;

            this.game.updateGameState(delta);
            
            if (checkZombie()) {
                gameOver = true;
            }
        }
    }

    private boolean checkZombie(){
        for(Zombie z : this.game.getZombies()){
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

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        shapeRenderer.dispose();
        plantSeedView.dispose();
        font.dispose();
    }
}
