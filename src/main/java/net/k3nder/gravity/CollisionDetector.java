package net.k3nder.gravity;

import org.joml.Vector3f;

import java.util.List;

public class CollisionDetector {
    private SpatialGrid grid;

    public CollisionDetector(SpatialGrid grid) {
        this.grid = grid;
    }

    public void add(AABB box) {
        grid.add(box);
    }

    public boolean detectCollisions(AABB box) {
        List<AABB> candidates = grid.getNearbyBoxes(box);
        for (AABB candidate : candidates) {
            if (box != candidate && box.intersects(candidate)) {
                return true;
            }
        }
        return false;
    }
    public Vector3f resolveCollision(AABB box, Vector3f velocity) {
        Vector3f resolvedPosition = new Vector3f(box.getMin());
        List<AABB> candidates = grid.getNearbyBoxes(box);

        for (AABB candidate : candidates) {
            if (box != candidate && box.intersects(candidate)) {
                Vector3f correction = new Vector3f();

                if (velocity.x > 0) {
                    correction.x = candidate.getMin().x - box.getMax().x;
                } else if (velocity.x < 0) {
                    correction.x = candidate.getMax().x - box.getMin().x;
                }

                if (velocity.y > 0) {
                    correction.y = candidate.getMin().y - box.getMax().y;
                } else if (velocity.y < 0) {
                    correction.y = candidate.getMax().y - box.getMin().y;
                }

                if (velocity.z > 0) {
                    correction.z = candidate.getMin().z - box.getMax().z;
                } else if (velocity.z < 0) {
                    correction.z = candidate.getMax().z - box.getMin().z;
                }

                // Aplicar la corrección a la posición resuelta
                resolvedPosition.add(correction);

                // Anular el componente de la velocidad en la dirección de la colisión
                if (Math.abs(correction.x) > 0) {
                    velocity.x = 0;
                }
                if (Math.abs(correction.y) > 0) {
                    velocity.y = 0;
                }
                if (Math.abs(correction.z) > 0) {
                    velocity.z = 0;
                }
            }
        }

        // Devolver la posición resuelta
        return resolvedPosition;
    }
}

