package net.k3nder.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UBuffer {
    private UBuffer() { throw new IllegalStateException("Utility class"); }
    public static String[] splitBuffer(StringBuffer line) {
        return line.toString().split("\\s+");
    }
    public static boolean streamStartsWith(StringBuffer stream, String prefix) {
        String s = stream.toString();
        return s.startsWith(prefix);
    }
    public static List<StringBuffer> splitInputStreamByLine(InputStream inputStream) throws IOException {
        List<StringBuffer> buffers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            StringBuffer buffer = new StringBuffer(line);
            buffers.add(buffer);
        }

        return buffers;
    }
}
