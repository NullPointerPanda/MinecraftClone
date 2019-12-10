package com.pandaly.drivegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DirtBlock extends Block {
    public DirtBlock() {
        super(new Texture(Gdx.files.internal("Dirt.png")), Type.DirtBlock);
    }
}
