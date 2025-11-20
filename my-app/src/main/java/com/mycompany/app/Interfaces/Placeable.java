package com.mycompany.app.Interfaces;

public interface Placeable {
	void removeFromTile();
    void setTileRemovalListener(Runnable r);
}