package net.k3nder.lz4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import org.lwjgl.util.lz4.LZ4;

import net.k3nder.lz4.exceptions.LZ4CompressException;
import net.k3nder.lz4.exceptions.LZ4DecompressException;

public class LZ4Util {
	private static final LZ4Util INSTANCE = new LZ4Util();
	private LZ4Util() {}
	public static LZ4Util getInstance() {
		return INSTANCE;
	}
	
	public ByteBuffer compress(ByteBuffer data) {
		int originalSize = data.remaining();

        // Obtener el tamaño máximo de los datos comprimidos
        int maxCompressedLength = LZ4.LZ4_compressBound(originalSize);

        // Crear el buffer de salida para los datos comprimidos (debe ser suficiente para datos comprimidos + tamaño original)
        ByteBuffer compressedBuffer = ByteBuffer.allocateDirect(maxCompressedLength + 4);
        compressedBuffer.order(ByteOrder.LITTLE_ENDIAN);

        // Escribir el tamaño original al principio del buffer comprimido
        compressedBuffer.putInt(originalSize);

        // Configurar el buffer de datos comprimidos a partir del offset
        ByteBuffer compressedDataBuffer = compressedBuffer.duplicate();
        compressedDataBuffer.position(4);
        compressedDataBuffer.limit(maxCompressedLength + 4);

        // Comprimir los datos
        int compressedSize = LZ4.LZ4_compress_default(data, compressedDataBuffer);

        // Verificar si la compresión fue exitosa
        if (compressedSize <= 0) 
        	throw new LZ4CompressException(compressedSize);

        // Ajustar el límite del buffer comprimido
        compressedBuffer.position(0);
        compressedBuffer.limit(compressedSize + 4);	
        return compressedBuffer;
	}
	
	public ByteBuffer decompress(ByteBuffer compressedBuffer) {
		int decompressedSize = compressedBuffer.getInt();

        // Crear el buffer de salida para los datos descomprimidos (usando el tamaño original leído)
        ByteBuffer decompressedBuffer = ByteBuffer.allocateDirect(decompressedSize);

        // Descomprimir los datos
        int resultSize = LZ4.LZ4_decompress_safe(compressedBuffer, decompressedBuffer);

        // Verificar si la descompresión fue exitosa
        if (resultSize < 0) 
            throw new LZ4DecompressException(resultSize);

        // Convertir el buffer de salida de vuelta a una cadena
        byte[] outputData = new byte[resultSize];
        decompressedBuffer.get(outputData);

        String result = new String(outputData, StandardCharsets.UTF_8);
        return decompressedBuffer;
	}
	
	public ByteBuffer compress(String cc) {
		return compress(ByteBuffer.wrap(cc.getBytes()));
	}
	public ByteBuffer compress(Boolean cc) {
		return compress((cc ? 1 : 0));
	}
	public ByteBuffer compress(Integer cc) {
		return compress(String.valueOf(cc));
	}
	public String decompressString(ByteBuffer bb) {
		return new String(decompress(bb).array());
	}
}
