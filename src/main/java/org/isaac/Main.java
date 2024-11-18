package org.isaac;


import org.isaac.commands.CheckVerCode;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        CheckVerCode checkVerCode = new CheckVerCode();

        int exitCode = new CommandLine(checkVerCode).execute(args);

        if (exitCode == 1)
            CommandLine.usage(checkVerCode, System.out);

        System.exit(exitCode);
    }
}