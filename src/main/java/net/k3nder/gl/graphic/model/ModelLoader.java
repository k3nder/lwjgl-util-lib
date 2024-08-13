package net.k3nder.gl.graphic.model;

import net.k3nder.gl.Loader;
import net.k3nder.gl.exceptions.LoadModelException;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

public class ModelLoader implements Loader<Polygon> {
    public Polygon load(InputStream s) {
        try {
            Path tmpfile = Files.createTempFile("model-", "-loader");

            FileWriter writer = new FileWriter(tmpfile.toFile());
            writer.write(new String(s.readAllBytes()));
            writer.close();

            tmpfile.toFile().deleteOnExit();
            return load(tmpfile.toFile());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Polygon load(File file) {
        Polygon.Builder builder = new Polygon.Builder();

        AIScene scene = aiImportFile(file.getAbsolutePath(), aiProcess_JoinIdenticalVertices | aiProcess_Triangulate);
        if (scene == null) {
            throw new LoadModelException();
        }

        // Procesar el primer mesh (malla) en la escena
        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));

        // Obtener los datos
        AIVector3D.Buffer vertexBuffer = mesh.mVertices();
        AIVector3D.Buffer normalBuffer = mesh.mNormals();
        AIVector3D.Buffer texCoordBuffer = mesh.mTextureCoords(0); // Asumiendo que hay coordenadas de textura



        if (texCoordBuffer != null) {
            for (int i = 0; i < mesh.mNumVertices(); i++) {
                // Agregar vértices
                builder.put(vertexBuffer.get(i).x()); // x
                builder.put(vertexBuffer.get(i).y()); // y
                builder.put(vertexBuffer.get(i).z()); // z

                // Agregar normales
                builder.put(normalBuffer.get(i).x()); // nx
                builder.put(normalBuffer.get(i).y()); // ny
                builder.put(normalBuffer.get(i).z()); // nz

                // Agregar coordenadas de textura
                builder.put(texCoordBuffer.get(i).x()); // u
                builder.put(texCoordBuffer.get(i).y()); // v
            }
        } else {
            // Si no hay coordenadas de textura, solo usa vértices y normales
            for (int i = 0; i < mesh.mNumVertices(); i++) {
                // Agregar vértices
                builder.put(vertexBuffer.get(i).x()); // x
                builder.put(vertexBuffer.get(i).y()); // y
                builder.put(vertexBuffer.get(i).z()); // z

                // Agregar normales
                builder.put(normalBuffer.get(i).x()); // nx
                builder.put(normalBuffer.get(i).y()); // ny
                builder.put(normalBuffer.get(i).z()); // nz

                // Agregar coordenadas de textura (placeholder si no están disponibles)
                builder.put(0.0f); // u
                builder.put(0.0f); // v
            }
        }

        // Obtener los índices
        for (int i = 0; i < mesh.mNumFaces(); i++) {
            IntBuffer indexBuffer = mesh.mFaces().get(i).mIndices();
            while (indexBuffer.remaining() > 0) {
                builder.face(indexBuffer.get());
            }
        }

        builder.attribPointer(0, 3);
        builder.attribPointer(1, 3);
        builder.attribPointer(2, 2);


        return builder.build();
    }
}
