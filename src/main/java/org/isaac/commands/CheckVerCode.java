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
import java.util.concurrent.Callable;

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

    @Override
    public Integer call() throws Exception {
        if (xapkPath == null || appVer == null || output == null) return 1;

        ObjectMapper mapper = new ObjectMapper();

        LOGGER.info("Getting global-metadata.dat from apk " + xapkPath);
        ZipExtractor xapkExtractor = new ZipExtractor(xapkPath);
        Path apkPath = xapkExtractor.extract(
            "com.aniplex.fategrandorder.apk",
            ""
        );

        if (apkPath == null) {
          //TODO:Make zipExtractor múltiple apk
          apkPath = xapkExtractor.extract(
           "com.aniplex.fategrandorder.en.apk",
           ""
          );
        }

        ZipExtractor globalMetadataExtrator = new ZipExtractor(apkPath.toString());
        Path globalMetadaPath = globalMetadataExtrator.extract(
            "assets/bin/Data/Managed/Metadata/global-metadata.dat",
            ""
        );

        StringLiteralExtractor literalExtractor = new StringLiteralExtractor(globalMetadaPath.toString()).extract();
        AppEntity appEntity = new AppEntity(literalExtractor.getVerCodeString(), appVer);

        mapper.writeValue(new File(output), appEntity);
        LOGGER.info("Writed file on " + output + " completed!");
        return 0;
    }
}
