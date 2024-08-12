package net.k3nder.gl.graphic.objects;

import net.k3nder.gl.graphic.GraphicalObject;
import net.k3nder.gl.graphic.model.Polygon;
import net.k3nder.gl.graphic.model.Vertex;
import net.k3nder.gl.graphic.visual.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Cube extends GraphicalObject {
    public static final Polygon CUBE = Polygon.builder()
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f  ( 0.0f,  0.0f, -1.0f), new Vector2f( 0.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f, -0.5f, -0.5f), new Vector3f ( 0.0f,  0.0f, -1.0f),  new Vector2f( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f, -0.5f), new Vector3f ( 0.0f,  0.0f, -1.0f),  new Vector2f( 1.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f, -0.5f), new Vector3f ( 0.0f,  0.0f, -1.0f),  new Vector2f( 1.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f,  0.5f, -0.5f),new Vector3f  ( 0.0f,  0.0f, -1.0f), new Vector2f ( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f  ( 0.0f,  0.0f, -1.0f), new Vector2f ( 0.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f,  0.5f),new Vector3f  ( 0.0f,  0.0f, 1.0f),  new Vector2f ( 0.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f, -0.5f,  0.5f), new Vector3f ( 0.0f,  0.0f, 1.0f),   new Vector2f( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f,  0.5f), new Vector3f ( 0.0f,  0.0f, 1.0f),   new Vector2f( 1.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f,  0.5f), new Vector3f ( 0.0f,  0.0f, 1.0f),   new Vector2f( 1.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f,  0.5f,  0.5f),new Vector3f  ( 0.0f,  0.0f, 1.0f),  new Vector2f ( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f,  0.5f),new Vector3f  ( 0.0f,  0.0f, 1.0f),  new Vector2f ( 0.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f,  0.5f,  0.5f),new Vector3f  (-1.0f,  0.0f,  0.0f), new Vector2f ( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f,  0.5f, -0.5f),new Vector3f  (-1.0f,  0.0f,  0.0f), new Vector2f ( 1.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f  (-1.0f,  0.0f,  0.0f), new Vector2f ( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f  (-1.0f,  0.0f,  0.0f), new Vector2f ( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f,  0.5f),new Vector3f  (-1.0f,  0.0f,  0.0f), new Vector2f ( 0.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f,  0.5f,  0.5f),new Vector3f  (-1.0f,  0.0f,  0.0f), new Vector2f ( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f,  0.5f), new Vector3f ( 1.0f,  0.0f,  0.0f),  new Vector2f( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f, -0.5f), new Vector3f ( 1.0f,  0.0f,  0.0f),  new Vector2f( 1.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f, -0.5f, -0.5f), new Vector3f ( 1.0f,  0.0f,  0.0f),  new Vector2f( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f, -0.5f, -0.5f), new Vector3f ( 1.0f,  0.0f,  0.0f),  new Vector2f( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f, -0.5f,  0.5f), new Vector3f ( 1.0f,  0.0f,  0.0f),  new Vector2f( 0.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f,  0.5f), new Vector3f ( 1.0f,  0.0f,  0.0f),  new Vector2f( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f  ( 0.0f, -1.0f,  0.0f), new Vector2f ( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f, -0.5f, -0.5f), new Vector3f ( 0.0f, -1.0f,  0.0f),  new Vector2f( 1.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f, -0.5f,  0.5f), new Vector3f ( 0.0f, -1.0f,  0.0f),  new Vector2f( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f, -0.5f,  0.5f), new Vector3f ( 0.0f, -1.0f,  0.0f),  new Vector2f( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f,  0.5f),new Vector3f  ( 0.0f, -1.0f,  0.0f), new Vector2f ( 0.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f, -0.5f, -0.5f),new Vector3f  ( 0.0f, -1.0f,  0.0f), new Vector2f ( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f,  0.5f, -0.5f),new Vector3f  ( 0.0f,  1.0f,  0.0f), new Vector2f ( 0.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f, -0.5f), new Vector3f ( 0.0f,  1.0f,  0.0f),  new Vector2f( 1.0f, 1.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f,  0.5f), new Vector3f ( 0.0f,  1.0f,  0.0f),  new Vector2f( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(0.5f,  0.5f,  0.5f), new Vector3f ( 0.0f,  1.0f,  0.0f),  new Vector2f( 1.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f,  0.5f,  0.5f),new Vector3f  ( 0.0f,  1.0f,  0.0f), new Vector2f ( 0.0f, 0.0f)))
            .vertex(Vertex.create(new Vector3f(-0.5f,  0.5f, -0.5f),new Vector3f (  0.0f,  1.0f,  0.0f), new Vector2f(  0.0f, 1.0f)))
            .build();
    public Cube(Vector3f pos, Texture t) {
        super();
        model.translate(pos);
        polygon = CUBE;
        this.texture = t;
    }
}
