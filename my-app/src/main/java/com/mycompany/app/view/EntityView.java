package com.mycompany.app.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.entities.Entity;

public class EntityView {


    public static void draw(Texture texture, SpriteBatch batch, Entity entity) {
        Vector2 pos = entity.getPosition();
        batch.draw(texture, pos.x, pos.y);
    }

    public static void draw(Texture texture, SpriteBatch batch, float x, float y, float width, float height) {
        batch.draw(texture, x, y, width, height);
    }

}

