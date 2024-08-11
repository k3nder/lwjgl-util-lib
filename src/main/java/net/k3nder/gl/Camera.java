package net.k3nder.gl;

import net.k3nder.gl.shader.Shader;
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

    private boolean frontStoped = false;
    private boolean backStoped = false;
    private boolean leftStoped = false;
    private boolean rightStoped = false;

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
        return cameraPos;
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
        return cameraFront;
    }
    public boolean check(GraphicalObject modelMatrix, float maxDistance) {
        var cameraPos = new Vector3f(this.cameraPos);
        var rayDirection = new Vector3f(this.cameraFront);
        // Invertir la matriz del modelo para transformar el rayo al espacio local del cubo
        Matrix4f inverseModel = new Matrix4f(modelMatrix.getModel()).invert();

        // Transformar la posición de la cámara y la dirección del rayo al espacio local del cubo
        Vector3f localRayOrigin = cameraPos.mulPosition(inverseModel, new Vector3f());
        Vector3f localRayDirection = rayDirection.mulDirection(inverseModel, new Vector3f()).normalize();

        // Definir el AABB del cubo en su espacio local (por ejemplo, desde -1 a 1 en todas las dimensiones)
        Vector3f boxMin = modelMatrix.getMin();
        Vector3f boxMax = modelMatrix.getMax();

        // Comprobar si el rayo intersecta el cubo
        Vector3f invDir = new Vector3f(1.0f / localRayDirection.x, 1.0f / localRayDirection.y, 1.0f / localRayDirection.z);
        Vector3f tMin = new Vector3f(boxMin).sub(localRayOrigin).mul(invDir);
        Vector3f tMax = new Vector3f(boxMax).sub(localRayOrigin).mul(invDir);
        Vector3f t1 = new Vector3f(Math.min(tMin.x, tMax.x), Math.min(tMin.y, tMax.y), Math.min(tMin.z, tMax.z));
        Vector3f t2 = new Vector3f(Math.max(tMin.x, tMax.x), Math.max(tMin.y, tMax.y), Math.max(tMin.z, tMax.z));

        float tNear = Math.max(Math.max(t1.x, t1.y), t1.z);
        float tFar = Math.min(Math.min(t2.x, t2.y), t2.z);

        // Verificar si hay una intersección y si está dentro de la distancia especificada
        if (tNear <= tFar && tFar > 0) {
            return tNear <= maxDistance;
        }

        return false;
    }
    public int checks(List<GraphicalObject> objects, float maxDistance) {
        int i = 0;
        for (GraphicalObject object : objects) {
            if (check(object, maxDistance)) return i;
            i++;
        }
        return -1;
    }
}

