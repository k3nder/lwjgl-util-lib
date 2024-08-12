package net.k3nder.gl.graphic.objects.ui;

import net.k3nder.gl.graphic.objects.Cube;
import net.k3nder.gl.graphic.shader.Shader;
import net.k3nder.gl.graphic.shader.Shaders;
import net.k3nder.gl.graphic.visual.Texture;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class StaticCube extends Cube {
    protected Shader staticShader;
    public StaticCube(Vector3f pos, Texture tex) {
        super(pos, tex);
        model.translate(pos);
    }
    @Override
    public void render(Shader shader) {
        staticShader.use();
        super.render(staticShader);
    }
    @Override
    public void load() {
        super.load();
        staticShader = Shaders.getDefaultShader("static_model");
    }
}
