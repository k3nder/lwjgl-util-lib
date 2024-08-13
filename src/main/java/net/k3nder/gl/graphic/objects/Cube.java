package net.k3nder.gl.graphic.objects;

import net.k3nder.gl.graphic.GraphicalObject;
import net.k3nder.gl.graphic.model.ModelLoader;
import net.k3nder.gl.graphic.model.Polygon;
import net.k3nder.gl.graphic.visual.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.File;

public class Cube extends GraphicalObject {
    public static Polygon CUBE = new ModelLoader().load(new File("sin_nombre.obj")); /* Polygon.builder()
            .put(-0.5f).put( -0.5f).put( -0.5f).put( 0.0f).put(0.0f).put( -1.0f).put( 0.0f).put( 0.0f)
            .put(0.5f).put(-0.5f).put(-0.5f).put( 0.0f).put( 0.0f).put(-1.0f).put( 1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f).put(-0.5f).put( 0.0f).put( 0.0f).put(-1.0f).put( 1.0f).put( 1.0f)
            .put(0.5f).put( 0.5f).put(-0.5f).put( 0.0f).put( 0.0f).put(-1.0f).put( 1.0f).put( 1.0f)
            .put(-0.5f).put( 0.5f).put(-0.5f).put( 0.0f).put( 0.0f).put(-1.0f).put( 0.0f).put( 1.0f)
            .put(-0.5f).put(-0.5f).put(-0.5f).put( 0.0f).put( 0.0f).put(-1.0f).put( 0.0f).put( 0.0f)
            .put(-0.5f).put(-0.5f).put( 0.5f).put( 0.0f).put( 0.0f).put(1.0f).put( 0.0f).put( 0.0f)
            .put(0.5f).put(-0.5f).put( 0.5f).put( 0.0f).put( 0.0f).put(1.0f).put( 1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put( 0.0f).put(1.0f).put( 1.0f).put( 1.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put( 0.0f).put(1.0f).put( 1.0f).put( 1.0f)
            .put(-0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put( 0.0f).put(1.0f).put( 0.0f).put( 1.0f)
            .put(-0.5f).put(-0.5f).put( 0.5f).put( 0.0f).put( 0.0f).put(1.0f).put( 0.0f).put( 0.0f)
            .put(-0.5f).put( 0.5f).put( 0.5f).put(-1.0f).put( 0.0f).put( 0.0f).put( 1.0f).put( 0.0f)
            .put(-0.5f).put( 0.5f).put(-0.5f).put(-1.0f).put( 0.0f).put( 0.0f).put( 1.0f).put( 1.0f)
            .put(-0.5f).put(-0.5f).put(-0.5f).put(-1.0f).put( 0.0f).put( 0.0f).put( 0.0f).put( 1.0f)
            .put(-0.5f).put(-0.5f).put(-0.5f).put(-1.0f).put( 0.0f).put( 0.0f).put( 0.0f).put( 1.0f)
            .put(-0.5f).put(-0.5f).put( 0.5f).put(-1.0f).put( 0.0f).put( 0.0f).put( 0.0f).put( 0.0f)
            .put(-0.5f).put( 0.5f).put( 0.5f).put(-1.0f).put( 0.0f).put( 0.0f).put( 1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 1.0f).put( 0.0f).put( 0.0f).put( 1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f).put(-0.5f).put( 1.0f).put( 0.0f).put( 0.0f).put( 1.0f).put( 1.0f)
            .put(0.5f).put(-0.5f).put(-0.5f).put( 1.0f).put( 0.0f).put( 0.0f).put( 0.0f).put( 1.0f)
            .put(0.5f).put(-0.5f).put(-0.5f).put( 1.0f).put( 0.0f).put( 0.0f).put( 0.0f).put( 1.0f)
            .put(0.5f).put(-0.5f).put( 0.5f).put( 1.0f).put( 0.0f).put( 0.0f).put( 0.0f).put( 0.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 1.0f).put( 0.0f).put( 0.0f).put( 1.0f).put( 0.0f)
            .put(-0.5f).put(-0.5f).put(-0.5f).put( 0.0f).put(-1.0f).put( 0.0f).put( 0.0f).put( 1.0f)
            .put(0.5f).put(-0.5f).put(-0.5f).put( 0.0f).put(-1.0f).put( 0.0f).put( 1.0f).put( 1.0f)
            .put(0.5f).put(-0.5f).put( 0.5f).put( 0.0f).put(-1.0f).put( 0.0f).put( 1.0f).put( 0.0f)
            .put(0.5f).put(-0.5f).put( 0.5f).put( 0.0f).put(-1.0f).put( 0.0f).put( 1.0f).put( 0.0f)
            .put(-0.5f).put(-0.5f).put( 0.5f).put( 0.0f).put(-1.0f).put( 0.0f).put( 0.0f).put( 0.0f)
            .put(-0.5f).put(-0.5f).put(-0.5f).put( 0.0f).put(-1.0f).put( 0.0f).put( 0.0f).put( 1.0f)
            .put(-0.5f).put( 0.5f).put(-0.5f).put( 0.0f).put( 1.0f).put( 0.0f).put( 0.0f).put( 1.0f)
            .put(0.5f).put( 0.5f).put(-0.5f).put( 0.0f).put( 1.0f).put( 0.0f).put( 1.0f).put( 1.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put( 1.0f).put( 0.0f).put( 1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put( 1.0f).put( 0.0f).put( 1.0f).put( 0.0f)
            .put(-0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put( 1.0f).put( 0.0f).put( 0.0f).put( 0.0f)
            .put(-0.5f).put( 0.5f).put(-0.5f).put( 0.0f).put( 1.0f).put( 0.0f).put( 0.0f).put( 1.0f)


            .attribPointer(0, 3)
            .attribPointer(1, 3)
            .attribPointer(2, 2)

            .build();*/
    public Cube(Vector3f pos, Texture t) {
        super();
        texture = t;
        model.translate(pos);
    }
    @Override
    public void load() {
        polygon = CUBE;
    }
}
