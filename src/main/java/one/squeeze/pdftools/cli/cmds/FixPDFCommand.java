package one.squeeze.pdftools.cli.cmds;


import one.squeeze.pdftools.DIN;
import one.squeeze.pdftools.app.scale.IScaler;
import one.squeeze.pdftools.app.scale.NoopScaler;
import one.squeeze.pdftools.app.scale.Scaler;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import picocli.CommandLine;

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

    public static final PDRectangle TARGET_MEDIA_BOX = DIN.A4;
    public static final PDRectangle TARGET_MEDIA_BOX_LANDSCAPE = DIN.A4_Landscape;
    public static final float MAX_WIDTH = TARGET_MEDIA_BOX.getWidth();
    public static final float MAX_HEIGHT = TARGET_MEDIA_BOX.getHeight();
    public static final float MAX_AREA = MAX_WIDTH * MAX_HEIGHT;

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
            PDPageTree tree = pdf.getPages();

            for (PDPage page : tree) {
                IScaler scaler = buildScaler(page);
                scaler.scale(pdf, page);
            }

            pdf.save(output);
        }
    }

    public static boolean shouldScale(PDPage page) {
        PDRectangle mediaBox = page.getMediaBox();

        // Pages may be landscape, portrait or other weired aspect rations.
        // The general concern for us is that the area should not be too large, no matter
        // what aspect ratio. Thus, we limit the area.

        float area = mediaBox.getWidth() * mediaBox.getHeight();
        return area > MAX_AREA;
    }

    public static IScaler buildScaler(PDPage page) {
        boolean shouldScale = shouldScale(page);
        if (!shouldScale) {
            return new NoopScaler();
        }

        PDRectangle mediaBox = page.getMediaBox();
        boolean isPortrait = mediaBox.getWidth() < mediaBox.getHeight();

        // Calculate scale factors. Depending on the orientation this requires division of height or width.
        float fWidth = 1;
        float fHeight = 1;
        if (isPortrait) {
            fWidth = MAX_WIDTH / mediaBox.getWidth();
            fHeight = MAX_HEIGHT / mediaBox.getHeight();
        } else {
            fWidth = MAX_HEIGHT / mediaBox.getWidth();
            fHeight = MAX_WIDTH / mediaBox.getHeight();
        }

        float factor = Math.min(fWidth, fHeight);

        // Determine new media box
        PDRectangle targetMediaBox;
        if (isPortrait) {
            targetMediaBox = TARGET_MEDIA_BOX;
        } else {
            targetMediaBox = TARGET_MEDIA_BOX_LANDSCAPE;
        }

        return new Scaler(factor, targetMediaBox);
    }
}
