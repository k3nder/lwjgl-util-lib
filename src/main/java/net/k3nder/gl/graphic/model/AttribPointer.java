package net.k3nder.gl.graphic.model;

import net.k3nder.gl.Applicable;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class AttribPointer {
    private int size;
    private int pointer;
    private int layout;
    private int type;
    public AttribPointer(int size, int pointer, int layout, int type) {
        this.size = size;
        this.pointer = pointer;
        this.type = type;
        this.layout = layout;
    }
    public void apply(int total) {
        glVertexAttribPointer(layout, size, GL_FLOAT, false,total * Float.BYTES, pointer);
    }
    public int getSize() {
        return size;
    }
    public int getLayout() {
        return layout;
    }
}
