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

    public void breakBlock(Vector3 vekPos, Vector3 vekDirc){
        Vector3 tmpPos = new Vector3(vekPos);
        Vector3 tmpDir = new Vector3(vekDirc);
        for(int i = 1; i < terrainSize; i++) {
            tmpDir.nor();
            tmpDir.scl(i);
            System.out.println(tmpDir);
            System.out.println(tmpPos);
            Vector3 line = tmpPos.add(tmpDir);
            line.scl(1 / pixelSize);
            System.out.println(line);

            int x = Math.round(line.x);
            int y = Math.round(line.y);
            int z = Math.round(line.z);

            if (terrain[x][y][z] != null) {
                terrain[x][y][z].dispose();
                terrain[x][y][z] = null;
                updateGrid();
            }
        }
    }




            /*} else if (type == Block.Type.DirtBlock) {
                terrain[lastX][lastY][lastZ] = new DirtBlock();
                updateGrid();
            }

            lastX = x;
            lastY = y;
            lastZ = z;*/




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
