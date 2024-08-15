package net.k3nder.al;

import org.lwjgl.openal.AL10;

import static org.lwjgl.openal.AL10.AL_NO_ERROR;
import static org.lwjgl.openal.AL10.alGetError;


public enum ALError {
    AL_INVALID_NAME,
    AL_INVALID_ENUM,
    AL_INVALID_VALUE,
    AL_INVALID_OPERATION,
    AL_OUT_OF_MEMORY,
    AL_NO_ERROR;

    public static ALError of(int error) {
        if (error != AL10.AL_NO_ERROR) {
            if (error == AL10.AL_INVALID_NAME) {
                return AL_INVALID_NAME;
            } else if (error == AL10.AL_INVALID_ENUM) {
                return AL_INVALID_ENUM;
            } else if (error == AL10.AL_INVALID_VALUE) {
                return AL_INVALID_VALUE;
            } else if (error == AL10.AL_INVALID_OPERATION) {
                return AL_INVALID_OPERATION;
            } else if (error == AL10.AL_OUT_OF_MEMORY) {
                return AL_OUT_OF_MEMORY;
            }
        }
        return AL_NO_ERROR;
    }
    public static ALError error() {
        return of(alGetError());
    }
}

