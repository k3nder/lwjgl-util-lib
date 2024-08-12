package net.k3nder.gl.graphic.text;

import net.k3nder.gl.Renderable;
import net.k3nder.gl.graphic.GraphicalObject;
import net.k3nder.gl.graphic.model.VertexArrayObject;
import net.k3nder.gl.graphic.model.VertexBufferObject;
import net.k3nder.gl.graphic.objects.Cube;
import net.k3nder.gl.graphic.shader.Shader;
import net.k3nder.gl.graphic.visual.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.freetype.FreeType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.util.freetype.FreeType.FT_New_Face;
import static org.lwjgl.util.freetype.FreeType.nFT_Activate_Size;

public class Text extends GraphicalObject {
    private String text = "" ;
    private FontLoader font;
    private Vector2f position;
    private Vector2f size;

    public Text(FontLoader loader, String text, Vector2f pos, Vector2f size) throws IOException, FontFormatException {
        position = pos;
        this.size = size;
        this.text = text;
        this.font = loader;
        model.translate(position.x, position.y, 0);
    }
    public void render(Shader shader) {
        int ip = 0;
        for (char c : text.toCharArray()) {
            Vector2f pos = new Vector2f(position.x + ip, position.y);
            //.out.println("c: " + c + " pos: " + pos + " img: " + font.getGlyphs().get(Character.toString(c)));
            Glyph glyph = new Glyph(font, c, pos);
            glyph.render(shader);
            ip++;
        }
    }
    public void setText(String text) {
        this.text = text;
    }
}
