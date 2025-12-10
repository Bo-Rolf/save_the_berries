package com.mycompany.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mycompany.app.MainGame;

public class GameOverScreen extends ScreenAdapter {

    private final MainGame game;
    private final int score;

    private Stage stage;
    private Skin skin;

    public GameOverScreen(MainGame game, int score) {
        this.game = game;
        this.score = score;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label lost = new Label("GAME OVER!", skin);
        Label scoreLabel = new Label("Your Time: " + score + "s", skin);

        TextButton menuBtn = new TextButton("Main Menu", skin);

        table.add(lost).pad(20).row();
        table.add(scoreLabel).pad(20).row();
        table.add(menuBtn).width(200).pad(10).row();


    menuBtn.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
    @Override
    public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        game.setScreen(new MainMenuScreen(game));
    }
});
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
