package one.squeeze.pdftools.cli.cmds;


import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.util.Matrix;
import picocli.CommandLine;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * This class implements scaling down PDF pages which are too large.
 * The main idea comes from this <a href="https://stackoverflow.com/a/64935769/3647782">stack overflow answer</a>.
 */
@CommandLine.Command(name = "fix", mixinStandardHelpOptions = true,
        description = "Scales large pages scaled down to A4.")
public class FixPDFCommand implements Callable<Integer> {

    public static final int MAX_WIDTH = 595;
    public static final int MAX_HEIGHT = 841;

    @CommandLine.Parameters(index = "0", description = "The input PDF to fix")
    private File inputFile;

    @CommandLine.Parameters(index = "1", description = "Path to the output PDF")
    private File outputFile;

    @Override
    public Integer call() throws Exception {
        if (!inputFile.exists()) {
            throw new FileNotFoundException("Input file does not exist");
        }

        try {
            fix(inputFile, outputFile);
        } catch (Throwable e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    private static void fix(File input, File output) throws IOException {
        try (PDDocument pdf = Loader.loadPDF(input)) {
            PrinterJob job = PrinterJob.getPrinterJob();
            PDPageTree tree = pdf.getPages();

            for (PDPage page : tree) {
                if (page.getMediaBox().getWidth() > MAX_WIDTH || page.getMediaBox().getHeight() > MAX_HEIGHT) {
                    float fWidth = MAX_WIDTH / page.getMediaBox().getWidth();
                    float fHeight = MAX_HEIGHT / page.getMediaBox().getHeight();

                    float factor = Math.min(fWidth, fHeight);

                    PDPageContentStream contentStream = new PDPageContentStream(pdf, page, PDPageContentStream.AppendMode.PREPEND, false);
                    contentStream.transform(Matrix.getScaleInstance(factor, factor));
                    contentStream.close();

                    page.setMediaBox(PDRectangle.A4);
                }
            }

            pdf.save(output);
        }
    }
}
