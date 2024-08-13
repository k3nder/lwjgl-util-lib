package net.k3nder.gl.graphic.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;

public class ElementArrayBuffer {
    private final int id;

    public ElementArrayBuffer() {
        id = glGenBuffers();
    }
    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }
    public void uploadData(int target, FloatBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
    public void uploadData(int target, long size, int usage) {
        glBufferData(target, size, usage);
    }
    public void uploadSubData(int target, long offset, FloatBuffer data) {
        glBufferSubData(target, offset, data);
    }
    public void uploadData(int target, IntBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
    public void delete() {
        glDeleteBuffers(id);
    }
    public int getID(){
        return id;
    }
}
