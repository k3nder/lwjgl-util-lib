package net.k3nder.gl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public interface Loader<T> {
    T load(InputStream file);
    default T load(File file) throws FileNotFoundException {
        InputStream fileStream = new FileInputStream(file);
        return load(fileStream);
    }
}
