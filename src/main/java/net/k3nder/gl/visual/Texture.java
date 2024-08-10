package net.k3nder.gl.visual;

import net.k3nder.gl.shader.Shader;
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
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Texture {
    private int id;
    private int width;
    private int height;
    private Integer type;
    private HashMap<Integer, Integer> configurations = new HashMap<>();
    private InputStream image;
    private Integer dChannels;
    private Boolean flipV;
    private Integer colorChannel;

    private Texture() {}

    private static Texture create(InputStream stream, int textureType, Integer dChannels,HashMap<Integer, Integer> configurations, boolean flipV, Integer colorChannel) {
        Texture t = new Texture();

        t.configurations = configurations;
        t.type = textureType;
        t.dChannels = dChannels;
        t.image = stream;
        t.flipV = flipV;
        t.colorChannel = colorChannel;


        return t;
    }

    public int getId() {
        return id;
    }

    public void init() {
        MemoryStack stack = stackPush();

        IntBuffer w = stack.mallocInt(1);
        IntBuffer h = stack.mallocInt(1);
        IntBuffer channels = stack.mallocInt(1);

        ByteBuffer buffer = null;
        try {
            buffer = readInputStreamToByteBuffer(image);
            if (buffer == null)
                throw new IOException("Failed to read texture buffer");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteBuffer data = STBImage.stbi_load_from_memory(buffer, w, h, channels,  dChannels);

        width = w.get(0);
        height = h.get(0);

        id = glGenTextures();
        glBindTexture(type, id);

        loadConfigurations(type);
        STBImage.stbi_set_flip_vertically_on_load(flipV);


        if (type == GL_TEXTURE_1D) {
            glTexImage1D(type, 0, colorChannel, width, 0, colorChannel, GL_UNSIGNED_BYTE, data);
        } else if (type == GL_TEXTURE_2D) {
            glTexImage2D(type, 0, colorChannel, width, height, 0, colorChannel, GL_UNSIGNED_BYTE, data);
        }
        glGenerateMipmap(type);

        stbi_image_free(data);
    }

    private void loadConfigurations(int textureType) {
        configurations.forEach((k, v) -> {
            glTexParameteri(textureType, k, v);
        });
    }

    public void addToShader(String key, Shader shader) {
        shader.setI(key, this.id);
    }

    public void active(Integer texture) {
        glActiveTexture(texture);
        glBindTexture(this.type, this.id);
    }

    private static ByteBuffer readInputStreamToByteBuffer(InputStream inputStream) throws IOException {
        if (inputStream == null) System.out.println("errs");
        try (var readableByteChannel = Channels.newChannel(inputStream)) {
            ByteBuffer buffer = BufferUtils.createByteBuffer(8192);
            while (true) {
                int bytesRead = readableByteChannel.read(buffer);
                if (bytesRead == -1) break;
                if (buffer.remaining() == 0) {
                    ByteBuffer newBuffer = BufferUtils.createByteBuffer(buffer.capacity() * 2);
                    buffer.flip();
                    newBuffer.put(buffer);
                    buffer = newBuffer;
                }
            }
            buffer.flip();
            return buffer;
        }
    }
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private HashMap<Integer, Integer> configurations = new HashMap<>();
        private Integer type = GL_TEXTURE_2D;
        private InputStream stream;
        private Integer dChannels = 0;
        private Integer colorChanel = GL_RGB;
        private Boolean flipV = false;

        public HashMap<Integer, Integer> getConfigurations() {
            return configurations;
        }

        public Builder configuration(Integer key, Integer value) {
            this.configurations.put(key, value);
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder source(InputStream stream) {
            this.stream = stream;
            return this;
        }

        public Builder dChannels(Integer dChannels) {
            this.dChannels = dChannels;
            return this;
        }

        public Builder source(String file) {
            try {
                this.stream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder colorChanel(Integer colorChanel) {
            this.colorChanel = colorChanel;
            return this;
        }

        public Builder flipV() {
            this.flipV = true;
            return this;
        }

        public Texture create() {
            return Texture.create(this.stream, this.type, this.dChannels, this.configurations, this.flipV, this.colorChanel);
        }
    }

}