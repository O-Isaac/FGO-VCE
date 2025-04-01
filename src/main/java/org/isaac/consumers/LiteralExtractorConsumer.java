/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.isaac.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;
import org.isaac.entities.AppEntity;
import org.isaac.utils.MyLogger;
import org.isaac.utils.StringLiteralExtractor;

/**
 * Consumer used on GlobalMetadata extract, this will called if we have global-metadata.dat
 * @author Isaac
 */
public class LiteralExtractorConsumer implements Consumer<Path> {
    private final MyLogger LOGGER = new MyLogger(LiteralExtractorConsumer.class);
    private final ObjectMapper mapper = new ObjectMapper();
    
    private final String appVersion;
    private final String outputFolder;

    public LiteralExtractorConsumer(String appVersion, String outputFolder) {
        this.appVersion = appVersion;
        this.outputFolder = outputFolder;
    }
    
    @Override
    public void accept(Path globalMetadataPath) {
        StringLiteralExtractor literalExtractor = new StringLiteralExtractor(globalMetadataPath.toString());
        
        try {
            literalExtractor.extract();
            AppEntity appEntity = new AppEntity(literalExtractor.getVerCodeString(), appVersion);
            mapper.writeValue(new File(outputFolder), appEntity);
            
            LOGGER.info("Writed file on " + outputFolder + " completed!");
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
}
