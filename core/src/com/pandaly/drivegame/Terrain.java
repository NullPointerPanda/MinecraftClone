package com.pandaly.drivegame;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Terrain {

    private ArrayList<DirtBlock> terrain;

    public Terrain(int size){
        size = size * -1;
        terrain = new ArrayList<DirtBlock>();
        for(int i = 0; i > size; i-=1){
            for(int j = 0; j > size; j-=1){
                DirtBlock d = new DirtBlock();
                d.setPosition(i,0,j);
                terrain.add(d);
            }
        }
    }

    public ArrayList<DirtBlock> returnList(){
        return terrain;
    }
}
