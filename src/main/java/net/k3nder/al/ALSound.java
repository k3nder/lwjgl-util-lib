package net.k3nder.al;

import net.k3nder.al.buffers.ALBuffer;
import net.k3nder.utils.UBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_memory;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class ALSound {
    private final ALBuffer buffer;
    private ALSound(ShortBuffer buffer, int format, int sampleRate) {
        this.buffer = new ALBuffer();
        this.buffer.uploadData(format, buffer, sampleRate);
        free(buffer);
    }
    public static ALSound create(InputStream stream) throws IOException {
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);

        ShortBuffer rawAudioBuffer = stb_vorbis_decode_memory(UBuffer.streamToByteBuffer(stream), channelsBuffer, sampleRateBuffer);

        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();

        stackPop();
        stackPop();

        int format = -1;
        if(channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if(channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        return new ALSound(rawAudioBuffer, format, sampleRate);
    }
    public static ALSound create(File file) throws IOException {
        return create(file);
    }
    public static ALSound create(String file) throws IOException {
        return create(new FileInputStream(file));
    }
    public ALBuffer buffer() {
        return buffer;
    }
}
