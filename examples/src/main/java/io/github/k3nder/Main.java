package io.github.k3nder;

import net.k3nder.al.ALContext;
import net.k3nder.al.ALDevice;
import net.k3nder.al.ALSound;
import net.k3nder.al.ALSource;
import net.k3nder.defaults.objects.ui.Text;
import net.k3nder.gl.Camera;
import net.k3nder.defaults.DefaultRes;
import net.k3nder.gl.Window;
import net.k3nder.defaults.objects.ui.Pane;
import net.k3nder.gl.graphic.shader.Shader;
import net.k3nder.gl.graphic.text.Glyph;
import net.k3nder.gl.graphic.visual.Texture;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Main extends Window {
    private List<Cube> blocks = new ArrayList<>();
    private Shader shader;
    private Player player;
    private boolean firstMouse;
    private boolean isEsc;
    private int selectedBlock;
    private int renderingBlocks = 0;
    private Pane chatPane;
    private boolean openChat = false;

    private double gameXCursor, gameYCursor;


    float deltaTime = 0.0f;
    float lastFrame = 0.0f;

    public static void main(String[] args) {
        glfwInit();

        GLFWErrorCallback.createPrint(System.err).set();

        Main w = new Main();

        /* Aqui ya puedes usar funciones de OpenGL */

        w.init();
    }

    public Main() {
        super("hello", 800, 600);

        this.makeContextCurrent();
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);

        blocks = new ArrayList<>();

        blocks.add(new Cube(new Vector3f(0)));


        Cube.texture = DefaultRes.getFont("Roboto/Roboto-Black", 48).renderTextToImage("hello");
        Cube.CUBE.load();
        Glyph.MODEL.load();

        var loader = DefaultRes.getFont("Roboto/Roboto-Black", 100);
        //btn = new Button(new Vector3f(0, 0, -3), new Vector2f(.05f, .05f), Cube.texture, tt, loader);
        //text = new TextField(new Vector3f(-0.98f, -0.98f, -3f), new Vector3f(.05f,.05f, .00002f), tt, loader, id);

        chatPane = new Pane(new Vector3f(0, 0, -5f), new Vector3f(1.999f, 1.999f, 0), Cube.texture);
        chatPane.add(new Text(loader, "hello",new Vector3f(0, 0, 0), new Vector3f(.05f,.05f, .00002f)));

        try {
            player = new Player(new Vector3f(0.0f), Camera.create(width(), height()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        blocks.forEach(v -> v.setTexture(Cube.texture));
        player.setTexture(Cube.texture);
        player.load();
        shader = DefaultRes.getShader("simple");
    }

    @Override
    public void draw() {

        player.getCamera().setHeight(height());
        player.getCamera().setWidth(width());

        float currentFrame = (float) glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        shader.use();


        //light.apply(shader);
        if (openChat) chatPane.render(shader);

        player.getCamera().apply(shader);
        //pointer.render(shader);

        blocks.forEach(v -> v.render(shader));

        //shader.setV3f("color", new Vector3f(1, 0,0));
        player.render(shader);

        selectedBlock = player.getCamera().checks(blocks, 10);
        System.out.println(selectedBlock);
        if (renderingBlocks != blocks.size()) {
            System.out.println("rendering " + blocks.size() + " blocks");
            renderingBlocks = blocks.size();
        }
    }
    @Override
    public void ControlsCallback() {
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
            //System.out.println("acc");
    }
    @Override
    public void CharCallback(long id, int chars) {
        //if (player.getCamera().check(text, 10)) {
        //    System.out.println(chars);
        //    tt += (char) chars;
        //}
    }

    @Override
    public void KeyCallback(long id, int button, int scancode, int ac, int mods) {
        if (button == GLFW_KEY_C && ac == GLFW_PRESS) {
            openChat = true;
            //text.setSelected(true);
        }
        if (button == GLFW_KEY_ESCAPE && ac == GLFW_PRESS) {
            if (openChat) {
                openChat = false;
                glfwSetCursorPos(id, gameXCursor, gameYCursor);
                //text.setSelected(false);
                enableControls();
                enableMousePos();
                glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                return;
            }
            if (isEsc) {
                close();
            }
            isEsc = true;
            glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
    }


     @Override
     public void MouseClickCallback(long id, int button, int action, int mods) {
         if (button == org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT && action == org.lwjgl.glfw.GLFW.GLFW_PRESS) {
             if (selectedBlock != -1) {
                 ALDevice device = new ALDevice();

                 System.out.println("OpenAL device " + device.getID());


                 ALContext context = device.createContext();
                 context.makeContextCurrent();

                 ALCapabilities alCapabilities = device.getCapabilities();

                 if(alCapabilities.OpenAL10) {
                     //OpenAL 1.0 is supported
                     System.out.println("OpenAL 10");
                 }

                 float listenerPos[] = {0.0f, 0.0f, 0.0f};  // X, Y, Z
                 alListenerfv(AL_POSITION, listenerPos);

                 float listenerOri[] = {0.0f, 0.0f, -1.0f,  // At vector (hacia dónde mira)
                         0.0f, 1.0f,  0.0f}; // Up vector (hacia arriba)
                 alListenerfv(AL_ORIENTATION, listenerOri);

                 float listenerVel[] = {0.0f, 0.0f, 0.0f};  // X, Y, Z
                 alListenerfv(AL_VELOCITY, listenerVel);

                 //System.out.println(UBuffer.streamToByteBuffer(new FileInputStream("sound.ogg")));

                 // code play

                 ALSound sound = null;
                 try {
                     sound = ALSound.create("sound_mono.ogg");
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }

                 ALSource source = ALSource.create(sound);

                 System.out.println("pla");

                 source.setFloat(AL_MAX_DISTANCE, 10f);
                 source.setFloat(AL_ROLLOFF_FACTOR, 1.0f);
                 source.setFloat(AL_REFERENCE_DISTANCE, 1.0f);
                 source.setInt(AL_LOOPING, 1);
                 source.setVector3f(AL_POSITION, new Vector3f(11));
                 source.setInt(AL_REFERENCE_DISTANCE, 11);

                 source.play();

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
        if (openChat) return;
        if (firstMouse)
        {
            player.getCamera().setLastX((float) x);
            player.getCamera().setLastY((float) y);
            firstMouse = false;
        }

        gameXCursor = x;
        gameYCursor = y;

        player.getCamera().rotate(x, y);
        player.getCamera().updateRotation();
    }

    @Override
    public void window() {
        super.window();
        windowed();
        glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }
}
class Player extends Cube {
    private Camera camera;
    public Player(Vector3f pos, Camera camera) throws IOException {
        super(pos);
        camera.setPos(pos.x, pos.y, pos.z);
        this.camera = camera;
        load();
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
        load();
    }
    @Override
    public void render(Shader shader) {

        color = new Vector3f((float) Math.sin(glfwGetTime() * 2.0f), (float) Math.sin(glfwGetTime() * 0.7f), (float) Math.sin(glfwGetTime() * 1.3f));

        customShader.use();
        customShader.setVec3("color", color);

        camera.apply(customShader);

        customShader.setMatrix(model, "model");
        polygon.render(GL_TRIANGLES);
        shader.use();
    }
    @Override
    public void load() {
        polygon.load(GL_STATIC_DRAW);
        customShader = DefaultRes.getShader("static_color");
    }
}
class Cube extends net.k3nder.defaults.objects.Cube {
    public static Texture texture;
    //private AABB aabb;
    public Cube(Vector3f pos) {
        super(pos, texture);
        load();
    }
    @Override
    public void Clicked() {
        System.out.println("cc");
    }
}
class Pointer extends Cube {
    private Shader customShader;
    public Pointer() {
        super(new Vector3f(0.0f, 0.0f, 0.0f));
        model.getTranslation(new Vector3f(0));
        model.scale(new Vector3f(0.05f, 0.05f, 0.0f));
        load();
    }
    @Override
    public void load() {
        super.load();
        customShader = DefaultRes.getShader("static_model_color");
    }
    @Override
    public void render(Shader shade) {
        customShader.use();
        customShader.setVec3("color", model.getTranslation(new Vector3f(1)));
        super.render(customShader);
    }
}
class PlaySound {


}
