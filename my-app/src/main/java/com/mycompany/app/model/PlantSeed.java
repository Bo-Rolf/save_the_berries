package com.mycompany.app.model;
import java.util.Vector;
import com.badlogic.gdx.math.Vector2;

import com.mycompany.app.model.entities.PeaShooter;
import com.mycompany.app.model.entities.Plant;

public class PlantSeed {
    public Class<? extends Plant> type;
    public String t;
    public int cost;
    public double cooldown;
    public double cooldown_left;

    

    public PlantSeed(Class<? extends Plant> p){
        this.type=p;
        try {
           //ngl skit lösning men det funkar /sixten
           
           //När vi skapar ett nytt plantseed från typen har vi tillgång vilken klass det är
           //Vi behöver också ha tillgång till kostnad, textur och cooldown, 
           // (något som är gemensamt för varje subtyp)(tex wallnut kommer alltid ha 50 cost)
           //Jag lyckades inte få en statisk funktion som returnarar värdena att fungera,
           //Så för att få värdena skapar jag en ny instans av objektet och hämtar de gemensamma värdena



        

           Class[] cArg = new Class[3]; //Our constructor has 3 arguments
           cArg[0]= Vector2.class;
           cArg[1] = int.class; 
           cArg[2] = int.class;
           Plant a = p.getDeclaredConstructor(cArg).newInstance(new Vector2(0,0),0,0); //Skapar en ny planta
           this.cost=a.getSunCost();
           this.t=a.getTexturestring();
           this.cooldown=a.getCooldown();
        } catch (Exception e) {
            //Detta borde aldrig hända men det är för ifall det ba händer.
            System.out.print(e);
            this.t ="Sunflower.png";
            this.type = PeaShooter.class;
            this.cost=50;
            this.cooldown=15;

        }
    }
    
    public void update(double deltaTime){
        
        cooldown_left-=deltaTime;
        if(cooldown_left<0){
            cooldown_left=0;
        }

    }
    
    public boolean ready_to_place(){
        return cooldown_left<=0;
    }
    public void try_place(){
        if(ready_to_place()){
            this.cooldown_left=this.cooldown;
        }
    }


    public String getTexturestring(){
        return this.t;
    }
    
}
