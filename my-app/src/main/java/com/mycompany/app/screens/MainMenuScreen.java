package com.mycompany.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mycompany.app.MainGame;
import com.mycompany.app.model.Difficulty;
import com.mycompany.app.model.Model;


public class MainMenuScreen extends ScreenAdapter {

    private final MainGame game;
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private SpriteBatch batch;


    public MainMenuScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        backgroundTexture = new Texture("menu.png");

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Save the berries", skin);

        TextButton easyBtn = new TextButton("Easy", skin);
        TextButton normalBtn = new TextButton("Normal", skin);
        TextButton hardBtn = new TextButton("Hard", skin);
        TextButton quitBtn = new TextButton("Quit", skin);

        table.add(title).padBottom(40).row();
        table.add(easyBtn).width(200).pad(10).row();
        table.add(normalBtn).width(200).pad(10).row();
        table.add(hardBtn).width(200).pad(10).row();
        table.add(quitBtn).width(200).pad(10).row();

easyBtn.addListener(new ClickListener() {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        startGame(Difficulty.EASY);
    }
});

normalBtn.addListener(new ClickListener() {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        startGame(Difficulty.NORMAL);
    }
});

hardBtn.addListener(new ClickListener() {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        startGame(Difficulty.HARD);
    }
});

quitBtn.addListener(new ClickListener() {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
    }
});
    }

    private void startGame(Difficulty diff) {
        Model model = new Model();
        if(stage != null) stage.dispose();
        game.setScreen(new GameScreen(game, model, diff));
                
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
