package net.k3nder.al;

import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class ALSource {
    private final int id;
    public ALSource() {
        id = alGenSources();
    }
    public void uploadData(int tt, ALSound ALSound) {
        alSourcei(id, tt, ALSound.buffer().getID());
    }
    public void play() {
        alSourcePlay(id);
    }
    public void pause() {
        alSourcePause(id);
    }
    public void resume() {
        alSourceRewind(id);
    }
    public void stop() {
        alSourceStop(id);
    }
    public void destroy() {
        alDeleteSources(id);
    }
    public static ALSource create(ALSound ALSound) {
        ALSource source = new ALSource();
        source.uploadData(AL_BUFFER, ALSound);
        return source;
    }
    public void setFloat(int propertie, float value) {
        alSourcef(id, propertie, value);
    }
    public float getFloat(int propertie) {
        return alGetSourcef(id, propertie);
    }
    public void setInt(int propertie, int value) {
        alSourcei(id, propertie, value);
    }
    public int getInt(int propertie) {
        return alGetSourcei(id, propertie);
    }
    public void setVector3f(int propertie, Vector3f value) {
        alSource3f(id, propertie, value.x, value.y, value.z);
    }
    public Vector3f getVector3f(int propertie) {
        float[] x = new float[1];
        float[] y = new float[1];
        float[] z = new float[1];
        alGetSource3f(id, propertie, x, y, z);
        return new Vector3f(x[0], y[0], z[0]);
    }
}
