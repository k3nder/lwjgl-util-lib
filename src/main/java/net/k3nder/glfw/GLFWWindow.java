package net.k3nder.glfw; 

import static org.lwjgl.glfw.GLFW.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallback;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class GLFWWindow {
	private final long id;
	public GLFWWindow(int width, int height, ByteBuffer title, GLFWMonitor monitor, long share) {
		id = glfwCreateWindow(width, height, title, monitor.id(), share);
		if (id == MemoryUtil.NULL) {
				
			GLFWError err = GLFWError.error();
			throw new RuntimeException("GLFW Error on creating window: " + err.name() + "\n\n" + err.description());
		}
	}
	public GLFWWindow(int width, int height, CharSequence title, GLFWMonitor monitor, long share) {
		id = glfwCreateWindow(width, height, title, monitor.id(), share);
		if (id == MemoryUtil.NULL) {
			GLFWError err = GLFWError.error();
			throw new RuntimeException("GLFW Error on creating window: " + err.name() + "\n\n" + err.description());
		}
	}
	public void makeContextCurrent() {
		glfwMakeContextCurrent(id);
	}
	public void show() {
		glfwShowWindow(id);
	}
	public void hide() {
		glfwHideWindow(id);
	}
	public boolean shouldClose() {
		return glfwWindowShouldClose(id);
	}
	public void swapBuffers() {
		glfwSwapBuffers(id);
	}
	public void destroy() {
		glfwDestroyWindow(id);
	}
	public void setTitle(CharSequence title) {
		glfwSetWindowTitle(id, title);
	}
	public void setSize(int width, int height) {
		glfwSetWindowSize(id, width, height);
	}
	public void setPos(int xPos, int yPos) {
		glfwSetWindowPos(id, xPos, yPos);
	}
	public void setAspectRatio(int numer, int demon) {
		glfwSetWindowAspectRatio(id, numer, demon);
	}
	public void setMonitor(GLFWMonitor monitor, int xpos, int ypos, int width, int height, int refreshRate) {
		glfwSetWindowMonitor(id, monitor.id(), xpos, ypos, width, height, refreshRate);
	}
	public Vector2i getSize() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer width = stack.mallocInt(1);
			IntBuffer height = stack.mallocInt(1);
			glfwGetWindowSize(id, width, height);
			return new Vector2i(width.get(0),height.get(0));
		}
	}
	public Vector2i getPos() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer xpos = stack.mallocInt(1);
			IntBuffer ypos = stack.mallocInt(1);
			glfwGetWindowPos(id, xpos, ypos);
			return new Vector2i(xpos.get(0),ypos.get(0));
		}
	}
	public Vector2i getFramebufferSize() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer width = stack.mallocInt(1);
			IntBuffer height = stack.mallocInt(1);
			glfwGetFramebufferSize(id, width, height);
			return new Vector2i(width.get(0),height.get(0));
		}
	}
	public Vector4i getFrameSize() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer left = stack.mallocInt(1);
			IntBuffer top = stack.mallocInt(1);
			IntBuffer right = stack.mallocInt(1);
			IntBuffer bottom = stack.mallocInt(1);
			glfwGetWindowFrameSize(id, left, top, right, bottom);
			return new Vector4i(left.get(0), top.get(0), right.get(0), bottom.get(0));
		}
	}
	public Vector2f getContentScale() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer xscale = stack.mallocFloat(1);
			FloatBuffer yscale = stack.mallocFloat(1);
			glfwGetWindowContentScale(id, xscale, yscale);
			return new Vector2f(xscale.get(0),yscale.get(0));
		}
	}
	public void setUserPointer(long pointer) {
		glfwSetWindowUserPointer(id, pointer);
	}
	public long getUserPointer() {
		return glfwGetWindowUserPointer(id);
	}
	public void swapInterval(int interval) {
		glfwSwapInterval(interval);
	}
	public void pollEvents() {
		glfwPollEvents();
	}
	public void waitEvents() {
		glfwWaitEvents();
	}
	public void waitEvents(double timeout) {
		glfwWaitEventsTimeout(timeout);
	}
	public void setSizeCallback(GLFWWindowSizeCallbackI callback) {
		glfwSetWindowSizeCallback(id, callback);
	}
	public void setPosCallback(GLFWWindowPosCallbackI callback) {
		glfwSetWindowPosCallback(id, callback);
	}
	public void setCloseCallback(GLFWWindowCloseCallbackI callback) {
		glfwSetWindowCloseCallback(id, callback);
	}
	public void setRefreshCallback(GLFWWindowRefreshCallbackI callback) {
		glfwSetWindowRefreshCallback(id, callback);
	}
	public void setFocusCallback(GLFWWindowFocusCallbackI callback) {
		glfwSetWindowFocusCallback(id, callback);
	}
	public void setIconifyCallback(GLFWWindowIconifyCallbackI callback) {
		glfwSetWindowIconifyCallback(id, callback);
	}
	public void setMaximizeCallback(GLFWWindowMaximizeCallbackI callback) {
		glfwSetWindowMaximizeCallback(id, callback);
	}
	public void setContentScaleCallback(GLFWWindowContentScaleCallbackI callback) {
		glfwSetWindowContentScaleCallback(id, callback);
	}
	public void setKeyCallback(GLFWKeyCallbackI callback) {
		glfwSetKeyCallback(id, callback);
	}
	public void setMouseButtonCallback(GLFWMouseButtonCallbackI callback) {
		glfwSetMouseButtonCallback(id, callback);
	}
	public void setCursorPosCallback(GLFWCursorPosCallbackI callback) {
		glfwSetCursorPosCallback(id, callback);
	}
	public void setScrollCallback(GLFWScrollCallbackI callback) {
		glfwSetScrollCallback(id, callback);
	}
	public void setCharCallback(GLFWCharCallbackI callback) {
		glfwSetCharCallback(id, callback);
	}
	public void setFramebufferSizeCallback(GLFWFramebufferSizeCallbackI callback) {
		glfwSetFramebufferSizeCallback(id, callback);
	}
	public void close() {
		glfwSetWindowShouldClose(id, true);
	}
	public void setSizeLimits(int width, int height, int width2, int height2) {
		glfwSetWindowSizeLimits(id, width, height, width2, height2);
	}
	public void setIcon(GLFWImage.Buffer img) {
		glfwSetWindowIcon(id, img);
	}
	public void setOpacity(float opacity) {
		glfwSetWindowOpacity(id, opacity);
	}
	public void iconify() {
		glfwIconifyWindow(id);
	}
	public void restore() {
		glfwRestoreWindow(id);
	}
	public int key(int key) {
		return glfwGetKey(id, key);
	}
	public void setInputMode(int t, int i) {
		glfwSetInputMode(id, t, i);
	}
}
