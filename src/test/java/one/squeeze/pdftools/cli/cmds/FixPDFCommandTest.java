package one.squeeze.pdftools.cli.cmds;

import one.squeeze.pdftools.app.scale.IScaler;
import one.squeeze.pdftools.app.scale.NoopScaler;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixPDFCommandTest {
    @Test
    void testBuildScaler_NoopOnA4Portrait() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(595);
        box.setUpperRightY(841);
        page.setMediaBox(box);

        IScaler scaler = FixPDFCommand.buildScaler(page);
        assertTrue(scaler instanceof NoopScaler);
    }

    @Test
    void testBuildScaler_NoopOnA4Landscape() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(841);
        box.setUpperRightY(595);
        page.setMediaBox(box);

        IScaler scaler = FixPDFCommand.buildScaler(page);
        assertTrue(scaler instanceof NoopScaler);
    }
}
