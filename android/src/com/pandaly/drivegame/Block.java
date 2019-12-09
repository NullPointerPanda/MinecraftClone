package com.pandaly.drivegame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Disposable;

public class Block implements Disposable
{
    private Type type;
    private Texture texture;
    private static ModelBuilder modelBuilder;

    public Block(Texture texture, Type type)
    {
        this.type = type;

        modelBuilder = new ModelBuilder();
        modelBuilder.createBox(512, 512 ,512 ,)
    }

    public enum Type
    {
        dirtBlock,
        stoneBlock
    }
    @Override
    public void dispose()
    {

    }
}
