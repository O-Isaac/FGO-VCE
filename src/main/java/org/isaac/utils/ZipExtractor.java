package org.isaac.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipExtractor {
    private final String filePath;
    private static final MyLogger LOG = new MyLogger(ZipExtractor.class);

    public ZipExtractor(String filePath) {
        this.filePath = filePath;
    }
    
    public Optional<Path> extract(List<String> files, String dest) throws IOException {
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(this.filePath))) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String entryName = entry.getName();
                
                if (files.contains(entryName)) {
                    LOG.info("Extracting " + entryName + " (" + entry.getCrc() + ")");
                    
                    Path pathEntryFile = Paths.get(entryName).getFileName();
                    Path destinationFolder = Paths.get(dest).resolve(pathEntryFile);

                    Files.copy(zip, destinationFolder.getFileName(), StandardCopyOption.REPLACE_EXISTING);
                    return Optional.of(destinationFolder);
                }
            }
        }

        return Optional.empty();
    }
}
