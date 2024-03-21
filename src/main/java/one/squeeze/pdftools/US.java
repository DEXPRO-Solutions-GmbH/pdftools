package one.squeeze.pdftools;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * This class holds constants for US paper sizes.
 */
abstract public class US {

    public static final PDRectangle LETTER = PDRectangle.LETTER;
    public static final PDRectangle LEGAL = PDRectangle.LEGAL;
    public static final PDRectangle TABLOID = PDRectangle.TABLOID;

    public static final PDRectangle LETTER_LANDSACPE = new PDRectangle(LETTER.getHeight(), LETTER.getWidth());
    public static final PDRectangle LEGAL_LANDSACPE = new PDRectangle(LEGAL.getHeight(), LEGAL.getWidth());
    public static final PDRectangle TABLOID_LANDSACPE = new PDRectangle(TABLOID.getHeight(), TABLOID.getWidth());

}
