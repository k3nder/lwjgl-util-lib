package net.k3nder.glfw;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.IntBuffer;

import org.joml.Vector2i;
import org.joml.Vector4i;

public class GLFWMonitor {
	private final long id;
	public GLFWMonitor(long id) {
		this.id = id;
	}
	public static GLFWMonitor primary() {
		long primary = glfwGetPrimaryMonitor();
		return new GLFWMonitor(primary);
	}
	public static GLFWMonitor[] monitors() {
		PointerBuffer nmonitors = glfwGetMonitors();
		GLFWMonitor[] result = new GLFWMonitor[nmonitors.capacity()];
		for (int i = 0; i < nmonitors.capacity(); i++) {
			result[i] = new GLFWMonitor(nmonitors.get(i));
		}
		return result;
	}
	public String getName() {
		return glfwGetMonitorName(id);
	}
	public Vector2i getPhysicalSize() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer width = stack.mallocInt(1);
			IntBuffer height = stack.mallocInt(1);
			glfwGetMonitorPhysicalSize(id, width, height);
			return new Vector2i(width.get(0), height.get(0));
		}
	}
	public Vector4i getWorkarea() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer xpos = stack.mallocInt(1);
			IntBuffer ypos = stack.mallocInt(1);
			IntBuffer width = stack.mallocInt(1);
			IntBuffer height = stack.mallocInt(1);
			glfwGetMonitorWorkarea(id, xpos, ypos, width, height);
			return new Vector4i(xpos.get(0), ypos.get(0), width.get(0), height.get(0));
		}
	}
	public GLFWVidMode getVideoMode() {
		return glfwGetVideoMode(id);
	}
	public long id() {
		return id;
	}
}
