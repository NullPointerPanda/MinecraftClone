package com.pandaly.drivegame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Disposable;


public class Grid implements Disposable {

    private Block[][][] terrain;
    private int terrainSize = 25;
    private int pixelSize = 5;
    private int lastX = 0;
    private int lastY = 0;
    private int lastZ = 0;

    public Grid(){
        terrain = new Block[terrainSize][terrainSize][terrainSize];
        for(int i = 0; i < terrainSize; i++){
            for(int k = 0; k < terrainSize; k++){
                terrain[i][0][k] = new DirtBlock();
            }
        }
        updateGrid();
    }

    public void updateGrid() {
        for(int i = 0; i < terrainSize; i++){
            for(int j = 0; j < terrainSize; j++){
                for(int k = 0; k < terrainSize; k++){
                    float x = i * pixelSize;
                    float y = j * pixelSize;
                    float z = k * pixelSize;
                    if(terrain[i][j][k] != null){
                        terrain[i][j][k].setPosition(x,y,z);
                        System.out.println("BlockCo: "+terrain[i][j][k].getPosition());
                    }
                }
            }
        }
    }

    public void renderGrid(ModelBatch modelBatch, Environment environment){
        for(int i = 0; i < terrainSize; i++){
            for(int j = 0; j < terrainSize; j++){
                for(int k = 0; k < terrainSize; k++){
                    if(terrain[i][j][k] != null){
                    modelBatch.render(terrain[i][j][k].getInstance(),environment);
                    }
                }
            }
        }
    }

    public void setViewVektor(Vector3 vekPos, Vector3 vekDirc, Block.Type type){
        for(int i = 1; i > terrainSize * 4; i++){
            vekDirc.nor();
            vekDirc.scl(i);
            Vector3 line = vekPos.add(vekDirc);
            line.scl(1/pixelSize);
            int x = Math.round(line.x);
            int y = Math.round(line.y);
            int z = Math.round(line.z);

            if(x > (terrainSize - 1) || y > (terrainSize - 1) || z > (terrainSize - 1) || x < 0 || y < 0 || z < 0){
                if(terrain[x][y][z] != null){
                    if(type == null){
                        if(terrain[x][y][z] != null){
                            terrain[x][y][z].dispose();
                            terrain[x][y][z] = null;
                            updateGrid();
                        }
                    }else if(type == Block.Type.DirtBlock){
                        terrain[lastX][lastY][lastZ] = new DirtBlock();
                        updateGrid();
                    }

                }
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }


    @Override
    public void dispose() {
        for(int i = 0; i < terrainSize; i++){
            for(int j = 0; j < terrainSize; j++){
                for(int k = 0; k < terrainSize; k++){
                    if(terrain[i][j][k] != null) {
                        terrain[i][j][k].dispose();
                    }
                }
            }
        }
    }
}
