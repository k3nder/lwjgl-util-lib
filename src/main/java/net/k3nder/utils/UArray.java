package net.k3nder.utils;

import java.util.List;

public class UArray {
    private UArray() { throw new IllegalStateException("Utility class"); }
    public static float[] toPrimitive(Float[] array) {
        float[] result = new float[array.length];
        int i = 0;
        for (Float v : array) {
            result[i] = v;
            i++;
        }
        return result;
    }
    public static int[] toPrimitive(Integer[] array) {
        int[] result = new int[array.length];
        int i = 0;
        for (Integer v : array) {
            result[i] = v;
            i++;
        }
        return result;
    }
    //public String[] startsWith()
}
