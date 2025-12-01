package com.mycompany.app.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mycompany.app.model.entities.Entity;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PlantSeedView {
    private final Map<String, Texture> textures = new HashMap<>();
    private final Texture placeholder;

    public PlantSeedView() {
        // load known seed textures (adjust filenames to match your assets)
        textures.put("PeaShooter", new Texture("pea_shooter.png"));
        textures.put("Sunflower", new Texture("sunflower.png"));
        textures.put("Wallnut", new Texture("wallnut.png"));
        placeholder = new Texture("pea.png"); // fallback texture - add to assets or change name
    }

    public void draw(Texture texture, SpriteBatch batch, Entity entity) {
        Vector2 pos = new Vector2(0,0);
        batch.draw(texture, pos.x, pos.y);
    }

    // Draw the plant seed images in order starting at the top-left.
    // marginLeft/marginTop are pixels from the top-left corner; size is width/height per icon; spacing is pixels between icons
    public void draw(SpriteBatch batch, Viewport viewport, List<String> plantSeeds, float marginLeft, float marginTop, float size, float spacing, int selectedIndex) {
        float x = marginLeft;
        float y = viewport.getWorldHeight() - marginTop - size; // top-left in world units
        for (String seed : plantSeeds) {
            Texture tex = textures.getOrDefault(seed, placeholder);
            // Slightly enlarge the selected icon so user sees the selection
            int i = plantSeeds.indexOf(seed);
            float drawSize = (i == selectedIndex) ? size * 1.08f : size;
            float offset = (drawSize - size) / 2f;
            batch.draw(tex, x - offset, y - offset, drawSize, drawSize);
            x += size + spacing;
        }
    }

    // Return index of seed clicked in world coords, or -1 if miss.
    public int getSeedIndexAt(float worldX, float worldY, Viewport viewport, List<String> plantSeeds,
                              float marginLeft, float marginTop, float size, float spacing) {
        float x = marginLeft;
        float y = viewport.getWorldHeight() - marginTop - size;
        for (int i = 0; i < plantSeeds.size(); i++) {
            if (worldX >= x && worldX <= x + size && worldY >= y && worldY <= y + size) {
                return i;
            }
            x += size + spacing;
        }
        return -1;
    }

    public void dispose() {
        for (Texture t : textures.values()) {
            t.dispose();
        }
        placeholder.dispose();
    }
}
