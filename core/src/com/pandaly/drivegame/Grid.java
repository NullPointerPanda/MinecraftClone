//==========================================
//	Title:  FlatLanders
//	Author: Fabian Joßberger | Luke Behrsing
//	Date:   15 Jan 2020
//==========================================

package com.pandaly.drivegame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;


public class Grid implements Disposable {

    private Block[][][] terrain;
    private int terrainSize = 35;
    private int renderDistance = 20;
    private float pixelSize = 5;
    private int lastX = 0;
    private int lastY = 0;
    private int lastZ = 0;
    private DirtBlock pufferDirt = new DirtBlock();
    private StoneBlock pufferStone = new StoneBlock();
    private WoodenBlock pufferWood = new WoodenBlock();

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
        //Boden
        for (int i = 5; i < 10; i++)
        {
            for (int j = 5; j<10;j++)
            {
                terrain[i][0][j] = new WoodenBlock();
            }

        }
        //Decke
        for (int i = 5; i < 10; i++)
        {
            for (int j = 5; j<10;j++)
            {
                terrain[i][4][j] = new WoodenBlock();
            }

        }
        //RECHTE WAND
        for (int i = 5; i < 10; i++)
        {

                for (int k = 0; k < 4; k++)
                {
                    terrain[i][k][4] = new WoodenBlock();
                    terrain[7][2][4] = null;
                }
        }
        // HINTERE WAND
        for (int i = 5; i < 10; i++)
        {

            for (int k = 0; k < 4; k++)
            {
                terrain[4][k][i] = new WoodenBlock();
                terrain[4][2][7] = null;
            }
        }
        // LINKE WAND
        for (int i = 5; i < 10; i++)
        {

            for (int k = 0; k < 4; k++)
            {
                terrain[i][k][10] = new WoodenBlock();
                terrain[7][2][10] = null;
            }
        }
        // VORDERE WAND
        for (int i = 5; i < 10; i++)
        {

            for (int k = 0; k < 4; k++)
            {
                terrain[10][k][i] = new WoodenBlock();
                terrain[10][1][7] = null;
                terrain[10][2][7] = null;
            }
        }
        terrain[19][1][20] = new StoneBlock();
        terrain[20][1][20] = new StoneBlock();
        terrain[21][1][20] = new StoneBlock();
        terrain[20][1][19] = new StoneBlock();
        terrain[20][1][21] = new StoneBlock();
        terrain[20][2][20] = new StoneBlock();

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

    public void renderGrid(ModelBatch modelBatch, Environment environment, Camera camera)
    {

        for(int i = 0 - renderDistance/2; i < renderDistance/2 + 1; i++)
        {
            for(int j = 0; j < terrainSize; j++)
            {
                for(int k = 0 - renderDistance/2; k < renderDistance/2 + 1; k++)
                {
                    if ((Math.round(camera.position.x) / (int) pixelSize + i) <= 0 || Math.round(camera.position.z) / (int) pixelSize + k <= 0)
                    {
                        //NIX
                    }
                    else if ((Math.round(camera.position.x) / (int) pixelSize + i) >= 25 || Math.round(camera.position.z) / (int) pixelSize + k >= terrainSize)
                    {
                        //AUCH NIX
                    }
                    else if (terrain[Math.round(camera.position.x) / (int) pixelSize + i][j][Math.round(camera.position.z) / (int) pixelSize + k] != null)
                    {
                        modelBatch.render(terrain[Math.round(camera.position.x) / (int) pixelSize + i][j][Math.round(camera.position.z) / (int) pixelSize + k].getInstance(),environment);
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
                if (terrain[x][y][z].getType() == pufferDirt.getType())
                {
                    terrain[lastX][lastY][lastZ] = new DirtBlock();
                }
                else if(terrain[x][y][z].getType() == pufferStone.getType())
                {
                    terrain[lastX][lastY][lastZ] = new StoneBlock();
                }
                else if (terrain[x][y][z].getType() == pufferWood.getType())
                {
                    terrain[lastX][lastY][lastZ] = new WoodenBlock();
                }

                terrain[lastX][lastY][lastZ].setPosition(x*5,y*5,z*5);
                updateGrid();

                i = terrainSize * 2;
            }
            else if(y <=0)
            {
                break;
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    public void breakBlock(Vector3 vekPos, Vector3 vekDirc){
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
               terrain[x][y][z].dispose();
               terrain[x][y][z] = null;

                updateGrid();

                i = terrainSize * 2;
            }
            else if(y <= 0)
            {
                break;
            }
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
