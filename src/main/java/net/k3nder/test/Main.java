package net.k3nder.test;

import net.k3nder.gl.Camera;
import net.k3nder.gl.Window;
import net.k3nder.gl.graphic.shader.Shader;
import net.k3nder.gl.graphic.shader.Shaders;
import net.k3nder.gl.graphic.visual.Texture;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Main extends Window {
    private List<Cube> blocks = new ArrayList<>();
    private Pointer pointer;
    private Shader shader;
    private Player player;
    private boolean firstMouse;
    private boolean isEsc;
    private int selectedBlock;
    private int renderingBlocks = 0;



    float deltaTime = 0.0f;
    float lastFrame = 0.0f;

    public static void main(String[] args) {
        new Main().init();
    }

    public Main() {
        super(800, 900, "hello");
        blocks = new ArrayList<>();
        blocks.add(new Cube(new Vector3f(0)));
    }
    @Override
    public void createComponents() {
        try {

            pointer = new Pointer();
            player = new Player(new Vector3f(0.0f), Camera.create(WIDTH, HEIGHT));
            //lightCube = new LightCube(new Vector3f(3.0f), new Vector3f(1, 0, 0), player.getCamera());
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

        blocks.forEach(v -> v.render(shader));

        //shader.setV3f("color", new Vector3f(1, 0,0));
        player.render(shader);
        pointer.render(shader);

        selectedBlock = player.getCamera().checks(blocks, 10);
        if (renderingBlocks != blocks.size()) {
            System.out.println("rendering " + blocks.size() + " blocks");
            renderingBlocks = blocks.size();
        }
    }

    @Override
    public void initComponents() {
        Cube.texture.init();
        pointer.init();
        blocks.forEach(Cube::init);
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
         if (button == org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT && action == org.lwjgl.glfw.GLFW.GLFW_PRESS) {
             if (selectedBlock != -1) {
                 Vector3f pos = new Vector3f();
                 blocks.get(selectedBlock).getModel().getTranslation(pos);

                 Vector3f cameraPos = new Vector3f(player.getCamera().getPos());
                 Vector3f cameraDir = new Vector3f(player.getCamera().getCameraFront()).normalize();

                 // Coordenadas mínimas y máximas del cubo seleccionado
                 Vector3f max = blocks.get(selectedBlock).getMax();
                 Vector3f min = blocks.get(selectedBlock).getMin();

                 // Variables para guardar el t (factor de escala del rayo)
                 float tMin = Float.NEGATIVE_INFINITY;
                 float tMax = Float.POSITIVE_INFINITY;

                 // Calcular la intersección del rayo con el AABB (cubo)
                 for (int i = 0; i < 3; i++) {
                     float invD = 1.0f / cameraDir.get(i);
                     float t0 = (min.get(i) - cameraPos.get(i)) * invD;
                     float t1 = (max.get(i) - cameraPos.get(i)) * invD;
                     if (invD < 0.0f) {
                         float temp = t0;
                         t0 = t1;
                         t1 = temp;
                     }
                     tMin = Math.max(tMin, t0);
                     tMax = Math.min(tMax, t1);
                     if (tMax <= tMin) {
                         return; // No hay intersección
                     }
                 }

                 // Punto de intersección
                 Vector3f hitPoint = new Vector3f(cameraPos).add(new Vector3f(cameraDir).mul(tMin));

                 // Determinar la cara que fue seleccionada
                 if (Math.abs(hitPoint.x - max.x) < 0.001f) {
                     System.out.println("right face of the cube");
                     pos.add(new Vector3f(1, 0, 0));
                 } else if (Math.abs(hitPoint.x - min.x) < 0.001f) {
                     System.out.println("left face of the cube");
                     pos.add(new Vector3f(-1, 0, 0));
                 } else if (Math.abs(hitPoint.y - max.y) < 0.001f) {
                     System.out.println("top face of the cube");
                     pos.add(new Vector3f(0, 1, 0));
                 } else if (Math.abs(hitPoint.y - min.y) < 0.001f) {
                     System.out.println("bottom face of the cube");
                     pos.add(new Vector3f(0, -1, 0));
                 } else if (Math.abs(hitPoint.z - max.z) < 0.001f) {
                     System.out.println("front face of the cube");
                     pos.add(new Vector3f(0, 0, 1));
                 } else if (Math.abs(hitPoint.z - min.z) < 0.001f) {
                     System.out.println("back face of the cube");
                     pos.add(new Vector3f(0, 0, -1));
                 } else {
                     System.out.println("no selected block");
                     return;
                 }

                 System.out.println("last " + blocks.size());

                 Cube block = new Cube(pos);
                 block.init();
                 blocks.add(0, block);

                 System.out.println("now " + blocks.size());

                 selectedBlock = player.getCamera().checks(blocks, 10);

                 System.out.println(selectedBlock);

                 //selectedBlock = blocks.size();
             }
         } else if (button == GLFW_MOUSE_BUTTON_RIGHT && action == org.lwjgl.glfw.GLFW.GLFW_PRESS) {
             selectedBlock = player.getCamera().checks(blocks, 10);
            if (selectedBlock != -1) {
                blocks.remove(selectedBlock);
                System.out.println(selectedBlock);
            }
         }
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
class Cube extends net.k3nder.gl.graphic.objects.Cube {
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
class Pointer extends Cube {
    private Shader customShader;
    public Pointer() {
        super(new Vector3f(0.0f, 0.0f, 0.0f));
        model.getTranslation(new Vector3f(0));
        model.scale(new Vector3f(0.05f, 0.05f, 0.0f));
    }
    @Override
    public void init() {
        super.init();
        customShader = Shaders.getDefaultShader("static_model_color");
    }
    @Override
    public void render(Shader shade) {
        customShader.use();
        customShader.setVec3("color", model.getTranslation(new Vector3f(1)));
        super.render(customShader);
    }
}