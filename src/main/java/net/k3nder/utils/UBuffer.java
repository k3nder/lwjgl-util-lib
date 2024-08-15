package net.k3nder.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
    public static ByteBuffer streamToByteBuffer(InputStream stream, int bufferSize) throws IOException {
        ByteBuffer buffer;

        try (InputStream source = stream;
             ReadableByteChannel rbc = Channels.newChannel(source)) {

            buffer = ByteBuffer.allocateDirect(bufferSize);

            while (true) {
                int bytes = rbc.read(buffer);
                if (bytes == -1) break;
                if (buffer.remaining() == 0) {
                    // Si el buffer está lleno, duplicar su tamaño
                    ByteBuffer newBuffer = ByteBuffer.allocateDirect(buffer.capacity() * 2);
                    buffer.flip();
                    newBuffer.put(buffer);
                    buffer = newBuffer;
                }
            }

            buffer.flip();
        }

        return buffer;
    }
    public static ByteBuffer streamToByteBuffer(InputStream stream) throws IOException {
        return streamToByteBuffer(stream, 1024);
    }
}
