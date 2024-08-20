package net.k3nder.utils.game;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class GlobalTimer {
    public static float deltaTime = 0.0f;
    public static float lastFrame = 0.0f;

    public static void update() {
        float currentFrame = (float) glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;
    }
}
