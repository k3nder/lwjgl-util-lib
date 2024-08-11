package net.k3nder.gl.visual;

import net.k3nder.gl.Camera;
import net.k3nder.gl.GraphicalObject;

import java.util.List;

public interface OcclusionSystem {
    <T extends GraphicalObject> boolean isHidden(T object, Camera camera);
    <T extends GraphicalObject> List<T> removeHidden(List<T> objects, Camera camera);
}
