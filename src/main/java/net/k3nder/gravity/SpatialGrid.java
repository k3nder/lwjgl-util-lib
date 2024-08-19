package net.k3nder.gravity;

import java.util.ArrayList;
import java.util.List;

public class SpatialGrid {
    private final int gridSize;
    private final List<AABB>[][][] grid;
    private final int gridCount;
    private final int halfGridCount;

    public SpatialGrid(int gridSize, int worldSize) {
        this.gridSize = gridSize;
        this.gridCount = worldSize / gridSize;
        this.halfGridCount = gridCount / 2;  // Usado para manejar coordenadas negativas

        grid = new ArrayList[gridCount][gridCount][gridCount];
        for (int x = 0; x < gridCount; x++) {
            for (int y = 0; y < gridCount; y++) {
                for (int z = 0; z < gridCount; z++) {
                    grid[x][y][z] = new ArrayList<>();
                }
            }
        }
    }

    private int getGridIndex(float coord) {
        // Ajustar el índice para manejar coordenadas negativas
        int index = (int) Math.floor(coord / gridSize) + halfGridCount;

        if (index < 0) {
            index = 0;
        } else if (index >= gridCount) {
            index = gridCount - 1;
        }
        return index;
    }

    public void add(AABB box) {
        int minX = getGridIndex(box.getMin().x);
        int minY = getGridIndex(box.getMin().y);
        int minZ = getGridIndex(box.getMin().z);
        int maxX = getGridIndex(box.getMax().x);
        int maxY = getGridIndex(box.getMax().y);
        int maxZ = getGridIndex(box.getMax().z);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    grid[x][y][z].add(box);
                }
            }
        }
    }

    public List<AABB> getNearbyBoxes(AABB box) {
        int minX = getGridIndex(box.getMin().x);
        int minY = getGridIndex(box.getMin().y);
        int minZ = getGridIndex(box.getMin().z);
        int maxX = getGridIndex(box.getMax().x);
        int maxY = getGridIndex(box.getMax().y);
        int maxZ = getGridIndex(box.getMax().z);

        List<AABB> nearbyBoxes = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    nearbyBoxes.addAll(grid[x][y][z]);
                }
            }
        }
        return nearbyBoxes;
    }
}