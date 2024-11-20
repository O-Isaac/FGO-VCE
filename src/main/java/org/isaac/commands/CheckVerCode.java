package org.isaac.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.isaac.entities.AppEntity;
import org.isaac.utils.MyLogger;
import org.isaac.utils.StringLiteralExtractor;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;

@Command(
        name = "fgo-vce",
        mixinStandardHelpOptions = true,
        version = "fgo vce 1.0.0",
        description = "Compare two stringliteral.json and return updated verCode"
)
public class CheckVerCode implements Callable<Integer> {
    @Option(names = { "-g", "--global-metadata" }, description = "Path for file global-metadata.dat")
    String globalMetadataPath;

    @Option(names = { "-av", "--appversion" }, description = "What version in this verCode.")
    String appVer;

    @Option(names = { "-o", "--output"}, description = "Specify the output where is the file. (include name & ext)")
    String output;

    private static final MyLogger logger = new MyLogger(CheckVerCode.class);

    @Override
    public Integer call() throws Exception {
        if (globalMetadataPath == null || appVer == null || output == null) return 1;

        ObjectMapper mapper = new ObjectMapper();

        logger.info("Getting global-metadata.dat " + globalMetadataPath);
        StringLiteralExtractor literalExtractor = new StringLiteralExtractor(globalMetadataPath).extract();
        
        logger.info("Getting VerCode...");
        AppEntity appEntity = new AppEntity(literalExtractor.getVerCodeString(), appVer);

        logger.info("Writing verCode (" + appEntity.getVerCode() + ") of version (" + appEntity.getAppVer() + ")");
        mapper.writeValue(new File(output), appEntity);

        logger.info("Done!");
        return 0;
    }
}
