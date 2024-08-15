package net.k3nder.al;

import static org.lwjgl.openal.ALC10.*;

public class ALContext {
    private final long id;
    public ALContext(long device, int[] attributes) {
        id = alcCreateContext(device, attributes);
    }
    public void makeContextCurrent() {
        alcMakeContextCurrent(id);
    }
    public void destroy() {
        alcDestroyContext(id);
    }
    public long getID() {
        return id;
    }
}
