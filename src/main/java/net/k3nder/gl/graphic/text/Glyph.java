package net.k3nder.gl.graphic.text;

import net.k3nder.gl.graphic.GraphicalObject;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Glyph extends GraphicalObject {
     Polygon.builder()
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

}
