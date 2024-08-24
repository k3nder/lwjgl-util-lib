package net.k3nder.lz4.exceptions;

import org.lwjgl.util.lz4.LZ4Frame;

public class LZ4Exception extends RuntimeException {
	public LZ4Exception(int cc) {
		super("Error decoding, error " + LZ4Frame.LZ4F_getErrorCode(cc) + " " + LZ4Frame.LZ4F_getErrorName(cc));
	}
}
