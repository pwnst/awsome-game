package v02.engine.gfx;

import lombok.Data;

@Data
public class Font {

    public static final Font FONT = new Font("/fonts.png");

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path) {
        fontImage = new Image(path);
        offsets = new int[59];
        widths = new int[59];

        int unicodeIdx = 0;

        for (int i = 0; i < fontImage.getWidth(); i++) {
            int currentPixel = fontImage.getPixels()[i];

            if (currentPixel == 0xff0000ff) {
                offsets[unicodeIdx] = i;
            }

            if (currentPixel == 0xffffff00) {
                widths[unicodeIdx] = i - offsets[unicodeIdx];
                unicodeIdx++;
            }
        }
    }
}
