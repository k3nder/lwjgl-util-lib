package net.k3nder.gl.graphic.shader;

import java.io.IOException;
import java.util.Objects;

public class Shaders {
    //public static final Shader SIMPLE = getShader("simple");

    public static Shader getDefaultShader(String name) {
        try {
            return Shader.create(
                    Objects.requireNonNull(Shader.class.getResourceAsStream("/default/shaders/" + name + "/" + name + ".vert")),
                    Objects.requireNonNull(Shader.class.getResourceAsStream("/default/shaders/" + name + "/" + name + ".frag"))
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
