package net.k3nder.gl;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class Window implements Initializable {
    protected Long id;
    protected final Integer WIDTH, HEIGHT;
    protected String title;
    private boolean disableControls;

    public Window(Integer width, Integer height, String title) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.title = title;
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
        if(!disableControls) ControlsCallback();

        draw();

        glfwSwapBuffers(id);
        glfwPollEvents();
    }
    public void draw() {

    }
    public void components() {

    }
    public void windowConf() {
        glfwMakeContextCurrent(id);
        glfwSetFramebufferSizeCallback(id, this::BufferSizeCallback);
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
        components();
    }
    public void GLFWSetup() {
        glfwDefaultWindowHints();
    }
    public void cleanup() {

    }
    protected void BufferSizeCallback(long window, int width, int height) {
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
    public void disableControls() {
        disableControls = true;
    }
    public void enableControls() {
        disableControls = false;

    }
    public void disableKey() {
        glfwSetKeyCallback(id, null);
    }
    public void enableKey() {
        glfwSetKeyCallback(id, this::KeyCallback);
    }
    public void disableChar() {
        glfwSetCharCallback(id, null);
    }
    public void enableChar() {
        glfwSetCharCallback(id, this::CharCallback);
    }
    public void disableMousePos() {
        glfwSetMouseButtonCallback(id, null);
    }
    public void enableMousePos() {
        glfwSetCursorPosCallback(id, this::MousePosCallback);
    }
    public void disableMouseScroll() {
        glfwSetScrollCallback(id, null);
    }
    public void enableMouseScroll() {
        glfwSetScrollCallback(id, this::MouseScrollCallback);
    }
    public void disableMouseClick() {
        glfwSetMouseButtonCallback(id, null);
    }
    public void enableMouseClick() {
        glfwSetMouseButtonCallback(id, this::MouseClickCallback);
    }
}
