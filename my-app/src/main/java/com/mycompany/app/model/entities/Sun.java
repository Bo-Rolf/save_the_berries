package com.mycompany.app.model.entities;



import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Rectangle2D;


public class Sun extends Entity{

    int sun_value;
    double time_out=0;
    int dissapear_timer=8; //tid tills den försvinner
    public Sun(Vector2 vector,int value) {
        //Health används bara för takedamage när den ska dö (this.die kraschar ifall det sker under iteration)/Sixten
        super(1, "Sun", vector, new Rectangle2D.Double(vector.x-10, vector.y-10, 50, 50),"sun.png");
        this.sun_value=value;
        
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
