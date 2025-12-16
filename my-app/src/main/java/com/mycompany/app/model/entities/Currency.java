package com.mycompany.app.model.entities;



import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.math.Vector2;


public class Currency extends Entity{

    int currency_value;
    double time_out=0;
    int dissapear_timer=8; //tid tills den försvinner
    public Currency(EntityCfg cfg ,Vector2 position) {
        //Health används bara för takedamage när den ska dö (this.die kraschar ifall det sker under iteration)/Sixten
        super(cfg,position,new Rectangle2D.Double(position.x+10, position.y+10, 90, 90));
        this.currency_value=50;
        
    }
    @Override
    public void update(double deltaTime) {
        this.time_out+=deltaTime;
        if(this.time_out>this.dissapear_timer){
            this.die();
        }
    }

    public int get_currency_value(){
        return this.currency_value;
    }

}
