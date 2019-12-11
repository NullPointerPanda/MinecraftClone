package com.pandaly.drivegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.kotcrab.vis.ui.widget.toast.Toast;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.AttributeSet;

public class DriveGame extends ApplicationAdapter {
	private final float fieldOfView = 67;
	private final float cameraNear = 1;
	private final float cameraFar = 300;
	private float framerate;
	//private float currentSecond;
	//private int framesLastSecond;
	//private int frameCount;
	//private final float crosshairSize = 55;

	/*private SpriteBatch spriteBatch;
	private Texture crosshair;
	private ModelInstance instance;*/

	private FPSControll cameraController;
	private ModelBatch modelBatch;
	private PerspectiveCamera camera;
	private com.badlogic.gdx.graphics.g3d.Environment environment;
	private Terrain terrain;
	private BitmapFont font;
	private SpriteBatch batch;

	@Override
	public void create ()
	{
		modelBatch = new ModelBatch();
		terrain = new Terrain(25);
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		font = new BitmapFont();
		batch = new SpriteBatch();

		camera = new PerspectiveCamera(fieldOfView, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(-2f, 2f, -2f);
		camera.near = cameraNear;
		camera.far = cameraFar;
		camera.update();
		framerate = Gdx.graphics.getFramesPerSecond();
		cameraController = new FPSControll(camera);

		Gdx.input.setInputProcessor(cameraController);


	}

	@Override
	public void render () {
		cameraController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


		modelBatch.begin(camera);
		for(DirtBlock d : terrain.returnList()){
			modelBatch.render(d.getInstance(), environment);
		}
		modelBatch.end();
		batch.begin();

		font.draw(batch, (int)framerate + " fps", 30, Gdx.graphics.getHeight() - 70);
		batch.end();

	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
		batch.dispose();
	}
}
