package net.k3nder.test;

import net.k3nder.gl.Camera;
import net.k3nder.gl.GraphicalObject;
import net.k3nder.gl.Window;
import net.k3nder.gl.shader.Shader;
import net.k3nder.gl.shader.Shaders;
import net.k3nder.gl.visual.Texture;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Main extends Window {
    private GraphicalObject cube;
    private GraphicalObject cube2;
    private GraphicalObject lightCube;
    private Shader shader;
    private Player player;
    private boolean firstMouse;
    private boolean isEsc;



    float deltaTime = 0.0f;
    float lastFrame = 0.0f;

    public static void main(String[] args) {
        new Main().init();
    }

    public Main() {
        super(800, 900, "hello");
    }
    @Override
    public void createComponents() {
        try {


            cube = new Cube(new Vector3f(0.0f));
            cube2 = new Cube(new Vector3f(1.0f));

            player = new Player(new Vector3f(0.0f), Camera.create(WIDTH, HEIGHT));
            lightCube = new LightCube(new Vector3f(3.0f), new Vector3f(1, 0, 0), player.getCamera());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw() {

        float currentFrame = (float) glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        shader.use();

        //light.apply(shader);


        player.getCamera().apply(shader);
        //pointer.render(shader);

        lightCube.render(shader);

        //shader.setV3f("color", new Vector3f(1, 0,0));
        player.render(shader);
        cube.render(shader);
        cube2.render(shader);

        List<GraphicalObject> object = new ArrayList<>();
        object.add(cube);
        object.add(cube2);
        object.add(lightCube);

        System.out.println(player.getCamera().checks(object, 10));
    }

    @Override
    public void initComponents() {
        Cube.texture.init();
        cube.init();
        cube2.init();
        lightCube.init();
        player.init();
        shader = Shaders.getDefaultShader("simple");
    }

    @Override
    public void ControlsCallback() {
            if (isEsc) return;
            if (glfwGetKey(id, GLFW_KEY_W) == GLFW_PRESS) {
                player.move(Camera.Direction.FRONT, deltaTime);
            }
            if (glfwGetKey(id, GLFW_KEY_A) == GLFW_PRESS) {
                player.move(Camera.Direction.LEFT,  deltaTime);
            }
            if (glfwGetKey(id, GLFW_KEY_D) == GLFW_PRESS) {
                player.move(Camera.Direction.RIGHT, deltaTime);
            }
            if (glfwGetKey(id, GLFW_KEY_S) == GLFW_PRESS) {
                player.move(Camera.Direction.BACK, deltaTime);
            }
            if (glfwGetKey(id, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
                isEsc = true;
                glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            }
            //System.out.println("acc");
    }

     @Override
     public void MouseClickCallback(long id, int button, int action, int mods) {
        if(cube.getSelected()) cube.Clicked();
        if(cube2.getSelected()) cube2.Clicked();
        if(lightCube.getSelected()) lightCube.Clicked();
     }

    @Override
    public void MousePosCallback(long id, double x, double y) {
        if (isEsc) return;
        if (firstMouse)
        {
            player.getCamera().setLastX((float) x);
            player.getCamera().setLastY((float) y);
            firstMouse = false;
        }

        player.getCamera().rotate(x, y);
        player.getCamera().updateRotation();
    }

    @Override
    public void windowConf() {
        super.windowConf();
        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }
}
class Player extends Cube {
    private Camera camera;
    public Player(Vector3f pos, Camera camera) throws IOException {
        super(pos);
        camera.setPos(pos.x, pos.y, pos.z);
        this.camera = camera;
    }
    public void move(Camera.Direction direction, float delta) {
        camera.move(direction, delta);
    }
    @Override
    public void render(Shader shader) {
        camera.apply(shader);
        model.translate(camera.getPos());
        model.scale(10);
        super.render(shader);
    }
    public Camera getCamera() {
        return camera;
    }
}
class LightCube extends Cube {

    private Shader customShader;
    private Vector3f color;
    private Camera camera;
    public LightCube(Vector3f pos, Vector3f col, Camera cam) {
        super(pos);
        color = col;
        camera = cam;
    }
    @Override
    public void render(Shader shader) {

        color = new Vector3f((float) Math.sin(glfwGetTime() * 2.0f), (float) Math.sin(glfwGetTime() * 0.7f), (float) Math.sin(glfwGetTime() * 1.3f));

        customShader.use();
        customShader.setVec3("color", color);

        camera.apply(customShader);

        customShader.setMatrix(model, "model");
        polygon.draw(GL_TRIANGLES);
        shader.use();
    }
    @Override
    public void init() {
        polygon.create(GL_STATIC_DRAW);
        customShader = Shaders.getDefaultShader("static_color");
    }
}
class Cube extends net.k3nder.gl.objects.Cube {
    public static final Texture texture =
            Texture.builder()
                    .colorChanel(GL_RGB)
                    .flipV()
                    .configuration(GL_TEXTURE_WRAP_S, GL_REPEAT)
                    .configuration(GL_TEXTURE_WRAP_T, GL_REPEAT)
                    .configuration(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                    .configuration(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                    .source(Main.class.getResourceAsStream("/conatiner.jpg"))
                    .create();
    public Cube(Vector3f pos) {
        super(pos, texture);
    }
    @Override
    public void init() {
        polygon.create(GL_STATIC_DRAW);
    }
}