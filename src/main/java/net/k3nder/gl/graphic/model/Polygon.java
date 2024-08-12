package net.k3nder.gl.graphic.model;

import net.k3nder.gl.Reloadable;
import net.k3nder.utils.UArray;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.Buffer;
import java.nio.FloatBuffer;
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
    private VertexArrayObject VAO;
    private VertexBufferObject VBO;

    private FloatBuffer vertices;
    private AttribPointers attribs;

    private int numVertices;

    public FloatBuffer getVertices() {
        return vertices;
    }

    public int getNumVertices() {
        return numVertices;
    }
    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public static class Builder {
        private final FloatBuffer vertices;
        private final AttribPointers attribs;

        public Builder() {
            vertices = MemoryUtil.memAllocFloat(4096);
            attribs = new AttribPointers();
        }

        public Builder put(float x) {
            vertices.put(x);
            return this;
        }

        public Builder attribPointer(int lay, int size) {
            attribs.add(lay, size);
            return this;
        }

        public Polygon build() {
            Polygon polygon = new Polygon();
            polygon.attribs = attribs;
            polygon.vertices = this.vertices;
            return polygon;
        }
    }
    public void use() {
        VAO.bind();
        attribs.enable();
    }
    public void unuse() {
        attribs.disable();
        glBindVertexArray(0);
    }
    public void render(Integer mode) {
        use();
        glDrawArrays(mode, 0,  vertices.limit());
        unuse();
    }
    public void load() {
        load(GL_STATIC_DRAW);
    }
    public void load(Integer mode) {

        VAO = new VertexArrayObject();
        VAO.bind();

        vertices.flip();

        VBO = new VertexBufferObject();
        VBO.bind(GL_ARRAY_BUFFER);
        glBufferData(GL_ARRAY_BUFFER, vertices, mode);
        //VBO.uploadData(GL_ARRAY_BUFFER, FloatBuffer.wrap(vertices), mode);

        attribs.apply();
        attribs.enable();

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    public static Builder builder() {
        return new Builder();
    }
    public void clean() {
        VAO.delete();
        VBO.delete();
    }
}
