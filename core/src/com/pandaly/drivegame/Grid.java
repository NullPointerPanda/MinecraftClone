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
    private float pixelSize = 5;
    private int lastX = 0;
    private int lastY = 0;
    private int lastZ = 0;
    private int count = 0;


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
        count = 0;
        for(int i = 0; i < terrainSize; i++){
            for(int j = 0; j < terrainSize; j++){
                for(int k = 0; k < terrainSize; k++){
                    float x = i * pixelSize;
                    float y = j * pixelSize;
                    float z = k * pixelSize;
                    if(terrain[i][j][k] != null){
                        terrain[i][j][k].setPosition(x,y,z);
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
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

        for(int i = 1; i < terrainSize * 2; i++) {
            tmpDir.nor();
            tmpDir.scl(i);
            //System.out.println("TmpDir: " + tmpDir);
            //System.out.println("TmpPos: " + tmpPos);
            Vector3 line = new Vector3(tmpPos);
            line.add(tmpDir);
            line.scl(1 / pixelSize);
            //System.out.println("Line: " + line);

            int x = Math.round(line.x);
            int y = Math.round(line.y);
            int z = Math.round(line.z);

            if (terrain[x][y][z] != null) {
               // terrain[x][y][z].dispose();
               // terrain[x][y][z].changeType(Block.Type.AirBlock);

                //terrain[x][y][z] = null;
                System.out.println(lastX + " " + lastY + " " + lastZ);
                System.out.println(x + " " + y + " " + z);
                terrain[lastX][lastY][lastZ] = new DirtBlock();
                terrain[lastX][lastY][lastZ].setPosition(x*5,y*5,z*5);
                updateGrid();

                i = terrainSize * 2;
            }
            lastX = x;
            lastY = y;
            lastZ = z;
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
