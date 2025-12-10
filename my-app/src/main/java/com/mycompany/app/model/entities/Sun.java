package com.mycompany.app.model.entities;



import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.math.Vector2;


public class Sun extends Entity{

    int sun_value;
    double time_out=0;
    int dissapear_timer=8; //tid tills den försvinner
    public Sun(EntityCfg cfg ,Vector2 position) {
        //Health används bara för takedamage när den ska dö (this.die kraschar ifall det sker under iteration)/Sixten
        super(cfg,position,new Rectangle2D.Double(position.x+10, position.y+10, 90, 90));
        this.sun_value=50;
        
    }
    @Override
    public void update(double deltaTime) {
        this.time_out+=deltaTime;
        if(this.time_out>this.dissapear_timer){
            this.takeDamage(1000);
        }
    }

    public int get_sun_value(){
        return this.sun_value;
    }



    
}
