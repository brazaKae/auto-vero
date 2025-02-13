package com.kae.auto_vero;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Classe para lidar com a criação da pasta de download dos relatórios
public class FileManager {
    public static void createDownloadsFolder(String downloadsPath) throws IOException {
        Path dir = Paths.get(downloadsPath);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }
}
