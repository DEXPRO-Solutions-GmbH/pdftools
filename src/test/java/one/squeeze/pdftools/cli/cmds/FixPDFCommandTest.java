package one.squeeze.pdftools.cli.cmds;

import one.squeeze.pdftools.DIN;
import one.squeeze.pdftools.app.scale.Scaler;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FixPDFCommandTest {


    /**
     * This constant is used to compare the expected output of factor calculation against 1.
     * This is a nasty workarround, it would be better to improve the assertions using this constant.
     * Either way, this currently works fine.
     */
    public static final double TEN_PERCENT = 0.10000000149011612;

    @Test
    void testBuildScaler_NoopOnA4Portrait() {
        PDPage page = new PDPage();
        page.setMediaBox(DIN.A4);

        assertFalse(FixPDFCommand.shouldScale(page));
    }

    @Test
    void testBuildScaler_NoopOnA4Landscape() {
        PDPage page = new PDPage();
        page.setMediaBox(DIN.A4_Landscape);

        assertFalse(FixPDFCommand.shouldScale(page));
    }

    @Test
    void testBuildScaler_NoopOnSmallerThanA4Portrait() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(DIN.A4.getWidth() - 1);
        box.setUpperRightY(DIN.A4.getHeight() - 1);
        page.setMediaBox(box);

        assertFalse(FixPDFCommand.shouldScale(page));
    }

    @Test
    void testBuildScaler_NoopOnSmallerThanA4Landscape() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(DIN.A4_Landscape.getWidth() - 1);
        box.setUpperRightY(DIN.A4_Landscape.getHeight() - 1);
        page.setMediaBox(box);

        assertFalse(FixPDFCommand.shouldScale(page));
    }

    @Test
    void testBuildScaler_ScalesLargePortrait() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(DIN.A4.getWidth() * 10);
        box.setUpperRightY(DIN.A4.getHeight() * 10);
        page.setMediaBox(box);

        Scaler scaler = (Scaler) FixPDFCommand.buildScaler(page);
        assertEquals(TEN_PERCENT, scaler.getFactor());
        assertEquals(DIN.A4, scaler.getTargetMediaBox());
    }

    @Test
    void testBuildScaler_ScalesLargeLandscape() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(DIN.A4_Landscape.getWidth() * 10);
        box.setUpperRightY(DIN.A4_Landscape.getHeight() * 10);
        page.setMediaBox(box);

        Scaler scaler = (Scaler) FixPDFCommand.buildScaler(page);
        assertEquals(TEN_PERCENT, scaler.getFactor());
        assertEquals(DIN.A4_Landscape, scaler.getTargetMediaBox());
    }
}
