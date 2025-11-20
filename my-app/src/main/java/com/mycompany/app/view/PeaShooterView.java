package com.mycompany.app.view;

import com.mycompany.app.model.entities.PeaShooter;

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
        float x = peaShooter.getPosition().x;
        float y = peaShooter.getPosition().y;
        batch.draw(texture, x, y, 100, 100);
    }

    public void dispose() {
        texture.dispose();
    }

    public PeaShooter getPeaShooter() {
        return peaShooter;
    }
}
