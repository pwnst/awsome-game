package v02.engine;

import lombok.Data;
import v02.engine.gfx.Font;
import v02.engine.gfx.Image;

import java.awt.image.DataBufferInt;
import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Data
public class Renderer {

    private Font font = Font.FONT;
    private int pixelWidth;
    private int pixelHeight;
    private int[] pixels;

    public Renderer(GameContainer gc) {
        this.pixelWidth = gc.getWidth();
        this.pixelHeight = gc.getHeight();
        pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

    public void setPixel(int x, int y, int value) {
        if (((value >> 24) & 0xff) != 0) {
            int index = y * pixelWidth + x;
            pixels[index] = value;
        }
    }

    public void drawImage(Image image, int offX, int offY) {
        drawImage(image, offX, offY, 0);
    }

    public void drawImage(Image image, int offX, int offY, int frame) {
        int imageWidth = image.getWidth() / image.getFrames();
        int imageHeight = image.getHeight();

        int xStart = max(offX, 0);
        int xEnd = min(imageWidth + offX, pixelWidth);
        int yStart = max(offY, 0);
        int yEnd = min(imageHeight + offY, pixelHeight);

        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                int imagePixelIndex = (y - offY) * image.getWidth() + (x - offX + imageWidth * frame);
                int currentPixelValue = image.getPixels()[imagePixelIndex];
                setPixel(x, y, currentPixelValue);
            }
        }
    }

    public void fillRect(int offX, int offY, int width, int height, int color) {
        int xStart = max(offX, 0);
        int xEnd = min(width + offX, pixelWidth);
        int yStart = max(offY, 0);
        int yEnd = min(height + offY, pixelHeight);

        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd;  y++) {
                setPixel(x, y, color);
            }
        }
    }
}
