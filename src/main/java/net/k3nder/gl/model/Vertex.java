package net.k3nder.gl.model;

import net.k3nder.utils.UArray;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vertex {
    private Vector3f vertex;
    private Vector2f texture;
    private Vector3f normal;
    //private Vector3f normal;
    protected Vertex() {}
    public static Vertex create(Vector3f vertex, Vector3f normal, Vector2f texture) {
        Vertex v = new Vertex();
        v.vertex = vertex;
        v.texture = texture;
        v.normal = normal;
        return v;
    }

    public Vector2f getTexture() {
        return texture;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public Vector3f getVertex() {
        return vertex;
    }

    public float[] toArray() {
        return new float[]{vertex.x(), vertex.y(), vertex.z(), texture.x(), texture.y() /*normal.x(), normal.y(), normal.z()*/};
    }
    public static float[] toArray(Vertex[] vertices) {
        List<Float> verticesList = new ArrayList<>();
        Arrays.stream(vertices).forEach((v) -> {
            verticesList.add(v.vertex.x());
            verticesList.add(v.vertex.y());
            verticesList.add(v.vertex.z());

            verticesList.add(v.normal.x());
            verticesList.add(v.normal.y());
            verticesList.add(v.normal.z());

            verticesList.add(v.texture.x());
            verticesList.add(v.texture.y());
        });
        return UArray.toPrimitive(verticesList.toArray(new Float[] {}));
    }
}
