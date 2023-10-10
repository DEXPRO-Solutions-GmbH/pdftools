package one.squeeze.pdftools.cli;

import one.squeeze.pdftools.cli.cmds.MainCommand;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int code = new CommandLine(new MainCommand()).execute(args);
        System.exit(code);
    }
}
