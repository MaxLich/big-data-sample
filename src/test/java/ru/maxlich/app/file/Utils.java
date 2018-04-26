package ru.maxlich.app.file;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mshulakov on 26.04.2018.
 */
public class Utils {
    static Path getPathToResource(String pathToResource) throws URISyntaxException {
        URI fileUri = Utils
                .class
                .getClassLoader()
                .getResource(pathToResource)
                .toURI();
        return Paths.get(fileUri);
    }
}
