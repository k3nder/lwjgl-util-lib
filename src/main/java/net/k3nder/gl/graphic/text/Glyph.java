package net.k3nder.gl.graphic.text;

import net.k3nder.gl.graphic.objects.Cube;
import net.k3nder.gl.graphic.objects.ui.StaticCube;
import net.k3nder.gl.graphic.shader.Shader;
import net.k3nder.gl.graphic.visual.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Glyph extends StaticCube {
    public Glyph(FontLoader loader,char c, Vector2f pos) {
        super(new Vector3f(pos.x, pos.y, 0.0f), loader.getGlyphs().get(Character.valueOf(c)));
    }
}
