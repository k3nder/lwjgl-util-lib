package net.k3nder.gl.graphic.visual;

import net.k3nder.gl.graphic.shader.Shader;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL12.glTexImage3D;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Texture {
    private final int id;
    private int width;
    private int height;

    public Texture() {
        id = glGenTextures();
    }
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
    public void setParameter(int param, int value) {
        glTexParameteri(GL_TEXTURE_2D, param, value);
    }
    public void uploadData(int width, int height, ByteBuffer data) {
        uploadData(GL_RGBA8, width, height, GL_RGBA, data);
    }
    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }
    public void delete() {
        glDeleteTextures(id);
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }
    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }
    public static Texture createTexture(int width, int height, ByteBuffer data) {
        Texture tex = new Texture();
        tex.setWidth(width);
        tex.setHeight(height);

        tex.bind();

        tex.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        tex.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        tex.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        tex.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        tex.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

        return tex;
    }
    public static Texture loadTexture(String path) {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Prepare image buffers */
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            /* Load image */
            stbi_set_flip_vertically_on_load(true);
            image = stbi_load(path, w, h, comp, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load a texture file!"
                        + System.lineSeparator() + stbi_failure_reason());
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
        }
        return createTexture(width, height, image);
    }

    public int getID() {
        return id;
    }
}