package net.k3nder.gl;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class Window implements Initializable {
    protected Long id;
    protected final Integer WIDTH, HEIGHT;
    protected String title;

    public Window(Integer width, Integer height, String title) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.title = title;
        createComponents();
    }

    public void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        GLFWSetup();

        id = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);
        if (id == NULL) {
            throw new IllegalStateException("Unable to create GLFW window");
        }
        windowConf();
        glfwShowWindow(id);
        GLSetup();

        while (!glfwWindowShouldClose(id))
            loop();
        cleanup();
        glfwTerminate();
    }
    private void loop() {
        ControlsCallback();

        draw();

        glfwSwapBuffers(id);
        glfwPollEvents();
    }
    public void draw() {

    }
    public void initComponents() {

    }
    public void createComponents() {}
    public void windowConf() {
        glfwMakeContextCurrent(id);
        glfwSetFramebufferSizeCallback(id, this::bufferSizeCallback);
        glfwSetCursorPosCallback(id, this::MousePosCallback);
        glfwSetScrollCallback(id, this::MouseScrollCallback);
        glfwSetMouseButtonCallback(id, this::MouseClickCallback);
        glfwSetKeyCallback(id, this::KeyCallback);
        glfwSetCharCallback(id, this::CharCallback);
        glfwSwapInterval(GLFW_TRUE);
    }

    public void KeyCallback(long id, int key, int scancode, int action, int mods) {}
    public void MousePosCallback(long id, double x, double y) {}
    public void MouseClickCallback(long id, int button, int action, int mods) {}
    public void MouseScrollCallback(long id, double x, double y) {}
    public void GLSetup() {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        initComponents();
    }
    public void GLFWSetup() {
        glfwDefaultWindowHints();
    }
    public void cleanup() {

    }
    protected void bufferSizeCallback(long window, int width, int height) {
        glViewport(0,0, width, height);
    }
    public void ControlsCallback() {

    }
    public void CharCallback(long id, int character) {

    }
    public final void close() {
        glfwSetWindowShouldClose(id, true);
    }
    public final void hide() {
        glfwHideWindow(id);
    }
    public final void show() {
        glfwShowWindow(id);
    }
    public final void destroy() {
        glfwDestroyWindow(id);
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
