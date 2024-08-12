package net.k3nder.gl.graphic.model;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;

public class AttribPointers {
    private List<AttribPointer> pointers = new ArrayList<>();
    public AttribPointers add(int lay, int size) {
        pointers.add(new AttribPointer(size, this.vertex() * Float.BYTES, lay, GL_FLOAT));
        return this;
    }
    public int vertex() {
        int vertex = 0;
        for (AttribPointer pointer : pointers) {
            vertex += pointer.getSize();
        }
        return vertex;
    }
    public void apply() {
        for (AttribPointer pointer : pointers) {
            pointer.apply(vertex());
        }
    }
    public void enable(int lay) {
        glEnableVertexAttribArray(lay);
    }
    public void disable(int lay) {
        glDisableVertexAttribArray(lay);
    }
    public void enable() {
        for (AttribPointer pointer : pointers) {
            glEnableVertexAttribArray(pointer.getLayout());
        }
    }
    public void disable() {
        for (AttribPointer pointer : pointers) {
            glDisableVertexAttribArray(pointer.getLayout());
        }
    }
}
