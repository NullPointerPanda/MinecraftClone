package com.pandaly.drivegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import java.awt.Image;

public class DriveGame extends ApplicationAdapter {
	private final float fieldOfView = 67;
	private final float cameraNear = 1;
	private final float cameraFar = 300;
	private float framerate;
	//private final float crosshairSize = 55;

	private Sprite crosshair;
	private Texture crosshairTexture;
	//private ModelInstance instance;

	private Controller cameraController;
	private ModelBatch modelBatch;
	private PerspectiveCamera camera;
	private com.badlogic.gdx.graphics.g3d.Environment environment;
	private BitmapFont font;
	private SpriteBatch batch;
	boolean touchMove = false;

	private Grid grid;


	@Override
	public void create ()
	{
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		font = new BitmapFont();
		batch = new SpriteBatch();
		crosshairTexture = new Texture(Gdx.files.internal("Crosshair.png"));
		crosshair = new Sprite(crosshairTexture);
		camera = new PerspectiveCamera(fieldOfView, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		grid = new Grid();

		camera.position.set(10f, 10f, 10f);
		camera.direction.set(1.0f,0.0f,1.0f);
		camera.near = cameraNear;
		camera.far = cameraFar;

		grid.createGrid();
		camera.update();
		cameraController = new Controller(camera,grid);
		Gdx.input.setInputProcessor(cameraController);
	}

	@Override
	public void render () {
		cameraController.update();

		framerate = Gdx.graphics.getFramesPerSecond();

		if (cameraController.getPressed()) {
			if (touchMove) {
				cameraController.move();
			} else {
				touchMove = true;
			}
		} else {
			touchMove = false;
		}



		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		grid.renderGrid(modelBatch,environment);
		modelBatch.end();

		batch.begin();
		font.draw(batch, (int)framerate + " fps", Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() - 40), Gdx.graphics.getHeight() - 40);
		batch.draw(crosshair,Gdx.graphics.getWidth()/2-57f,Gdx.graphics.getHeight()/2-64f,Gdx.graphics.getHeight()/10, Gdx.graphics.getHeight()/10);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		grid.dispose();
		modelBatch.dispose();
	}
}
