package com.mycompany.app.view;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.EntityManager;
import com.mycompany.app.model.Lawn;
import com.mycompany.app.model.Tile;
import com.mycompany.app.model.entities.Character;
import com.mycompany.app.model.entities.Currency;
import com.mycompany.app.model.entities.Enemy;
import com.mycompany.app.model.entities.Projectile;


public class CharacterRenderer {
    private final EntityView entityView;
    private final Texturemanager t;
    List<String> tiletexs;
    
    public CharacterRenderer(EntityView view, Texturemanager t){
        this.entityView = view;
        this.t = t;
        tiletexs = new ArrayList<>();
        tiletexs.add("tile_dark.png");
        tiletexs.add("tile_light.png");
    }

    public void render(EntityManager entitys,SpriteBatch batch, float tileW, float tileH,float gridX,float gridY) {
        List<Enemy> enemys = entitys.getEnemys();
        List<Projectile> projectiles =entitys.getProjectiles();
        List<Currency> currencys = entitys.getCurrencys();
        Lawn lawn = entitys.getLawn();
        
        for (int r = 0; r < lawn.getRows(); r++) {
            for (int c = 0; c < lawn.getCols(); c++) {
                Tile tile = lawn.getTile(r, c);
                float x = gridX + c * tileW;
                float y = gridY + r * tileH;
                entityView.draw(t.get_Texture(tiletexs.get(((r+1)+(c+1))%2)), batch, x,y,tileW,tileH);
                if (tile.getPlaceable() instanceof Character character) {
                    entityView.draw(t.get_Texture(character.getTexturestring()), batch, x, y, tileW, tileH);
                }
            }
        }
        for (Enemy z : enemys) {
            Vector2 pos = z.getPosition();
            entityView.draw(this.t.get_Texture(z.getTexturestring()), batch, pos.x,pos.y,tileW,tileH);
        }
        for (Projectile p : projectiles) {
            Vector2 pPos = p.getPosition();
            entityView.draw(t.get_Texture(p.getTexturestring()), batch, pPos.x, pPos.y, 50, 50);
        }
        for (Currency s : currencys) {
            Vector2 pPos = s.getPosition();
            //shapeRenderer.line((float)s.getHitBox().getMinX(),(float)s.getHitBox().getMinY(),(float)s.getHitBox().getMaxX(), (float)s.getHitBox().getMaxY());
            entityView.draw(t.get_Texture(s.getTexturestring()), batch, pPos.x, pPos.y, 50, 50);
        }


    }
}