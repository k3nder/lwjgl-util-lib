package net.k3nder.gl.graphic;

import net.k3nder.gl.Camera;
import net.k3nder.gl.Initializable;
import net.k3nder.gl.Reloadable;
import net.k3nder.gl.Renderable;
import net.k3nder.gl.graphic.model.Polygon;
import net.k3nder.gl.graphic.shader.Shader;
import net.k3nder.gl.graphic.visual.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public abstract class GraphicalObject implements Renderable<Shader>, Reloadable {
    protected Polygon polygon;
    protected Texture texture;
    protected Matrix4f model;
    protected Boolean selected;

    public GraphicalObject() {
        model = new Matrix4f();
    }

    public void render(Shader shader) {
        shader.use();
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        shader.setI("tex", texture.getID());

        shader.setMatrix(model, "model");
        polygon.render(GL_TRIANGLES);
    }
    public void clean() {
        polygon.clean();
    }
    public void load() {
        polygon.load();
    }
    public Vector3f[] getTransformedVertices() {
        Vector3f[] vertices = {
                new Vector3f(-0.5f, -0.5f, -0.5f),
                new Vector3f(0.5f, -0.5f, -0.5f),
                new Vector3f(0.5f, 0.5f, -0.5f),
                new Vector3f(-0.5f, 0.5f, -0.5f),
                new Vector3f(-0.5f, -0.5f, 0.5f),
                new Vector3f(0.5f, -0.5f, 0.5f),
                new Vector3f(0.5f, 0.5f, 0.5f),
                new Vector3f(-0.5f, 0.5f, 0.5f)
        };

        Vector3f[] transformedVertices = new Vector3f[8];
        for (int i = 0; i < 8; i++) {
            transformedVertices[i] = vertices[i].mulPosition(model, new Vector3f());
        }
        return transformedVertices;
    }
    public Vector3f getMin() {
        Vector3f min = new Vector3f(Float.POSITIVE_INFINITY);
        for (Vector3f v : getTransformedVertices()) {
            min.min(v);
        }
        return min;
    }

    public Vector3f getMax() {
        Vector3f max = new Vector3f(Float.NEGATIVE_INFINITY);
        for (Vector3f v : getTransformedVertices()) {
            max.max(v);
        }
        return max;
    }
    public void checkCameraPointer(Camera camera) {
        if (camera.check(this, 100)) {
            if (!lastSelected) {
                selected = true;
                lastSelected = true;
                Selected();
            }
        } else {
            selected = false;
            if (lastSelected) {
                Unselected();
                lastSelected = false;
            }
        }
    }
    private boolean lastSelected;
    public void Selected() {}
    public void Unselected() {}
    public void Clicked() {}

    public Boolean getSelected() {
        return selected;
    }

    public GraphicalObject setSelected(Boolean selected) {
        this.selected = selected;
        return this;
    }

    public Matrix4f getModel() {
        return model;
    }

    public GraphicalObject setModel(Matrix4f model) {
        this.model = model;
        return this;
    }

    public Texture getTexture() {
        return texture;
    }

    public GraphicalObject setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public GraphicalObject setPolygon(Polygon polygon) {
        this.polygon = polygon;
        return this;
    }
}
