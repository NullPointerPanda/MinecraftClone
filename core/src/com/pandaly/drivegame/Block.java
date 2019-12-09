package com.pandaly.drivegame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Disposable;

public class Block implements Disposable
{
    private Type type;
    private Texture texture = new Texture("Dirt.png");
    private static ModelBuilder modelBuilder;
    private Model model;
    private Material material = new Material();

    public Block(Texture texture, Type type)
    {
        this.type = type;
        material = new Material(new Material(TextureAttribute.createDiffuse(texture)));
    }

    public void iwasproductive(){
        modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f,5f,5f, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
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
