package com.pandaly.drivegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Vector3;


public class Controller extends FirstPersonCameraController {

    private float velocity = 5f;
    private float degreesPerPixel = 0.1f;

    private final Vector3 tmpVec = new Vector3();
    private final Camera camera;

    private boolean pressDown = false;

    public Controller(Camera camera) {
        super(camera);
        this.camera = camera;
        tmpVec.set(camera.position);
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

        tmpVec.z += 0.3f * camera.direction.z;
        tmpVec.x += 0.3f * camera.direction.x;
        camera.position.set(tmpVec);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < 450 && screenY > 200)
        {
            pressDown = true;
        }


        System.out.println("position: "+camera.position+" direction: "+camera.direction);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        pressDown = false;
        return true;
    }
}

