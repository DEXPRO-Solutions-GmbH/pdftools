package one.squeeze.pdftools;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * This class holds constants for DIN paper sizes.
 */
abstract public class DIN {

    public static final PDRectangle A6 = PDRectangle.A6;
    public static final PDRectangle A5 = PDRectangle.A5;
    public static final PDRectangle A4 = PDRectangle.A4;
    public static final PDRectangle A3 = PDRectangle.A3;
    public static final PDRectangle A2 = PDRectangle.A2;

    public static final PDRectangle A6_Landscape = new PDRectangle(A6.getHeight(), A6.getWidth());
    public static final PDRectangle A5_Landscape = new PDRectangle(A5.getHeight(), A5.getWidth());
    public static final PDRectangle A4_Landscape = new PDRectangle(A4.getHeight(), A4.getWidth());
    public static final PDRectangle A3_Landscape = new PDRectangle(A3.getHeight(), A3.getWidth());
    public static final PDRectangle A2_Landscape = new PDRectangle(A2.getHeight(), A2.getWidth());

}
