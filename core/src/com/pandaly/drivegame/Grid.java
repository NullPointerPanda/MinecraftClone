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

    }

    public void createGrid()
    {
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



    public void setBlock(Vector3 vekPos, Vector3 vekDirc){

        Vector3 tmpPos = new Vector3(vekPos);
        Vector3 tmpDir = new Vector3(vekDirc);

        for(int i = 1; i < terrainSize * 2; i++) {
            tmpDir.nor();
            tmpDir.scl(i);
            Vector3 line = new Vector3(tmpPos);

            line.add(tmpDir);
            line.scl(1 / pixelSize);

            int x = Math.round(line.x);
            int y = Math.round(line.y);
            int z = Math.round(line.z);

            if (terrain[x][y][z] != null) {

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

    public void breakBlock(Vector3 vekPos, Vector3 vekDirc){


        for(int i = 1; i < terrainSize * 2; i++) {

            if (getBlockInFront(vekPos,vekDirc,false,i) != null) {
                getBlockInFront(vekPos,vekDirc,false,i).dispose();
                getBlockInFront(vekPos,vekDirc,true,i);

                updateGrid();

                i = terrainSize * 2;
            }

        }
    }

    public Block getBlockInFront(Vector3 vekPos, Vector3 vekDirc, boolean destroy, int i)
    {
        Vector3 tmpPos = new Vector3(vekPos);
        Vector3 tmpDir = new Vector3(vekDirc);
        int x = 0;
        int y = 0;
        int z = 0;


            tmpDir.nor();
            tmpDir.scl(i);

            Vector3 line = new Vector3(tmpPos);
            line.add(tmpDir);
            line.scl(1 / pixelSize);

            x = Math.round(line.x);
            y = Math.round(line.y);
            z = Math.round(line.z);

            if (destroy == true)
            {
                terrain[x][y][z] = null;
            }

        return terrain[x][y][z];

    }

    public void checkForBlock(Camera camera)
    {

    }

    public Block getTerrain(int x,int y, int z)
    {
        return terrain[x][y][z];
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
