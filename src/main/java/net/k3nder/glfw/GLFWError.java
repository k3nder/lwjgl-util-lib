package net.k3nder.glfw;

import org.lwjgl.glfw.GLFW;

public enum GLFWError {
	GLFW_NO_ERROR("No error has occurred"),
	GLFW_NOT_INITIALIZED("This occurs if a GLFW function was called that must not be called unless the library is initialize"),
	GLFW_NOT_CURRENT_CONTEXT("This occurs if a GLFW function was called that needs and operates on the current OpenGL or OpenGL ES context but no context is current on the calling thread. One such function is glfwSwapInterval"),
	GLFW_INVALID_ENUM("One of the arguments to the function was an invalid enum value, for example requesting GLFW_RED_BITS with glfwGetWindowAttrib"),
	GLFW_INVALID_VALUE("""
			One of the arguments to the function was an invalid value, for example requesting a non-existent OpenGL or OpenGL ES version like 2.7
			Requesting a valid but unavailable OpenGL or OpenGL ES version will instead result in a GLFW_VERSION_UNAVAILABLE error
			"""),
	GLFW_OUT_OF_MEMORY("A memory allocation failed"),
	GLFW_API_UNAVZILABLE("GLFW could not find support for the requested API on the system"),
	GLFW_VERSION_UNAVAIABLE("The requested OpenGL or OpenGL ES version (including any requested context or framebuffer hints) is not available on this machine"),
	GLFW_PLATFORM_ERROR("A platform-specific error occurred that does not match any of the more specific categories"),
	GLFW_FORMAT_UNAVAIABLE("""
			If emitted during window creation, the requested pixel format is not supported
			If emitted when querying the clipboard, the contents of the clipboard could not be converted to the requested format
			"""),
	GLFW_NO_WINDOW_CONTEXT("A window that does not have an OpenGL or OpenGL ES context was passed to a function that requires it to have one"),
	GLFW_CURSOR_UNAVAILABLE("The specified standard cursor shape is not available, either because the current platform cursor theme does not provide it or because it is not available on the platform"),
	GLFW_FEATURE_UNAVAILABLE("The requested feature is not provided by the platform, so GLFW is unable to implement it. The documentation for each function notes if it could emit this error"),
	GLFW_FEATURE_UNIMPLEMENTED("The requested feature has not yet been implemented in GLFW for this platform"),
	GLFW_PLATFORM_UNAVAIABLE("""
			If emitted during initialization, no matching platform was found. If the GLFW_PLATFORM init hint was set to GLFW_ANY_PLATFORM, GLFW could not detect any of the platforms supported by this library binary, except for the Null platform. If the init hint was set to a specific platform, it is either not supported by this library binary or GLFW was not able to detect it.
			If emitted by a native access function, GLFW was initialized for a different platform than the function is for.
			""");
	
	private String description;
	
	GLFWError(String description) {
		this.description = description;
	}
	
	public static GLFWError error() {
		return error(GLFW.glfwGetError(null));
	}
	public static GLFWError error(int i) {
		return GLFWError.values()[i-0x00010000];
	}
	public void throww() {
		throw new RuntimeException(this.name() + "\n\n" + this.description);
	}
	public String description() {
		return description;
	}
}
