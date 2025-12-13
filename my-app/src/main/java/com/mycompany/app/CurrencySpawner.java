//Sak som spawnar sol
package com.mycompany.app;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.mycompany.app.model.entities.Currency;
import com.mycompany.app.model.entities.EntityCfg;



public class CurrencySpawner {
    private final double currencyCooldown = 15;
    private double timeSinceLastCurrency = 0;
    private final Random r = new Random();

    public void Currencyspawner() {

    }
    public void update(double deltaTime) { //Räknar tid från förra solen
        timeSinceLastCurrency +=deltaTime;
    }

    public boolean canSpawnCurrency() {
        return timeSinceLastCurrency >= currencyCooldown;
    }

    
    public Currency spawnCurrency() {
        if (canSpawnCurrency()){
            timeSinceLastCurrency = 0;
            Vector2 currencypos = new Vector2(this.r.nextInt(100,700),this.r.nextInt(100,500));
            EntityCfg cfg = new EntityCfg();
            cfg.health=1;
            cfg.name="Currency";
            cfg.texture="currency.png";
            return new Currency(cfg,currencypos);
        }
        return null;

    }
}
