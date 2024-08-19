package net.k3nder.gravity;

import net.k3nder.gl.graphic.GraphicalObject;
import org.joml.Vector3f;

public class AABB {
    private Vector3f min;
    private Vector3f max;

    public AABB(Vector3f min, Vector3f max) {
        this.min = min;
        this.max = max;
    }

    public boolean intersects(AABB other) {
        // Verificar colisiones en cada eje (X, Y, Z)
        return (this.max.x >= other.min.x && this.min.x <= other.max.x) &&
                (this.max.y >= other.min.y && this.min.y <= other.max.y) &&
                (this.max.z >= other.min.z && this.min.z <= other.max.z);
    }

    public Vector3f getMin() {
        return min;
    }

    public void setMin(Vector3f min) {
        this.min = min;
    }

    public Vector3f getMax() {
        return max;
    }

    public void setMax(Vector3f max) {
        this.max = max;
    }
}
