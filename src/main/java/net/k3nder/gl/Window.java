package net.k3nder.gl;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

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

        long primaryMotor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        id = glfwCreateWindow(vidmode.width(), vidmode.height(), title, primaryMotor, NULL);

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
        glfwSetWindowRefreshCallback(id, this::RefreshCallback);
        glfwSetWindowCloseCallback(id, this::CloseCallback);
        glfwSetWindowFocusCallback(id, this::FocusCallback);
    }

    public void KeyCallback(long id, int key, int scancode, int action, int mods) {}
    public void MousePosCallback(long id, double x, double y) {}
    public void MouseClickCallback(long id, int button, int action, int mods) {}
    public void MouseScrollCallback(long id, double x, double y) {}
    public void RefreshCallback(long id) {}
    public void CloseCallback(long id) {}
    public void FocusCallback(long id, boolean focus) {}
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
        glfwSetCursorPosCallback(id, null);
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
    public final void fullscreen() {
        long motor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(motor);

        glfwSetWindowMonitor(id, motor, 0, 0, vidmode.width(), vidmode.height(), GLFW_DONT_CARE);
    }
    public final void windowed() {
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Configurar el tamaño y la posición de la ventana en modo ventana
        glfwSetWindowMonitor(id, NULL,
                (vidMode.width() - WIDTH) / 2,
                (vidMode.height() - HEIGHT) / 2,
                WIDTH, HEIGHT,
                GLFW_DONT_CARE);
    }
    public final int width() {
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(id, width, height);
        return width[0];
    }
    public final int height() {
        int[] height = new int[1];
        int[] width = new int[1];
        glfwGetWindowSize(id, width, height);
        return height[0];
    }

    public final void setWindowPos(int x, int y) {
        glfwSetWindowPos(id, x, y);
    }
    public final void setWindowSize(int width, int height) {
        glfwSetWindowSize(id, width, height);
    }
    public final void setWindowSizeLimits(int width, int height) {
        glfwSetWindowSizeLimits(id, width, height, WIDTH, HEIGHT);
    }
    public final void setWindowAspectRatio(int width, int height) {
        glfwSetWindowAspectRatio(id, width, height);
    }
    public final void setWindowMonitor(long monitor, int xpos, int ypox, int width, int height, int refreshRate) {
        glfwSetWindowMonitor(id, monitor, xpos, ypox, width, height, refreshRate);
    }
    public final long getWindowMonitor() {
        return glfwGetWindowMonitor(id);
    }
    public final void getFramebufferSize(IntBuffer width, IntBuffer height) {
        glfwGetFramebufferSize(id, width, height);
    }
    public final void setTitle(String title) {
        glfwSetWindowTitle(id, title);
    }
    public final void setIcon(GLFWImage.Buffer icon) {
        glfwSetWindowIcon(id, icon);
    }
    public final void setOpacity(float opacity) {
        glfwSetWindowOpacity(id, opacity);
    }
    public final void setUserPointer(long userPointer) {
        glfwSetWindowUserPointer(id, userPointer);
    }
    public final long getUserPointer() {
        return glfwGetWindowUserPointer(id);
    }
    public final void getPos(IntBuffer w, IntBuffer h) {
        glfwGetWindowPos(id, w, h);
    }
    public final void getSize(IntBuffer w, IntBuffer h) {
        glfwGetWindowSize(id, w, h);
    }
    public final void minimize() {
        glfwIconifyWindow(id);
    }
    public final void restore() {
        glfwRestoreWindow(id);
    }
    public final void swapInterval(int interval) {
        glfwSwapInterval(interval);
    }
}
