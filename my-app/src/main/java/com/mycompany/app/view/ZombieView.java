package com.mycompany.app.view;

import com.mycompany.app.model.Zombie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ZombieView {

    private Zombie zombie;
    private Texture texture;

    public ZombieView() {
        this.texture = new Texture("zombie.png");
    }

    public void draw(SpriteBatch batch, Zombie zombie) {
        float x = 200; //zombie.getPosition().x;
        float y = 200; //zombie.getPosition().y;
        batch.draw(texture, x, y, 80, 100);
    }

    public void dispose() {
        texture.dispose();
    }
}
