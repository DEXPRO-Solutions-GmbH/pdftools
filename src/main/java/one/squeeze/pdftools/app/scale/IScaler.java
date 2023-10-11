package one.squeeze.pdftools.app.scale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;

public interface IScaler {

    public void scale(PDDocument pdf, PDPage page) throws IOException;

}
