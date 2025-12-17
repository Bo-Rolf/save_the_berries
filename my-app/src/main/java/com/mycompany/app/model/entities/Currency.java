package com.mycompany.app.model.entities;



import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.math.Vector2;


public class Currency extends Entity{

    int currencyValue;
    double timeOut=0;
    int dissapearTimer=8; //tid tills den försvinner
    public Currency(EntityCfg cfg ,Vector2 position) {
        //Health används bara för takedamage när den ska dö (this.die kraschar ifall det sker under iteration)/Sixten
        super(cfg,position,new Rectangle2D.Double(position.x+10, position.y+10, 90, 90));
        this.currencyValue=50;
        
    }
    @Override
    public void update(double deltaTime) {
        this.timeOut+=deltaTime;
        if(this.timeOut>this.dissapearTimer){
            this.die();
        }
    }

    public int getCurrencyValue(){
        return this.currencyValue;
    }

}
