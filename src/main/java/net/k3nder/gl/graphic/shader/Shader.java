package net.k3nder.gl.graphic.shader;

import net.k3nder.gl.exceptions.ShaderCompilationException;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Shader {
    private int fragmentShaderId;
    private int vertexShaderId;
    private int programId;

    public static final String SIMPLE_VERTEX_SHADER =
            """
                    #version 330 core
                    layout (location = 0) in vec3 aPos;
                    layout (location = 1) in vec2 aTexCoord;
                    uniform mat4 model;
                    uniform mat4 view;
                    uniform mat4 projection;
                    out vec2 TexCoord;
                    void main()
                    {
                       gl_Position = projection * view * model * vec4(aPos, 1.0);
                       TexCoord = aTexCoord;
                    }
            """;
    public static final String SIMPLE_FRAGMENT_SHADER =
            """
                    #version 330 core
                    out vec4 FragColor;
                    in vec2 TexCord;
                    uniform sampler2D tex;
                    void main()
                    {
                       FragColor = texture(tex, TexCord);
                    }
            """;

    private Shader() {}

    public static Shader create() {
        return create(SIMPLE_VERTEX_SHADER, SIMPLE_FRAGMENT_SHADER);
    }

    public static Shader create(File vertexFile, File fragmentFile) throws IOException {
        FileInputStream vertexFis = new FileInputStream(vertexFile);
        FileInputStream fragmentFis = new FileInputStream(fragmentFile);
        return create(vertexFis, fragmentFis);
    }

    public static Shader create(InputStream vertexShader, InputStream fragmentShader) throws IOException {
        String vertexS = new String(vertexShader.readAllBytes());
        String fragmentS = new String(fragmentShader.readAllBytes());
        return create(vertexS, fragmentS);
    }

    public static Shader create(String vertex, String fragment) {
        int vertexId = compileShader(GL_VERTEX_SHADER, vertex);
        int fragmentId = compileShader(GL_FRAGMENT_SHADER, fragment);
        return create(vertexId, fragmentId);
    }

    public static Shader create(int vertexShader, int fragmentShader) {
        Shader sh = new Shader();

        int shader = glCreateProgram();
        glAttachShader(shader, vertexShader);
        glAttachShader(shader, fragmentShader);
        glLinkProgram(shader);
        checkCompileErrors(shader, "PROGRAM");

        sh.setFragmentShaderId(fragmentShader);
        sh.setVertexShaderId(vertexShader);
        sh.setProgramId(shader);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        return sh;
    }


    public static int compileShader(int type, String source) {
        int id = glCreateShader(type);
        glShaderSource(id, source);
        glCompileShader(id);
        checkCompileErrors(id, (type == GL_VERTEX_SHADER ? "VERTEX" : "FRAGMENT"));

        return id;
    }

    public static void checkCompileErrors ( int shader, String type){
        int success;
        if (type.equals("PROGRAM")) {
            success = glGetProgrami(shader, GL_LINK_STATUS);
            if (success == GL_FALSE) {
                String infoLog = glGetProgramInfoLog(shader);
                throw new ShaderCompilationException("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + infoLog);
            }
        } else {
            success = glGetShaderi(shader, GL_COMPILE_STATUS);
            if (success == GL_FALSE) {
                String infoLog = glGetShaderInfoLog(shader);
                throw new ShaderCompilationException("ERROR::SHADER::" + type + "::COMPILATION_FAILED\n" + infoLog);
            }
        }
    }

    public void use() {
        glUseProgram(programId);
    }

    public int getVertexShaderId() {
        return vertexShaderId;
    }

    public void setVertexShaderId(int vertexShaderId) {
        this.vertexShaderId = vertexShaderId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getFragmentShaderId() {
        return fragmentShaderId;
    }

    public void setFragmentShaderId(int fragmentShaderId) {
        this.fragmentShaderId = fragmentShaderId;
    }
    public void setFloat(String name, float value) {
        glUniform1f(glGetUniformLocation(programId, name), value);
    }
    public void setI(String name, float value) {
        glUniform1f(glGetUniformLocation(programId, name), value);
    }
    public void setV4f(String name, float x, float y, float z, float w) {
        glUniform4f(glGetUniformLocation(programId, name), x, y, z, w);
    }
    public void setVec3(String name, Vector3f value) {
        glUniform3f(glGetUniformLocation(programId, name), value.x, value.y, value.z);
    }
    public void setV2f(String name, Vector2f value) {
        glUniform2f(glGetUniformLocation(programId, name), value.x, value.y);
    }
    public void setMatrix(Matrix4f matrix, String key) {
        try (var stack = stackPush()) {
            var transformBuffer = stack.mallocFloat(16);
            matrix.get(transformBuffer);
            int transformLoc = glGetUniformLocation(programId, key);
            glUniformMatrix4fv(transformLoc, false, transformBuffer);
        }
    }
}
