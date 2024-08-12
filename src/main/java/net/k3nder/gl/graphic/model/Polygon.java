package net.k3nder.gl.graphic.model;

import net.k3nder.gl.Reloadable;
import net.k3nder.utils.UArray;
import org.joml.Vector2f;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

public class Polygon implements Reloadable {
    private Integer VAO;
    private Integer VBO;
    private Integer EBO;

    private List<Vertex> vertices;
    private List<Integer> faces;

    public static class Builder {
        private final List<Vertex> vertices = new ArrayList<>();
        private final List<Integer> faces = new ArrayList<>();

        public Builder() {}

        public Polygon build() {
            Polygon p = new Polygon();
            p.vertices = vertices;
            p.faces = faces;
            return p;
        }

        public Builder vertex(Vertex v) {
            vertices.add(v);
            return this;
        }
        public Builder face(Integer f) {
            faces.add(f);
            return this;
        }
    }
    public void use() {
        glBindVertexArray(VAO);
        glEnableVertexAttribArray(0);
        //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
    }
    public void unuse() {
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
    public void draw(Integer mode) {
        use();
        glDrawArrays(mode, 0,  vertex());
        unuse();
    }
    public int vertex() {
        return vertices.size();
    }
    public void create(Integer mode) {

        var vertices = UArray.toPrimitive(verticesToArray());

        EBO = glGenBuffers();

        var indices = UArray.toPrimitive(faces.toArray(new Integer[0]));

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, mode);

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, mode);

        glVertexAttribPointer(0, 3, GL_FLOAT, false,8 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false,8 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false,8 * Float.BYTES, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public static Builder builder() {
        return new Builder();
    }
    public void clean() {
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
    }
    public void load() {
        create(GL_STATIC_DRAW);
    }

    public Float[] verticesToArray() {
        List<Float> result = new ArrayList<>();
        for (Vertex v : vertices) {
            Vector3f ver = v.getVertex();
            Vector3f normal = v.getNormal();
            Vector2f tex = v.getTexture();

            result.add(ver.x);
            result.add(ver.y);
            result.add(ver.z);

            result.add(normal.x);
            result.add(normal.y);
            result.add(normal.z);

            result.add(tex.x);
            result.add(tex.y);
        }
        return result.toArray(new Float[0]);
    }
}
