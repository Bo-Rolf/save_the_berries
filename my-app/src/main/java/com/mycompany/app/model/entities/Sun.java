package com.mycompany.app.model.entities;



import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Rectangle2D;


public class Sun extends Entity{

    int sun_value;
    double time_out=0;
    int dissapear_timer=8; //tid tills den försvinner
    public Sun(entitycfg,Vector2 position) {
        //Health används bara för takedamage när den ska dö (this.die kraschar ifall det sker under iteration)/Sixten
        super(cfg,position,new Rectangle2D.Double((float)0,(float)0,(float)0,(float)0));

        Rectangle2D r = new Rectangle2D
        
        
        //super(cfg,position,new Rectangle2D.Double(position.x,position.y,20,20));
        //super(cfg, vector, new Rectangle2D.Double(vector.x-10, vector.y-10, 50, 50));
        this.sun_value=25;
        
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
