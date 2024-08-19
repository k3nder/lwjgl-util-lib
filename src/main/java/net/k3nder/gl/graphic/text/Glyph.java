package net.k3nder.gl.graphic.text;

import net.k3nder.gl.graphic.GraphicalObject;
import net.k3nder.gl.graphic.model.Polygon;
import net.k3nder.gl.graphic.shader.Shader;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Glyph extends GraphicalObject {
      private Shader shader;
      public static final Polygon MODEL =  Polygon.builder()
            .put(-0.5f).put( -0.5f).put( -0.5f).put( 0.0f).put(0.0f).put( -1.0f).put(0.0f).put( 0.0f)
            .put(0.5f ).put(-0.5f ).put(-0.5f).put( 0.0f).put(  0.0f).put( -1.0f).put( 1.0f).put( 0.0f)
            .put(0.5f ).put( 0.5f ).put(-0.5f).put( 0.0f).put( 0.0f).put( -1.0f).put(1.0f).put( 1.0f)
            .put(0.5f ).put( 0.5f ).put(-0.5f).put( 0.0f).put( 0.0f).put( -1.0f).put( 1.0f).put( 1.0f)
            .put(-0.5f).put(  0.5f).put( -0.5f).put(0.0f).put(  0.0f).put( -1.0f).put( 0.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put( 0.0f).put(  0.0f).put( -1.0f).put( 0.0f).put( 0.0f)
            .put(-0.5f).put( -0.5f).put(  0.5f).put( 0.0f).put(  0.0f).put( 1.0f).put( 0.0f).put( 0.0f)
            .put(0.5f).put(-0.5f).put( 0.5f).put( 0.0f).put(0.0f).put( 1.0f).put( 1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put(0.0f).put( 1.0f).put(  1.0f).put( 1.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put(  0.0f).put( 1.0f).put(  1.0f).put( 1.0f)
            .put(-0.5f).put(  0.5f).put(  0.5f).put ( 0.0f).put(  0.0f).put( 1.0f).put(  0.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put(  0.5f).put ( 0.0f).put(  0.0f).put( 1.0f).put(  0.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put(  0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put( -0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put(  0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put(  0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(0.5f ).put( 0.5f ).put( 0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(0.5f ).put( 0.5f ).put(-0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put(-0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put(-0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put( 0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 0.0f)
            .put(0.5f ).put( 0.5f ).put( 0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put ( 0.0f).put( -1.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put(-0.5f).put( 0.0f).put( -1.0f).put(  0.0f).put(  1.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put( 0.5f).put( 0.0f).put( -1.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(0.5f ).put(-0.5f ).put( 0.5f).put( 0.0f).put( -1.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(-0.5f).put( -0.5f).put(  0.5f).put ( 0.0f).put( -1.0f).put(  0.0f).put(  0.0f).put( 0.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put ( 0.0f).put( -1.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(-0.5f).put(  0.5f).put( -0.5f).put ( 0.0f).put(  1.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(0.5f ).put( 0.5f).put(-0.5f).put( 0.0f).put(  1.0f).put(  0.0f).put(  1.0f).put( 1.0f)
            .put(0.5f).put( 0.5f ).put( 0.5f).put( 0.0f).put(  1.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f ).put( 0.5f).put( 0.0f).put(  1.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put(  0.5f).put ( 0.0f).put(  1.0f).put(  0.0f).put(  0.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put( -0.5f).put(  0.0f).put(  1.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .build();
    public Glyph(Font loader, char c, Vector2f pos) {
        super();
        texture = loader.getGlyphs().get(c);
        model.translate(new Vector3f(pos.x, pos.y, 0));
        shader = Shader.create(
                """
                #version 330
                
                layout (location = 0) in vec3 aPos;
                layout (location = 1) in vec3 aNormal;
                layout (location = 2) in vec2 aTexCoords;
                
                uniform mat4 model;
                uniform mat4 view;
                uniform mat4 projection;
                
                out vec3 Normal;
                out vec2 TexCoords;
                out vec3 FragPos;
                
                void main() {
                    gl_Position = model * vec4(aPos, 1.0);
                    FragPos = vec3(model * vec4(aPos, 1.0));
                    Normal = mat3(transpose(inverse(model))) * aNormal;
                    TexCoords = vec2(aTexCoords.x, 1.0 - aTexCoords.y);
                }
                """,
                """
                #version 330
                
                out vec4 FragColor;
                
                in vec2 TexCoords;
                
                uniform sampler2D tex;
                
                void main() {
                    FragColor = texture(tex, TexCoords);
                }
                """);
    }
    @Override
    public void render(Shader shader) {
        this.shader.use();
        super.render(this.shader);
    }
    @Override
    public void load() {
        polygon = MODEL;
    }
}
