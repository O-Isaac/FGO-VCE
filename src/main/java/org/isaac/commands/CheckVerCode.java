package org.isaac.commands;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.isaac.entities.AppEntity;
import org.isaac.utils.MyLogger;
import org.isaac.utils.StringLiteralExtractor;

import org.isaac.utils.ZipExtractor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import org.isaac.consumers.GlobalMetadataConsumer;

@Command(
        name = "fgo-vce",
        mixinStandardHelpOptions = true,
        version = "fgo vce 1.0.0",
        description = "Compare two stringliteral.json and return updated verCode"
)
public class CheckVerCode implements Callable<Integer> {
    @Option(names = { "-xa", "--apk"}, description = "Path for xapk.")
    String xapkPath;

    @Option(names = { "-av", "--appversion" }, description = "What version in this verCode.")
    String appVer;

    @Option(names = { "-o", "--output"}, description = "Specify the output where is the file. (include name & ext)")
    String output;

    private static final MyLogger LOGGER = new MyLogger(CheckVerCode.class);
    
    private List<String> apks = Arrays.asList("com.aniplex.fategrandorder.apk", "com.aniplex.fategrandorder.en.apk");
    
    @Override
    public Integer call() throws Exception {
        if (xapkPath == null || appVer == null || output == null) return 1;

        LOGGER.info("Getting global-metadata.dat from apk " + xapkPath);

        ZipExtractor xapkExtractor = new ZipExtractor(xapkPath);
        
        Optional<Path> apkPath = xapkExtractor.extract(apks, "");

        apkPath.ifPresentOrElse(new GlobalMetadataConsumer(appVer, output), () -> {
            LOGGER.info("Apk not found!");
        });
        
        return 0;
    }
    
    
}
