package one.squeeze.pdftools.cli.cmds;

import one.squeeze.pdftools.DIN;
import one.squeeze.pdftools.app.scale.IScaler;
import one.squeeze.pdftools.app.scale.NoopScaler;
import one.squeeze.pdftools.app.scale.Scaler;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testBuildScaler_NoopOnSmallerThanA4Portrait() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX((float) 595 / 2);
        box.setUpperRightY((float) 841 / 2);
        page.setMediaBox(box);

        IScaler scaler = FixPDFCommand.buildScaler(page);
        assertTrue(scaler instanceof NoopScaler);
    }

    @Test
    void testBuildScaler_NoopOnSmallerThanA4Landscape() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX((float) 841 / 2);
        box.setUpperRightY((float) 595 / 2);
        page.setMediaBox(box);

        IScaler scaler = FixPDFCommand.buildScaler(page);
        assertTrue(scaler instanceof NoopScaler);
    }

    @Test
    void testBuildScaler_ScalesLargePortrait() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(595 * 10);
        box.setUpperRightY(841 * 10);
        page.setMediaBox(box);

        Scaler scaler = (Scaler) FixPDFCommand.buildScaler(page);
        assertEquals(TEN_PERCENT, scaler.getFactor());
        assertEquals(DIN.A4, scaler.getTargetMediaBox());
    }

    @Test
    void testBuildScaler_ScalesLargeLandscape() {
        PDPage page = new PDPage();
        PDRectangle box = new PDRectangle();
        box.setUpperRightX(841 * 10);
        box.setUpperRightY(595 * 10);
        page.setMediaBox(box);

        Scaler scaler = (Scaler) FixPDFCommand.buildScaler(page);
        assertEquals(TEN_PERCENT, scaler.getFactor());
        assertEquals(DIN.A4_Landscape, scaler.getTargetMediaBox());
    }
}
