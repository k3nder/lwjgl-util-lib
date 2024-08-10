package net.k3nder.gl.model;

import kotlin.UByte;
import net.k3nder.gl.Reloadable;
import net.k3nder.utils.UArray;
import net.k3nder.utils.UBuffer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

public class Polygon implements Reloadable {
    //private Vertex[] vertices;
    private Integer VAO;
    private Integer VBO;
    private Integer EBO;

    private List<Vertex> vertices;
    private List<Integer> faces;

    //private List<Integer> indices;

    public static class Builder {
        private List<Vertex> vertices = new ArrayList<>();
        private List<Integer> faces = new ArrayList<>();
        //private List<Integer> indices = new ArrayList<>();

        public Builder() {}

        public Polygon build() {
            Polygon p = new Polygon();
            p.vertices = vertices;
            p.faces = faces;
            //p.normals = normals;
            //p.indices = indices;
            return p;
        }

        public Builder vertex(Vertex v) {
            vertices.add(v);
            return this;
        }
        public Builder face(Integer f) {
            faces.add(f);
            return this;
        }
    }
    public void use() {
        glBindVertexArray(VAO);
        glEnableVertexAttribArray(0);
        //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
    }
    public void unuse() {
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
    public void draw(Integer mode) {
        use();
        glDrawArrays(mode, 0,  vertex());
        unuse();
    }
    public int vertex() {
        return vertices.size();
    }
    public void create(Integer mode) {

        var vertices = UArray.toPrimitive(verticesToArray());

        //for (float vertex : vertices) {
        //    System.out.println(vertex);
        //}

        EBO = glGenBuffers();

        var indices = UArray.toPrimitive(faces.toArray(new Integer[faces.size()]));

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, mode);

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, mode);

        glVertexAttribPointer(0, 3, GL_FLOAT, false,8 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false,8 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false,8 * Float.BYTES, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);



        // NVBO = glGenBuffers();
        // glBindBuffer(GL_ARRAY_BUFFER, NVBO);
        // glBufferData(GL_ARRAY_BUFFER, normalBuffer, mode);
        // glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
        // glEnableVertexAttribArray(2);
//
        // EBO = glGenBuffers();
        // glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        // glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, mode);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    //public Integer getFirst() {
    //    return indices.length;
    //}
    public static Builder builder() {
        return new Builder();
    }
    public void clean() {
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
    }
    public void load() {
        create(GL_STATIC_DRAW);
    }

    public Float[] verticesToArray() {
        List<Float> result = new ArrayList<>();
        for (Vertex v : vertices) {
            Vector3f ver = v.getVertex();
            Vector3f normal = v.getNormal();
            Vector2f tex = v.getTexture();

            result.add(ver.x);
            result.add(ver.y);
            result.add(ver.z);

            result.add(normal.x);
            result.add(normal.y);
            result.add(normal.z);

            result.add(tex.x);
            result.add(tex.y);
        }
        return result.toArray(new Float[result.size()]);
    }

    public static Polygon loadFromFile(InputStream stream) throws IOException {
            Builder b = builder();

            Path tempFile = Files.createTempFile("tempfile_", ".tmp");

            // Escribir el InputStream en el archivo temporal
            try (OutputStream outputStream = Files.newOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = stream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            AIScene scene = Assimp.aiImportFile(tempFile.toString(),
                    Assimp.aiProcess_Triangulate |
                            Assimp.aiProcess_FlipUVs |
                            Assimp.aiProcess_CalcTangentSpace);

            AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));

            for (int i = 0; i < mesh.mNumVertices(); i++) {
                AIVector3D vec = mesh.mVertices().get(i);
                Vector3f ver = new Vector3f(vec.x(), vec.y(), vec.z());

                AIVector3D normal = mesh.mNormals().get(i);
                Vector3f norm = new Vector3f(normal.x(), normal.y(), normal.z());

                Vector2f texture;

                if (mesh.mTextureCoords(0) != null) {
                    AIVector3D texCoord = mesh.mTextureCoords(0).get(i);
                    texture = new Vector2f(texCoord.x(), texCoord.y());
                } else {
                    texture = new Vector2f(1.0f, 1.0f);
                }

                b.vertex(Vertex.create(ver, norm, texture));
            }

            for (int i = 0; i < mesh.mNumFaces(); i++) {
                AIFace face = mesh.mFaces().get(i);
                IntBuffer buffer = face.mIndices();
                while (buffer.remaining() > 0) {
                    b.face(buffer.get());
                }
            }

//
            //b.VC = b.vertices.size() / 3;

            return b.build();
        }

}
