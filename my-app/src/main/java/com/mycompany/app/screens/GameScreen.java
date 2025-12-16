package com.mycompany.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mycompany.app.MainGame;
import com.mycompany.app.model.Difficulty;
import com.mycompany.app.model.Game;
import com.mycompany.app.view.View;


public class GameScreen extends ScreenAdapter {

    private final MainGame mainGame;
    private final Game game;
    
    private View view;

    private Stage uiStage; 
    private Skin skin;
    //private Table pauseTable;
    private boolean paused = false;
    private TextButton pauseBtn;

    private final Difficulty selectedDifficulty;

    public GameScreen(MainGame mainGame, Game game, Difficulty selectedDifficulty) {
        this.mainGame = mainGame;
        this.game = game;
        this.selectedDifficulty = selectedDifficulty;
    }

 @Override
public void show() {
    game.setDifficulty(selectedDifficulty);
    view = new View(game);
    view.create();
    view.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    uiStage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(uiStage);
    skin = new Skin(Gdx.files.internal("uiskin.json"));

    pauseBtn = new TextButton("Pause", skin);
    pauseBtn.setPosition(Gdx.graphics.getWidth() - 110, Gdx.graphics.getHeight() - 100); 
    pauseBtn.setSize(100, 50);
    uiStage.addActor(pauseBtn);

    TextButton menuBtn = new TextButton("Main Menu", skin);
    menuBtn.setPosition(Gdx.graphics.getWidth() - 110, Gdx.graphics.getHeight() - 160); 
    menuBtn.setSize(100, 50);
    menuBtn.setVisible(false);
    uiStage.addActor(menuBtn);

    pauseBtn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            paused = !paused;
            pauseBtn.setText(paused ? "Resume" : "Pause");
            menuBtn.setVisible(paused);
        }
    });

    menuBtn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            mainGame.setScreen(new MainMenuScreen(mainGame));
        }
    });
}

    @Override
    public void render(float delta) {
        if (view == null) return;

        if (!paused) {
            view.render();
        }

        uiStage.act(delta);
        uiStage.draw();

        if (game.isGameOver()) {
            int finalTime = game.getFinalTime();
            mainGame.setScreen(new GameOverScreen(mainGame, finalTime));
            
        }

        if (game.isGoToMainMenu()) {
            game.setGoToMainMenu(false);
            mainGame.setScreen(new MainMenuScreen(mainGame));
            
        }
    }

    @Override
    public void resize(int width, int height) {
        if (view != null) view.resize(width, height);
        if (uiStage != null) uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        if (view != null) view.dispose();
        if (uiStage != null) uiStage.dispose();
        if (skin != null) skin.dispose();
    }
}
