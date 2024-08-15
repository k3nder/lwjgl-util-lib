package net.k3nder.al.buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.opengl.GL15.*;

public class ALBuffer {
    private final int id;
    public ALBuffer() {
        id = alGenBuffers();
    }
    public void uploadData(int format, ShortBuffer data, int sampleRate) {
        alBufferData(id, format, data, sampleRate);
    }
    public void delete() {
        alDeleteBuffers(id);
    }
    public int getID(){
        return id;
    }
}
