package com.mycompany.app.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.entities.Entity;

public class EntityView {

    private Texture texture;

    public EntityView() {
        //this.texture = new Texture("pea_shooter.png");
    }

    public void draw(Texture texture, SpriteBatch batch, Entity entity) {
        Vector2 pos = entity.getPosition();
        batch.draw(texture, pos.x, pos.y);
    }

    public void draw(Texture texture, SpriteBatch batch, Entity entity, float x, float y, float width, float height) {
        batch.draw(texture, x, y, width, height);
    }


    public void dispose() {
        texture.dispose();
    }



}

