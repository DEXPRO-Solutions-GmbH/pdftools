package one.squeeze.pdftools;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * This class holds constants for DIN paper sizes.
 */
abstract public class DIN {

    public static final PDRectangle A4 = PDRectangle.A4;
    public static final PDRectangle A4_Landscape = new PDRectangle(A4.getHeight(), A4.getWidth());

}
