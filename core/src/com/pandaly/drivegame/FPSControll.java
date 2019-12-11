package com.pandaly.drivegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;


public class FPSControll extends CameraInputController {
    Timer t = new Timer();

    public FPSControll(Camera camera) {
        super(camera);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 v = new Vector3(camera.position);


        camera.position.add(-0.5f, 0, 0);
        camera.update();
        //camera.position.add(-0.1f, 0, 0);
        return super.touchDragged(screenX,screenY,pointer);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < 450 && screenY > 540)
        {
            //while(touchUp(screenX,screenY,pointer,button) != true)
          //  {
            //    t.delay(100);

         //   }
            return super.touchDragged(screenX,screenY,pointer);
        }



        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }
}

