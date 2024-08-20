package net.k3nder.gl;

import net.k3nder.gl.graphic.GraphicalObject;
import net.k3nder.gl.graphic.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

public class Camera implements Applicable<Shader> {
    private Vector3f cameraPos   = new Vector3f(0.0f, 0.0f,  -3.0f);
    private Vector3f cameraFront = new Vector3f(1.0f, 0.0f, 0.0f);
    private Vector3f cameraUp    = new Vector3f(0.0f, 1.0f,  0.0f);
    private Vector3f cameraRight = new Vector3f(0.0f, 0.0f, 0.0f);
    private float lastX, lastY;

    private float yaw = -90.0f;
    private float pitch = 0.0f;
    private float roll = 0.0f;

    public float MovementSpeed = 10;
    public float MouseSensitivity = 1.0f;
    public float Zoom;

    private int width, height;

    private float fov = 45.0f;

    private Camera() {}

    public static Camera create(int width, int height) {
        Camera camera = new Camera();
        camera.width = width;
        camera.height = height;
        camera.lastX = width / 2.0f;
        camera.lastY = height / 2.0f;
        return camera;
    }
    public void setPos(float x, float y, float z) {
        cameraPos.set(x, y, z);
    }
    public void addPos(float x, float y, float z) {
        cameraPos.add(x, y, z);
    }
    public void move(Direction direction, float deltaTime) {
        cameraPos = calcNewPosToMove(deltaTime, direction);
    }
    public void setFov(float fov) {this.fov = fov;}
    public void updateRotation() {
        // Calcular la nueva dirección del frente de la cámara
        Vector3f direction = new Vector3f();
        direction.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        direction.y = (float) Math.sin(Math.toRadians(pitch));
        direction.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        cameraFront = new Vector3f(direction).normalize();
    }
    public void rotate(double xpos, double ypos) {
        float xoffset = (float) (xpos - lastX);
        float yoffset = (float) (lastY - ypos);
        lastX = (float) xpos;
        lastY = (float) ypos;

        float sensitivity = 0.1f;
        xoffset *= sensitivity;
        yoffset *= sensitivity;

        yaw   += xoffset;
        pitch += yoffset;

        //if(pitch > 89.0f)
        //    pitch = 89.0f;
        //if(pitch < -89.0f)
        //    pitch = -89.0f;
    }
    public void rotateX(float angle) {
        pitch += angle;
    }

    public void rotateY(float angle) {
        roll += angle;
    }

    public void rotateZ(float angle) {
        yaw += angle;
    }
    public Matrix4f getView() {
        return new Matrix4f().lookAt(cameraPos, cameraPos.add(cameraFront, new Vector3f()), cameraUp);
    }
    public Matrix4f getProjection() {
        Matrix4f projection = new Matrix4f().identity();
        projection.perspective(
                (float) Math.toRadians(fov),
                (float) width / height,
                0.1f,
                100.0f
        );
        return projection;
    }
    public void apply(Shader shader) {
        shader.setMatrix(getView(), "view");
        shader.setMatrix(getProjection(), "projection");
        shader.setVec3("viewPos", cameraPos);
    }
    public void setLastX(float x) {
        this.lastX = x;
    }
    public void setLastY(float y) {
        this.lastY = y;
    }
    public Vector3f getPos() {
        return new Vector3f(cameraPos);
    }
    public enum Direction {
        FRONT,
        BACK,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    public Vector3f calcNewPosToMove(float deltaTime, Direction direction) {
        float velocity = MovementSpeed * deltaTime;
        Vector3f cameraPos = null;
        try {
            cameraPos = (Vector3f) getPos().clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        if (direction == Direction.FRONT) {
            cameraPos.add(cameraFront.mul(velocity, new Vector3f()));
        } else if (direction == Direction.BACK) {
            cameraPos.sub(cameraFront.mul(velocity, new Vector3f()));
        } else if (direction == Direction.LEFT) {
            Vector3f right = new Vector3f(cameraFront).cross(cameraUp).normalize();
            cameraPos.sub(right.mul(velocity));
        } else if (direction == Direction.RIGHT) {
            Vector3f right = new Vector3f(cameraFront).cross(cameraUp).normalize();
            cameraPos.add(right.mul(velocity));
        } else if (direction == Direction.UP) {
            cameraPos.add(new Vector3f(cameraUp).mul(velocity));
        } else if (direction == Direction.DOWN) {
            cameraPos.sub(new Vector3f(cameraUp).mul(velocity));
        }
        return cameraPos;
    }
    public Vector3f getCameraFront() {
        return new Vector3f(cameraFront);
    }
    public boolean check(GraphicalObject modelMatrix) {

        var objectPos = new Vector3f();
        modelMatrix.getModel().getTranslation(objectPos);

        var cameraDir = getCameraFront();

        // Calcular el vector desde la cámara hasta el objeto
        Vector3f objectDir = new Vector3f();
        objectPos.sub(cameraPos, objectDir);

        // Normalizar el vector objectDir
        objectDir.normalize();

        // Normalizar el vector cameraDir (por si acaso no está normalizado)
        cameraDir.normalize();

        // Calcular el coseno del ángulo entre cameraDir y objectDir
        float cosTheta = cameraDir.dot(objectDir);

        // Calcular el coseno del ángulo máximo de visión (FOV / 2)
        float cosThetaMax = (float) Math.cos(Math.toRadians(fov / 2));

        // Verificar si el objeto está dentro del campo de visión
        return cosTheta >= cosThetaMax;

    }
    public <T extends GraphicalObject> int checks(List<T> objects, float maxDistance) {
        int closestIndex = -1;
        float closestTmin = Float.MAX_VALUE;

        for (int i = 0; i < objects.size(); i++) {
            GraphicalObject object = objects.get(i);
            if (check(object)) {
                // Calcular la distancia mínima 'tmin' para el objeto actual
                Vector3f cameraPos = new Vector3f(this.cameraPos);
                Vector3f rayDirection = new Vector3f(this.cameraFront).normalize();

                float tmin = getTmin(object, cameraPos, rayDirection);

                // Si la intersección es válida y más cercana que la anterior, actualiza el índice
                if (tmin < closestTmin && tmin >= 0) {
                    closestTmin = tmin;
                    closestIndex = i;
                }
            }
        }

        return closestIndex;
    }

    private static float getTmin(GraphicalObject object, Vector3f cameraPos, Vector3f rayDirection) {
        Vector3f max = object.getMax();
        Vector3f min = object.getMin();

        float tminX = (min.x - cameraPos.x) / rayDirection.x;
        float tmaxX = (max.x - cameraPos.x) / rayDirection.x;
        if (tminX > tmaxX) {
            float temp = tminX;
            tminX = tmaxX;
            tmaxX = temp;
        }

        float tminY = (min.y - cameraPos.y) / rayDirection.y;
        float tmaxY = (max.y - cameraPos.y) / rayDirection.y;
        if (tminY > tmaxY) {
            float temp = tminY;
            tminY = tmaxY;
            tmaxY = temp;
        }

        float tminZ = (min.z - cameraPos.z) / rayDirection.z;
        float tmaxZ = (max.z - cameraPos.z) / rayDirection.z;
        if (tminZ > tmaxZ) {
            float temp = tminZ;
            tminZ = tmaxZ;
            tmaxZ = temp;
        }

        float tmin = Math.max(tminX, Math.max(tminY, tminZ));
        return tmin;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}

