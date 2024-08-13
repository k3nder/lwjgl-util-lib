package net.k3nder.gl.exceptions;

import static org.lwjgl.assimp.Assimp.aiGetErrorString;

public class LoadModelException extends RuntimeException {
    public LoadModelException() {
        super(aiGetErrorString());
    }
}
