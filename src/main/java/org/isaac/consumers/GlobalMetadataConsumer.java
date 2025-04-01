/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.isaac.consumers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.isaac.utils.MyLogger;
import org.isaac.utils.ZipExtractor;

/**
 * Consumer used on Optional for extract metadata
 * @author Isaac
 */
public class GlobalMetadataConsumer implements Consumer<Path> {
    private final List<String> globalMetadata = Arrays.asList("assets/bin/Data/Managed/Metadata/global-metadata.dat");
    private final MyLogger LOGGER = new MyLogger(GlobalMetadataConsumer.class);

    private final String appVersion;
    private final String outputFolder;

    public GlobalMetadataConsumer(String appVersion, String outputFolder) {
        this.appVersion = appVersion;
        this.outputFolder = outputFolder;
    }

    @Override
    public void accept(Path apkPath) {
        ZipExtractor globalMetadataExtrator = new ZipExtractor(apkPath.toString());

        try {
            Optional<Path> globalMetadaPath = globalMetadataExtrator.extract(globalMetadata, "");
            globalMetadaPath.ifPresentOrElse(new LiteralExtractorConsumer(appVersion, outputFolder), () -> {
                LOGGER.info("Global metadata not found!");
            });
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
