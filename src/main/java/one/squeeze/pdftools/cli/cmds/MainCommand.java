package one.squeeze.pdftools.cli.cmds;

import picocli.CommandLine;

@CommandLine.Command(name = "pdftools", subcommands = {
        FixPDFCommand.class,
})
public class MainCommand {
}
