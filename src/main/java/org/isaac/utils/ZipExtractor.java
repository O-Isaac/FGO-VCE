package org.isaac.utils;

import org.isaac.commands.CheckVerCode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipExtractor {
    private String filePath;
    private static final MyLogger LOG = new MyLogger(ZipExtractor.class);

    public ZipExtractor(String filePath) {
        this.filePath = filePath;
    }

    public Path extract(String fileName, String pathDest) throws IOException {
        Path extractPath = null;

        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(this.filePath))) {
            ZipEntry entry = zip.getNextEntry();

            while (entry != null) {
                if (entry.getName().equals(fileName)) {
                    LOG.info("Extracting " + entry.getName() + " (" + entry.getCrc() + ")");
                    String[] fileNameParts = fileName.splitWithDelimiters("/", 0);
                    Path filePathDest = Paths.get(pathDest + fileNameParts[fileNameParts.length - 1]);
                    extractPath = filePathDest;
                    Files.copy(zip,filePathDest, StandardCopyOption.REPLACE_EXISTING);
                }

                entry = zip.getNextEntry();
            }
        }

        return extractPath;
    }
}
