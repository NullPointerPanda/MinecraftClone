//==========================================
//	Title:  FlatLanders
//	Author: Fabian Joßberger | Luke Behrsing
//	Date:   15 Jan 2020
//==========================================

package com.pandaly.drivegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WoodenBlock extends Block {
    public WoodenBlock() {
        super(new Texture(Gdx.files.internal("WoodenPlank.png")), Type.WoodenBlock);
    }
}
