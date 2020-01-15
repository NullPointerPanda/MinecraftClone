//==========================================
//	Title:  FlatLanders
//	Author: Fabian Joßberger | Luke Behrsing
//	Date:   15 Jan 2020
//==========================================

package com.pandaly.drivegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DirtBlock extends Block {
    public DirtBlock() {
        super(new Texture(Gdx.files.internal("Grass.png")), Type.DirtBlock);
    }
}
