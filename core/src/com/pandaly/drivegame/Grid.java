//==========================================
//	Title:  FlatLanders
//	Author: Fabian Joßberger | Luke Behrsing
//	Date:   15 Jan 2020
//==========================================

package com.pandaly.drivegame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntArray;

import java.util.ArrayList;


public class Grid implements Disposable {

    private Block[][][] terrain;
    private int terrainSize = 25;
    private int renderDistance = 30;
    private float pixelSize = 5;
    private int lastX = 0;
    private int lastY = 0;
    private int lastZ = 0;
    private int x = 0;
    private int y = 0;
    private int z = 0;


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

    public void renderGrid(ModelBatch modelBatch, Environment environment, Camera camera){

        for(int i = 0/*-5*/; i < terrainSize/*renderDistance/2*/; i++){
            for(int j = 0; j < terrainSize; j++){
                for(int k = 0/*-5*/; k < terrainSize/*renderDistance/2*/; k++){
                    //if(terrain[Math.round(camera.position.x) / (int) pixelSize + i][j][Math.round(camera.position.z) / (int) pixelSize + k] != null){
                    //modelBatch.render(terrain[Math.round(camera.position.x) / (int) pixelSize + i][j][Math.round(camera.position.z) / (int) pixelSize + k].getInstance(),environment);
                    if (terrain[i][j][k] != null){
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

    public boolean hittingBlock(Vector3 vekPos, Vector3 tmpVec)
    {
        Vector3 point = new Vector3(vekPos);

        x = Math.round(point.x)/ 5;

        //int y = Math.round(point.y);
        z = Math.round(point.z) / 5;
        System.out.println(x - 1+ " " + z);


        if(terrain[x - 1][1][z] != null && vekPos.set(tmpVec).x <= terrain[x-1][1][z].getPosition().x + 5)
        {
            if (vekPos.set(tmpVec).z <= terrain[x-1][1][z].getPosition().z-5)
            {
                return false;
                    //System.out.println(terrain[x-1][1][z].getPosition().z-2.5f);
                    //System.out.println(vekPos.set(tmpVec).z);
            }
            else
            {
                return true;
            }
        }
        else if (terrain[x+1][1][z] != null && vekPos.set(tmpVec).x <= terrain[x+1][1][z].getPosition().x + 5)
        {
            return true;
        }
       // else if (terrain[x][1][z - 1] != null && vekPos.set(tmpVec).z <= terrain[x-1][1][z].getPosition().z + 5)
       // {
       //     return true;
       // }
       // else if (terrain[x][1][z + 1] != null && vekPos.set(tmpVec).z <= terrain[x-1][1][z].getPosition().z + 5)
       // {
       //     return true;
       // }
        else
        {
            return false;
        }
    }

    public Vector3 checkNearby(Camera camera, Vector3 tmpVec)
    {
            tmpVec.z += (0.5f * camera.direction.z);

        return tmpVec;
    }

    public Block getTerrain(int x,int y, int z)
    {
        return terrain[x][y][z];
    }

    public Block[][][] getTerrain()
    {
        return terrain;
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

    public IntArray checkForBlock(Vector3 vekPos)
    {
        Vector3 tmpPos = new Vector3(vekPos);
        Vector3 direction1 = new Vector3(1.0f,2.0f,0.0f);
        Vector3 direction2 = new Vector3(1.0f,1.0f,0.0f);
        int x1 = 0;
        int y1 = 0;
        int z1 = 0;
        int x2 = 0;
        int y2 = 0;
        int z2 = 0;

        IntArray coordinates = new IntArray();

        for (int i = 0; i < 4; i++)
        {
            if (i == 1)
            {
                direction1.x = 0.0f;
                direction1.z = 1.0f;
                direction2.x = 0.0f;
                direction2.z = 1.0f;
            }
            else if (i == 2)
            {
                direction1.x = 0.0f;
                direction1.z = -1.0f;
                direction2.x = 0.0f;
                direction2.z = -1.0f;
            }
            else if (i == 3)
            {
                direction1.x = -1.0f;
                direction1.z = 0.0f;
                direction2.x = -1.0f;
                direction2.z = 0.0f;
            }
            direction1.nor();
            direction1.scl(5);

            direction2.nor();
            direction2.scl(5);

            Vector3 lineUp = new Vector3(tmpPos);
            lineUp.add(direction1);
            lineUp.scl(1 / pixelSize);

            Vector3 lineDown = new Vector3(tmpPos);
            lineDown.add(direction1);
            lineDown.scl(1 / pixelSize);

            if (i == 0)
            {
                x1 = Math.round(lineUp.x)+1;
                y1 = 2;
                z1 = Math.round(lineUp.z);

                x2 = Math.round(lineDown.x) + 1;
                y2 = 1;
                z2 = Math.round(lineDown.z);

            }
            else if (i == 1)
            {
                x1 = Math.round(lineUp.x);
                y1 = 2;
                z1 = Math.round(lineUp.z) + 1;

                x2 = Math.round(lineDown.x);
                y2 = 1;
                z2 = Math.round(lineDown.z) + 1;
            }
            else if (i == 2)
            {
                x1 = Math.round(lineUp.x);
                y1 = 2;
                z1 = Math.round(lineUp.z) - 1;

                x2 = Math.round(lineDown.x);
                y2 = 1;
                z2 = Math.round(lineDown.z) - 1;
            }
            else if (i == 3)
            {
                x1 = Math.round(lineUp.x) - 1;
                y1 = 2;
                z1 = Math.round(lineUp.z);

                x2 = Math.round(lineDown.x) - 1;
                y2 = 1;
                z2 = Math.round(lineDown.z);
            }



            coordinates.add(x1,y1,z1);
            coordinates.add(x2,y2,z2);

        }
        return coordinates;
    }
}
