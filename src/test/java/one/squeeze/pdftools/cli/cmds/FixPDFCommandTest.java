package one.squeeze.pdftools.cli.cmds;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixPDFCommandTest {
    @Test
    void testShouldPageBeRescaled_FalseOnA4Portrait() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(595);
        box.setUpperRightY(841);
        page.setMediaBox(box);

        assertFalse(FixPDFCommand.shouldPageBeRescaled(page));
    }

    @Test
    void testShouldPageBeRescaled_FalseOnA4Landscape() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(841);
        box.setUpperRightY(595);
        page.setMediaBox(box);

        assertFalse(FixPDFCommand.shouldPageBeRescaled(page));
    }
}
