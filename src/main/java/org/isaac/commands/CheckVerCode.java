package org.isaac.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.isaac.entities.AppEntity;
import org.isaac.utils.Comparator;
import org.isaac.utils.MyLogger;
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
    @Option(names = { "-s", "--stringliteral" }, description = "Source stringliteral old file.")
    String stringLiteralPath;

    @Option(names = { "-av", "--appversion" }, description = "What version in this verCode.")
    String appVer;

    @Option(names = { "-o", "--output"}, description = "Specify the output where is the file. (include name & ext)")
    String output;

    private static final MyLogger logger = new MyLogger(CheckVerCode.class);

    @Override
    public Integer call() throws Exception {
        if (stringLiteralPath == null || appVer == null || output == null) return 1;

        logger.info("Getting file from " + stringLiteralPath);
        File stringLiteralFile = new File(stringLiteralPath);
        ObjectMapper mapper = new ObjectMapper();

        logger.info("Reading and mapping the file...");
        JsonNode stringLiteralNode = mapper.readTree(stringLiteralFile);

        logger.info("Getting VerCode from map...");
        Comparator comparator = new Comparator(stringLiteralNode);
        AppEntity appEntity = new AppEntity(comparator.getVerCode(), appVer);

        logger.info("Writing verCode (" + appEntity.getVerCode() + ") of version (" + appEntity.getAppVer() + ")");
        mapper.writeValue(new File(output), appEntity);

        logger.info("Done!");
        return 0;
    }
}
