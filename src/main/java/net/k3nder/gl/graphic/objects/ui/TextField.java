package net.k3nder.gl.graphic.objects.ui;

import net.k3nder.gl.graphic.GraphicalObject;
import net.k3nder.gl.graphic.shader.Shader;
import net.k3nder.gl.graphic.text.FontLoader;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;

public class TextField extends Text {
    private boolean selected = false;
    private Long window;
    private boolean firstChar = true;
    public TextField(Vector2f pos, Vector3f size, String placeText, FontLoader font, long winID) throws IOException, FontFormatException {
        super(font, placeText, pos, size);
        window = winID;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isSelected() {
        return selected;
    }
    @Override
    public void render(Shader shader) {
        if (selected) glfwSetCharCallback(window, this::CharCallback);
        super.render(shader);
    }
    private void CharCallback(long id, int chars) {
        if (firstChar) {
            text = "";
            firstChar = false;
        }
        text += (char) chars;
    }
    public String getText() {
        return text;
    }
}
