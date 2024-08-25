package net.k3nder.gl;

import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import net.k3nder.glfw.GLFWMonitor;
import net.k3nder.glfw.GLFWWindow;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class Window implements Initializable {
    protected final GLFWWindow native_window;
    private boolean disableControls;

    public Window(String title, int width, int height) {
        native_window = new GLFWWindow(width, height, title, GLFWMonitor.primary(), NULL); 
        window();
        show();
    }

    public Window(String title) {
        GLFWMonitor primaryMotor = GLFWMonitor.primary();
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        native_window = new GLFWWindow(vidmode.width(), vidmode.height(), title, primaryMotor, NULL);
        window();
        show();
    }
    public Window(String title, GLFWMonitor primaryMotor) {
        GLFWVidMode vidmode = primaryMotor.getVideoMode();
        native_window = new GLFWWindow(vidmode.width(), vidmode.height(), title, primaryMotor, NULL);
        window();
        show();
    }
    public Window(String title, GLFWMonitor primaryMotor, GLFWVidMode vidmode) {
        native_window = new GLFWWindow(vidmode.width(), vidmode.height(), title, primaryMotor, NULL);
        window();
        show();
    }

    public void init() {
        while (!native_window.shouldClose())
            loop();
        cleanup();
        glfwTerminate();
    }
    private void loop() {
        if(!disableControls) ControlsCallback();

        draw();

        native_window.swapBuffers();
        native_window.pollEvents();
    }
    public void draw() {

    }
    public void window() {
        native_window.makeContextCurrent();
        native_window.setFramebufferSizeCallback(this::BufferSizeCallback);
        native_window.setCursorPosCallback(this::MousePosCallback);
        native_window.setScrollCallback(this::MouseScrollCallback);
        native_window.setMouseButtonCallback(this::MouseClickCallback);
        native_window.setKeyCallback(this::KeyCallback);
        native_window.setCharCallback(this::CharCallback);
        native_window.setRefreshCallback(this::RefreshCallback);
        native_window.setCloseCallback(this::CloseCallback);
        native_window.setFocusCallback(this::FocusCallback);
    }

    public void KeyCallback(long id, int key, int scancode, int action, int mods) {}
    public void MousePosCallback(long id, double x, double y) {}
    public void MouseClickCallback(long id, int button, int action, int mods) {}
    public void MouseScrollCallback(long id, double x, double y) {}
    public void RefreshCallback(long id) {}
    public void CloseCallback(long id) {}
    public void FocusCallback(long id, boolean focus) {}
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
        native_window.close();
    }
    public final void hide() {
        native_window.hide();
    }
    public final void show() {
        native_window.show();
    }
    public final void destroy() {
        native_window.destroy();
    }
    public void disableControls() {
        disableControls = true;
    }
    public void enableControls() {
        disableControls = false;

    }
    public void disableKey() {
        native_window.setKeyCallback(null);
    }
    public void enableKey() {
        native_window.setKeyCallback(this::KeyCallback);
    }
    public void disableChar() {
        native_window.setCharCallback(null);
    }
    public void enableChar() {
        native_window.setCharCallback(this::CharCallback);
    }
    public void disableMousePos() {
        native_window.setCursorPosCallback(null);
    }
    public void enableMousePos() {
        native_window.setCursorPosCallback(this::MousePosCallback);
    }
    public void disableMouseScroll() {
        native_window.setScrollCallback(null);
    }
    public void enableMouseScroll() {
        native_window.setScrollCallback(this::MouseScrollCallback);
    }
    public void disableMouseClick() {
        native_window.setMouseButtonCallback(null);
    }
    public void enableMouseClick() {
        native_window.setMouseButtonCallback(this::MouseClickCallback);
    }
    public final void fullscreen() {
        GLFWMonitor motor = GLFWMonitor.primary();
        GLFWVidMode vidmode = motor.getVideoMode();

        native_window.setMonitor(motor, 0, 0, vidmode.width(), vidmode.height(), GLFW_DONT_CARE);
    }
    public final void windowed() {
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Configurar el tamaño y la posición de la ventana en modo ventana
        native_window.setMonitor(new GLFWMonitor(NULL),
                (vidMode.width() - width()) / 2,
                (vidMode.height() - height()) / 2,
                width(), height(),
                GLFW_DONT_CARE);
    }
    public final int width() {
        return native_window.getSize().x;
    }
    public final int height() {
       return native_window.getSize().y; 
    }

    public final void setWindowPos(int x, int y) {
    	native_window.setPos(x, y);
    }
    public final void setWindowSize(int width, int height) {
        native_window.setSize(width, height);
    }
    public final void setWindowSizeLimits(int width, int height) {
        native_window.setSizeLimits(width, height, width(), height());
    }
    public final void setWindowAspectRatio(int width, int height) {
        native_window.setAspectRatio(width, height);
    }
    public final void setWindowMonitor(GLFWMonitor monitor, int xpos, int ypox, int width, int height, int refreshRate) {
        native_window.setMonitor(monitor, xpos, ypox, width, height, refreshRate);
    }
    public final Vector2i getFramebufferSize() {
        return native_window.getFramebufferSize();
    }
    public final void setTitle(String title) {
        native_window.setTitle(title);
    }
    public final void setIcon(GLFWImage.Buffer icon) {
        native_window.setIcon(icon);
    }
    public final void setOpacity(float opacity) {
        native_window.setOpacity(opacity);
    }
    public final void setUserPointer(long userPointer) {
        native_window.setUserPointer(userPointer);
    }
    public final long getUserPointer() {
        return native_window.getUserPointer();
    }
    public final Vector2i getPos() {
        return native_window.getPos();
    }
    public final Vector2i getSize() {
        return native_window.getSize();
    }
    public final void minimize() {
       native_window.iconify();
    }
    public final void restore() {
       native_window.restore();
    }
    public final void swapInterval(int interval) {
        native_window.swapInterval(interval);
    }
    public final void makeContextCurrent() { native_window.makeContextCurrent(); }
}
