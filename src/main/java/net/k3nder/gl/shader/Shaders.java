package net.k3nder.gl.shader;

import net.k3nder.test.Main;

import java.io.IOException;

public class Shaders {
    //public static final Shader SIMPLE = getShader("simple");

    public static Shader getDefaultShader(String name) {
        try {
            Shader shader = Shader.create(
                    Main.class.getResourceAsStream("/default/shaders/" + name + "/" + name + ".vert"),
                    Main.class.getResourceAsStream("/default/shaders/" + name + "/" + name + ".frag")
                    );
            return shader;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
