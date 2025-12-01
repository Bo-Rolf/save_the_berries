package com.mycompany.app.model;

public class PlantSeedList {
    int pointer;
    int size;

    public  String[] plantSeeds = {
            "PeaShooter",
            "Sunflower",
            "Wallnut"
    };

    public String getplantwewanttoplant() {
        return plantSeeds[pointer];
    }
}
