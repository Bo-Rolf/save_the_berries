package com.mycompany.app.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.mycompany.app.model.entities.Plant;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mycompany.app.model.PlantSeed;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlantSeedView {
    private final Map<String, Texture> textures = new HashMap<>();
    private final Texture placeholder;
    private final Texturemanager t;
    public static Texture white_tex;
    public BitmapFont font;

    public PlantSeedView(Texturemanager texture_manager,Texture white,BitmapFont font) {
        // load known seed textures (adjust filenames to match your assets)
        this.white_tex = white;
        this.t = texture_manager;
        this.font = font;
        placeholder = new Texture("pea.png"); // fallback texture - add to assets or change name
    }



    // Draw the plant seed images in order starting at the top-left.
    // marginLeft/marginTop are pixels from the top-left corner; size is width/height per icon; spacing is pixels between icons
    public void draw(SpriteBatch batch, Viewport viewport, List<PlantSeed> plantSeedList, float marginLeft, float marginTop, float size, float spacing, int selectedIndex) {
        batch.end();
        float x = marginLeft;
        ShapeRenderer shape = new ShapeRenderer();
        float y = viewport.getWorldHeight() - marginTop - size; // top-left in world units
        
        for (PlantSeed e2: plantSeedList){
            batch.begin();
            Texture tex = this.t.get_Texture(e2.getTexturestring());
            int i = plantSeedList.indexOf(e2);
            float drawSize = (i == selectedIndex) ? size * 1.08f : size;
            float offset = (drawSize - size) / 2f;
            
                  // stop sprite batch before shape renderer
            batch.setColor(Color.GRAY);
            batch.draw(this.white_tex,x - offset, y - offset, drawSize, drawSize);
            batch.setColor(Color.WHITE);
            
            // Draw texture card
            batch.draw(tex, x - offset, y - offset, drawSize, drawSize);
            batch.end();
            // --- Cooldown Overlay ---
            if (e2.cooldown_left > 0) {

                double progress = e2.cooldown_left / e2.cooldown;  
                double overlayHeight = drawSize * progress; 
                batch.begin();
                batch.setColor(0, 0, 0, 0.55f);  // ändrar färgen till svart och halvtransparant
            
                // Draw rectangle at TOP of the card
                //batch.draw(tex, x - offset, y - offset, drawSize, drawSize);
                //batch.draw(this.white_tex, x - offset, y - offset, drawSize, drawSize);
                batch.draw(this.white_tex,(x - offset),(float)((y - offset) + (drawSize - overlayHeight)), // top aligned
                    drawSize,(float)overlayHeight);

                batch.setColor(Color.WHITE); // reseta färgen
                batch.end();
            }
            batch.begin();
            font.draw(batch,"Cost:"+e2.cost, x,y);
            batch.end();
            x += size + spacing;
            
            
        }
        batch.begin();
    }

    // Return index of seed clicked in world coords, or -1 if miss.
    public int getSeedIndexAt(float worldX, float worldY, Viewport viewport, List<PlantSeed> plantSeeds,
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
        for (Texture tex : textures.values()) {
            tex.dispose();
        }
        placeholder.dispose();
    }
}
