package one.squeeze.pdftools.app.scale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.util.Matrix;

import java.io.IOException;

public class Scaler implements IScaler {

    private final float factor;

    private final PDRectangle targetMediaBox;

    public Scaler(float factor, PDRectangle targetMediaBox) {
        if (factor <= 0 || factor == 1) {
            throw new IllegalArgumentException("Invalid factor: " + factor);
        }
        if (targetMediaBox == null) {
            throw new IllegalArgumentException("Null targetMediaBox argument");
        }

        this.factor = factor;
        this.targetMediaBox = targetMediaBox;
    }

    @Override
    public void scale(PDDocument pdf, PDPage page) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(pdf, page, PDPageContentStream.AppendMode.PREPEND, false);
        contentStream.transform(Matrix.getScaleInstance(factor, factor));
        contentStream.close();

        page.setMediaBox(targetMediaBox);
    }

    public float getFactor() {
        return factor;
    }

    public PDRectangle getTargetMediaBox() {
        return targetMediaBox;
    }
}
