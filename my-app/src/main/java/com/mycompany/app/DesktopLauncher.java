package com.mycompany.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main (String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Save the Berries");
        config.setWindowedMode(800, 600);
        Lwjgl3Application application = new Lwjgl3Application(new MainGame(), config);
        System.out.print(application.getVersion());
    }
}
