package org.isaac;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

@Command(
        name = "fgo-vce",
        mixinStandardHelpOptions = true,
        version = "fgo vce 1.0.0",
        description = "Compare two stringliteral.json and return updated verCode"
)
public class CheckVerCode implements Callable<Integer> {
    @Option(names = { "-s", "--stringliteral" }, description = "Source stringliteral old file.")
    String stringLiteralPath;

    private static MyLogger logger = new MyLogger(CheckVerCode.class);

    @Override
    public Integer call() throws Exception {
        if (stringLiteralPath == null) return 1;

        logger.info("Getting file from " + stringLiteralPath);
        File stringLiteralFile = new File(stringLiteralPath);
        ObjectMapper mapper = new ObjectMapper();

        logger.info("Reading file and mapping the file...");
        JsonNode stringLiteralNode = mapper.readTree(stringLiteralFile);

        logger.info("Getting VerCode from map...");
        Comparator comparator = new Comparator(stringLiteralNode);
        String verCode = comparator.getVerCode();

        logger.info("Writing ");

        return 0;
    }
}
