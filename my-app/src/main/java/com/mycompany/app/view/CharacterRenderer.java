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
    private final Texturemanager t;
    List<String> tiletexs;
    
    public CharacterRenderer(Texturemanager t){
        this.t = t;
        tiletexs = new ArrayList<>();
        tiletexs.add("tile_dark.png");
        tiletexs.add("tile_light.png");
    }

    public void render(EntityManager entities,SpriteBatch batch, float tileW, float tileH,float gridX,float gridY) {
        List<Enemy> enemies = entities.getEnemies();
        List<Projectile> projectiles =entities.getProjectiles();
        List<Currency> currencys = entities.getCurrencys();
        Lawn lawn = entities.getLawn();
        
        for (int r = 0; r < lawn.getRows(); r++) {
            for (int c = 0; c < lawn.getCols(); c++) {
                Tile tile = lawn.getTile(r, c);
                float x = gridX + c * tileW;
                float y = gridY + r * tileH;
                EntityView.draw(t.getTexture(tiletexs.get(((r+1)+(c+1))%2)), batch, x,y,tileW,tileH);
                if (tile.getPlaceable() instanceof Character character) {
                    EntityView.draw(t.getTexture(character.getTexturestring()), batch, x, y, tileW, tileH);
                }
            }
        }
        for (Enemy z : enemies) {
            Vector2 pos = z.getPosition();
            EntityView.draw(this.t.getTexture(z.getTexturestring()), batch, pos.x,pos.y,tileW,tileH);
        }
        for (Projectile p : projectiles) {
            Vector2 pPos = p.getPosition();
            EntityView.draw(t.getTexture(p.getTexturestring()), batch, pPos.x, pPos.y, 50, 50);
        }
        for (Currency s : currencys) {
            Vector2 pPos = s.getPosition();
            //shapeRenderer.line((float)s.getHitBox().getMinX(),(float)s.getHitBox().getMinY(),(float)s.getHitBox().getMaxX(), (float)s.getHitBox().getMaxY());
            EntityView.draw(t.getTexture(s.getTexturestring()), batch, pPos.x, pPos.y, 50, 50);
        }


    }
}