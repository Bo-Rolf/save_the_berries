package com.mycompany.app.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mycompany.app.controller.Controller;
import com.mycompany.app.model.EntityManager;
import com.mycompany.app.model.Game;
import com.mycompany.app.model.Lawn;

public class View {

    private Game game;
    private Lawn lawn;
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private CharacterRenderer characterRenderer;
    private Texturemanager t = new Texturemanager();
    private CharacterSeedView characterSeedView;
    private ShapeRenderer shapeRenderer;
    private Controller controller;
    private Texture whiteTexture;
    private Texture backgroundTexture;   
    private BitmapFont font;

    public View(Game model) {
        //this.model=model;
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);
        t = new Texturemanager();
    }

    
    public void create() {
        font = new BitmapFont();
        this.game = new Game();
        this.lawn = game.getLawn();
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture("board.png");
        shapeRenderer = new ShapeRenderer();
        characterSeedView = new CharacterSeedView(this.t,this.whiteTexture,font);
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.WHITE);
        pm.fill();
        this.whiteTexture = new Texture(pm);
        pm.dispose();
        characterSeedView = new CharacterSeedView(t,this.whiteTexture,this.font);
        controller = new Controller(this.game, viewport);
        this.characterRenderer = new CharacterRenderer(t);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    public void render(){
        float delta = Gdx.graphics.getDeltaTime();
        float tileW = viewport.getWorldWidth() * 0.80f / lawn.getCols();
        float tileH = viewport.getWorldHeight() * 0.72f / lawn.getRows();
        float gridX = (viewport.getWorldWidth() - tileW * lawn.getCols()) / 2f + 15;
        float gridY = (viewport.getWorldHeight() - tileH * lawn.getRows()) / 2f - 50;

        EntityManager entitys = this.game.gEntityManager();
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        //drawGrid(gridX, gridY, tileW, tileH, lawn.getCols(), lawn.getRows());
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // draw the character seed icons in the top-left of the screen (pass viewport and selected index)

        
        characterSeedView.draw(spriteBatch, viewport, entitys.getcharacterSeeds(), 10, 10, 64, 8, game.getSelectedSeedIndex());

        // Grid



        controller.handleInput(gridX, gridY, tileW, tileH);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        
        
        characterRenderer.render(entitys, spriteBatch, tileW, tileH,gridX,gridY);
        font.draw(spriteBatch,"Currency:"+this.game.get_current_currency(),500,500);
        font.draw(spriteBatch, "Time: " + (int)game.getelapsedtime(), viewport.getWorldWidth() - 100, viewport.getWorldHeight() - 10);
        shapeRenderer.end();
        spriteBatch.end();

        this.controller.update(delta);
        // Draw grid
        
    }



    // public void pause() {}


    // public void resume() {}


    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        shapeRenderer.dispose();
        characterSeedView.dispose();
        font.dispose();
        t.Disposetextures();
    }
}
