package com.mycompany.app.view;

import com.mycompany.app.model.entities.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EntityView {

    private Texture texture;

    public EntityView() {
        this.texture = new Texture("pea_shooter.png");
    }

    public void draw(Texture texture,SpriteBatch batch, Entity e) {
        float x = e.getPosition().x;
        float y = e.getPosition().y;
        batch.draw(texture, x, y, 100, 100);
    }

    public void dispose() {
        texture.dispose();
    }


}

