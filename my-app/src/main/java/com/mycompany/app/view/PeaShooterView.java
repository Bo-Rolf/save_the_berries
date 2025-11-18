package com.mycompany.app.view;

import com.mycompany.app.model.PeaShooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PeaShooterView {

    private PeaShooter peaShooter;
    private Texture texture;

    public PeaShooterView() {
        this.peaShooter = peaShooter;
        this.texture = new Texture("pea_shooter.png");
    }

    public void draw(SpriteBatch batch, PeaShooter peaShooter) {
        float x = 100;   //(float) peaShooter.getPosition().x;
        float y = 100;   //(float) peaShooter.getPosition().y;
        batch.draw(texture, x, y, 100, 100);
    }

    public void dispose() {
        texture.dispose();
    }

    public PeaShooter getPeaShooter() {
        return peaShooter;
    }
}
