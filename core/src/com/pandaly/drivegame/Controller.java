package com.pandaly.drivegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;


public class Controller extends FirstPersonCameraController {

    private float velocity = 5f;
    private float degreesPerPixel = 0.1f;

    private final Vector3 tmpVec = new Vector3();
    private final Camera camera;

    private boolean pressDown = false;

    private Grid grid;

    public Controller(Camera camera, Grid grid) {
        super(camera);
        this.camera = camera;
        tmpVec.set(camera.position);
        this.grid = grid;
    }

    public void setVelocity(float velocity){
        this.velocity = velocity;
    }

    public void setDegreesPerPixel(float degreesPerPixel){
        this.degreesPerPixel = degreesPerPixel;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float deltaX = -Gdx.input.getDeltaX() * degreesPerPixel;
        float deltaY = -Gdx.input.getDeltaY() * degreesPerPixel;
        camera.direction.rotate(camera.up,deltaX);
        tmpVec.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmpVec,deltaY);
        tmpVec.set(camera.position);

        return true;
    }

    public boolean getPressed()
    {
        return pressDown;
    }

    public void move(){
        IntArray coordinates = new IntArray(grid.checkForBlock(camera.position));
        //for (int i = 0; i < 6; i++)
        //{
            //if(grid.getTerrain(coordinates.get(0),coordinates.get(1),coordinates.get(2)).getPosition().x >= camera.position.set(tmpVec).x ||
            //        grid.getTerrain(coordinates.get(0),coordinates.get(1),coordinates.get(2)).getPosition().z <= camera.position.set(tmpVec).z )
            //{||
        //                       grid.getTerrain(coordinates.get(0),coordinates.get(1),coordinates.get(2)).getPosition().z <= camera.position.set(tmpVec).z


            //}

        //}
        //Block puffer = grid.getBlockInFront(camera.position,camera.direction,false,40);
        //puffer.setPosition(puffer.getPosition().x,puffer.getPosition().y-1, puffer.getPosition().z);
        if (grid.getBlockInFront(camera.position,camera.direction,false,50) == null)
        {
            tmpVec.z += 0.5f * camera.direction.z;
            tmpVec.x += 0.5f * camera.direction.x;
            camera.position.set(tmpVec);
        }
        else if (grid.getTerrain(coordinates.get(0),coordinates.get(1),coordinates.get(2)) != null)
        {
            if(grid.getTerrain(coordinates.get(0),coordinates.get(1),coordinates.get(2)).getPosition().x >= camera.position.set(tmpVec).x )
            {

            }


            System.out.println("VEKTOR OBEN X: " + grid.getTerrain(coordinates.get(0),coordinates.get(1),coordinates.get(2)).getPosition().x);
        }
        else
        {

        }


        if (grid.getTerrain(coordinates.get(0),coordinates.get(1),coordinates.get(2)) != null)
        {
            System.out.println("VEKTOR UNTEN X: " +grid.getTerrain(coordinates.get(0),coordinates.get(1),coordinates.get(2)).getPosition().z);
        }

        System.out.println("KOORDINATEN: " + coordinates.get(0) + " " + coordinates.get(1) + " " + coordinates.get(2));



    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < 450 && screenY > 200)
        {
            pressDown = true;
        }
        else if(screenX > 1600 && screenX < 1800 && screenY > 700 && screenY < 900)
        {
            grid.setBlock(camera.position, camera.direction);
        }
        else if(screenX > 1799 && screenX < 2000 && screenY > 700 && screenY < 900)
        {
            grid.breakBlock(camera.position, camera.direction);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        pressDown = false;

        return true;
    }
}

