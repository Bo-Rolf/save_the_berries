package com.mycompany.app;

import com.badlogic.gdx.Game;
import com.mycompany.app.screens.MainMenuScreen;

public class MainGame extends Game {

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}
