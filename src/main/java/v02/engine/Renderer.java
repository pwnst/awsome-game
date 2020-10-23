package v02.engine;

import engine.gfx.Font;
import engine.gfx.Image;
import lombok.Data;

import java.awt.image.DataBufferInt;
import java.util.Arrays;

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

    public void setPixel(int index, int value) {
        if (((value >> 24) & 0xff) != 0) {
            pixels[index] = value;
        }
    }

    public void drawImage(Image image, int offX, int offY) {
        for (int x = offX; x < Math.min(image.getWidth() + offX, pixelWidth); x++) {
            for (int y = offY; y < Math.min(image.getHeight() + offY, pixelHeight); y++) {
                int windowPixelIndex = y * pixelWidth + x;
                int imagePixelIndex = (y - offY) * image.getWidth() + (x - offX);
                int currentPixelValue = image.getPixels()[imagePixelIndex];
                setPixel(windowPixelIndex, currentPixelValue);
            }
        }
    }

//    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
//
//        // don't render
//        if (offX < -image.getTileWidth()) {
//            return;
//        }
//
//        if (offX >= pixelWidth) {
//            return;
//        }
//
//        if (offY < -image.getTileHeight()) {
//            return;
//        }
//
//        if (offY >= pixelHeight) {
//            return;
//        }
//
//        int newX = 0;
//        int newY = 0;
//        int newImageWidth = image.getTileWidth();
//        int newImageHeight = image.getTileHeight();
//
//
//        // clipping
//        if (offY < 0) {
//            newY -= offY;
//        }
//
//        if (offX < 0) {
//            newX -= offX;
//        }
//
//
//        if (newImageWidth + offX >= pixelWidth) {
//            newImageWidth = newImageWidth - (newImageWidth + offX - pixelWidth);
//        }
//
//        if (newImageHeight + offY >= pixelHeight) {
//            newImageHeight = newImageHeight - (newImageHeight + offY - pixelHeight);
//        }
//
//        for (int x = newX; x < newImageWidth; x++) {
//            for (int y = newY; y < newImageHeight; y++) {
//                setPixel(
//                        x + offX,
//                        y + offY,
//                        image.getPixels()[(y + tileY * image.getTileHeight()) * image.getWidth() + (x + tileX * image.getTileWidth())]
//                );
//            }
//        }
//    }
}
