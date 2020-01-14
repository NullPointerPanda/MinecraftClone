package com.pandaly.drivegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class DriveGame implements Screen{
	private final float fieldOfView = 67;
	private final float cameraNear = 1;
	private final float cameraFar = 300;
	private float framerate;
	private Sprite crosshair;
	private Texture crosshairTexture;
	private Sprite axe;
	private Texture axeTexture;
	private Sprite blockPic;
	private Texture blockPicTexture;
	private Sprite dpad;
	private Texture dpadTexture;
	private Controller cameraController;
	private ModelBatch modelBatch;
	private PerspectiveCamera camera;
	private com.badlogic.gdx.graphics.g3d.Environment environment;
	private BitmapFont font;
	private SpriteBatch batch;
	boolean touchMove = false;
	private Grid grid;
	private Game game;

	public DriveGame(Game game){
		this.game = game;
	}

	public DriveGame() {
		super();
	}

	@Override
	public void show() {
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		font = new BitmapFont();
		batch = new SpriteBatch();
		crosshairTexture = new Texture(Gdx.files.internal("Crosshair.png"));
		crosshair = new Sprite(crosshairTexture);

		axeTexture = new Texture(Gdx.files.internal("axe.png"));
		axe = new Sprite(axeTexture);

        blockPicTexture = new Texture(Gdx.files.internal("block.png"));
        blockPic = new Sprite(blockPicTexture);

		dpadTexture = new Texture(Gdx.files.internal("dpad.png"));
		dpad = new Sprite(dpadTexture);

		camera = new PerspectiveCamera(fieldOfView, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		grid = new Grid();

		camera.position.set(50f, 10f, 50f);
		camera.direction.set(1.0f,0.0f,1.0f);
		camera.near = cameraNear;
		camera.far = cameraFar;

		grid.createGrid();
		camera.update();
		cameraController = new Controller(camera,grid);
		Gdx.input.setInputProcessor(cameraController);
	}

	@Override
	public void render(float delta) {
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

		Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		grid.renderGrid(modelBatch,environment, camera);
		modelBatch.end();

		batch.begin();
		font.draw(batch, (int)framerate + " fps", Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() - 40), Gdx.graphics.getHeight() - 40);
		batch.draw(crosshair,Gdx.graphics.getWidth()/2-57f,Gdx.graphics.getHeight()/2-64f,Gdx.graphics.getHeight()/10, Gdx.graphics.getHeight()/10);
		batch.draw(axe, Gdx.graphics.getWidth() - 128, Gdx.graphics.getHeight() - Gdx.graphics.getHeight(), 128,128);
        batch.draw(blockPic, Gdx.graphics.getWidth() - (128 * 2), Gdx.graphics.getHeight() - Gdx.graphics.getHeight(), 128,128);
		batch.draw(dpad, Gdx.graphics.getWidth() - Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight(), 512,512);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

    @Override
    public void dispose () {
        batch.dispose();
        grid.dispose();
        modelBatch.dispose();
    }
}
