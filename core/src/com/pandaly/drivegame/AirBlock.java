package com.pandaly.drivegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AirBlock extends Block {
    public AirBlock() {
        super(new Texture(Gdx.files.internal("Stone.png")),Type.AirBlock);
    }
}
