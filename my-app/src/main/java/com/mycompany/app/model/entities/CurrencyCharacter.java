package com.mycompany.app.model.entities;


import com.badlogic.gdx.math.Vector2;

public class CurrencyCharacter extends Character{
    private final double cooldownSeconds;
    private double timeSinceLastCurrency = 0;


    public CurrencyCharacter(EntityCfg cfg, Vector2 pos){
        super(cfg,pos);
        this.cooldownSeconds = cfg.cooldownSeconds;
    }

    @Override
    public void update(double deltaTime) { // den räknar tid ifrån förra skottet
        timeSinceLastCurrency +=deltaTime;
    }

    public boolean canSpawnCurrency() {
        return timeSinceLastCurrency >= cooldownSeconds;
    }

    
    public Currency spawnCurrency() {
        if (canSpawnCurrency()){
            timeSinceLastCurrency = 0;
            EntityCfg cfg = new EntityCfg();
            cfg.health=1;
            cfg.name="Currency";
            cfg.texture="currency.png";
            return new Currency(cfg,new Vector2(getPosition()));
        }
        return null;

    }
}
