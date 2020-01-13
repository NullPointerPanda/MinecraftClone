package com.pandaly.drivegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.sql.Driver;

public class StartScreen extends Game {

    private Game game;
    private Sprite logo;
    private Texture logoTexture;
    private SpriteBatch batch;

    public StartScreen(){
        game = this;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        logoTexture = new Texture(Gdx.files.internal("Logo.jpeg"));
        logo = new Sprite(logoTexture,-250, -150, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if(Gdx.input.justTouched()){
            this.setScreen(new DriveGame(game));
        }
    }


    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        logo.draw(batch);
        batch.end();
    }
}
