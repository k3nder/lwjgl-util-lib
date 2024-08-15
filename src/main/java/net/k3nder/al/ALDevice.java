package net.k3nder.al;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import static org.lwjgl.openal.ALC10.*;

public class ALDevice {
    private final long id;
    public ALDevice(String device) {
        id = alcOpenDevice(device);
    }
    public ALDevice() {
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        id = alcOpenDevice(defaultDeviceName);
    }
    public ALContext createContext(int[] attributes) {
        return new ALContext(id, attributes);
    }
    public ALContext createContext() {
        return createContext(new int[] {0});
    }
    public ALCCapabilities createAlcCapabilities() {
        return ALC.createCapabilities(id);
    }
    public ALCapabilities getCapabilities() {
        return AL.createCapabilities(createAlcCapabilities());
    }
    public void destroy() {
        alcCloseDevice(id);
    }
    public long getID() {
        return id;
    }
}
