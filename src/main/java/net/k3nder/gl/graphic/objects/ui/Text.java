package net.k3nder.gl.graphic.objects.ui;

import net.k3nder.gl.graphic.GraphicalObject;
import net.k3nder.gl.graphic.shader.Shader;
import net.k3nder.gl.graphic.shader.Shaders;
import net.k3nder.gl.graphic.text.FontLoader;
import net.k3nder.gl.graphic.text.Glyph;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.util.freetype.FreeType.FT_New_Face;

public class Text extends StaticCube {
    protected String text = "" ;
    private FontLoader font;
    private Vector2f position;
    private Vector3f size;

    public Text(FontLoader loader, String text, Vector2f pos, Vector3f size) throws IOException, FontFormatException {
        super(new Vector3f(pos.x, pos.y, 0), null);
        position = pos;
        this.size = size;
        this.text = text;
        this.font = loader;
        model.translate(position.x, position.y, 0);
        staticShader = Shaders.getDefaultShader("static_model");
    }
    public void render(Shader shader) {
        staticShader.use();
        staticShader.setMatrix(model, "model");

        float ip = 0;
        for (char c : text.toCharArray()) {
            Vector2f pos = new Vector2f(position.x + ip, position.y);
            //.out.println("c: " + c + " pos: " + pos + " img: " + font.getGlyphs().get(Character.toString(c)));
            Glyph glyph = new Glyph(font, c, pos);
            glyph.setModel(new Matrix4f().translate(pos.x, pos.y, 0).scale(new Vector3f(size.x, size.y, 0.0f)));
            glyph.load();
            glyph.render(staticShader);
            ip += size.x + size.z;
        }
    }
    public void setText(String text) {
        this.text = text;
    }
}
