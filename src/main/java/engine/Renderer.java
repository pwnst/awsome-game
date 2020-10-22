package engine;

import engine.gfx.Font;
import engine.gfx.Image;
import engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int pixelWidth;

    private int pixelHieght;

    private int[] pixels;

    private int[] zBuffer;

    private int zDepth = 0;

    private Font font = Font.FONT;

    public Renderer(GameContainer gc) {
        this.pixelWidth = gc.getWidth();
        this.pixelHieght = gc.getHeight();
        pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zBuffer = new int[pixels.length];
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
            zBuffer[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value) {
        int alpha = (value >> 24) & 0xff;
        if (x < 0 || x >= pixelWidth || y < 0 || y >= pixelHieght || alpha == 0) {
            return;
        }

        if (zBuffer[y * pixelWidth + x] > zDepth) {
            return;
        }
        if (alpha == 255) {
            pixels[y * pixelWidth + x] = value;
        } else {
            int pixelColor = pixels[y * pixelWidth + x];

            int newRed = (pixelColor >> 16) & 0xff - (int) (((pixelColor >> 16) & 0xff - (value >> 16) & 0xff) * (alpha / 255f));
            int newGreen = (pixelColor >> 8) & 0xff - (int) (((pixelColor >> 8) & 0xff - (value >> 8) & 0xff) * (alpha / 255f));
            int newBlue = (pixelColor & 0xff) - (int) (((pixelColor & 0xff) - (value & 0xff) * (alpha / 255f)));

            pixels[y * pixelWidth + x] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
        }

    }
    public void drawText(String text, int offX, int offY, int color) {
        Image fontImage = font.getFontImage();
        text = text.toUpperCase();
        int offset = 0;

        for (int i = 0; i < text.length(); i++) {
            int unicodeIdx = text.codePointAt(i) - 32;
            for (int y = 0; y < fontImage.getHeight(); y++) {
                for (int x = 0; x < font.getWidths()[unicodeIdx]; x++) {
                    if (fontImage.getPixels()[(x + font.getOffsets()[unicodeIdx]) + y * fontImage.getWidth()] == 0xffffffff) {
                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }
            offset += font.getWidths()[unicodeIdx];
        }
    }

    public void drawImage(Image image, int offX, int offY) {

        // don't render
        if (offX < -image.getWidth()) {
            return;
        }

        if (offX >= pixelWidth) {
            return;
        }

        if (offY < -image.getHeight()) {
            return;
        }

        if (offY >= pixelHieght) {
            return;
        }

        int newX = 0;
        int newY = 0;
        int newImageWidth = image.getWidth();
        int newImageHeight = image.getHeight();


        // clipping
        if (offY < 0) {
            newY -= offY;
        }

        if (offX < 0) {
            newX -= offX;
        }


        if (newImageWidth + offX >= pixelWidth) {
            newImageWidth = newImageWidth - (newImageWidth + offX - pixelWidth);
        }

        if (newImageHeight + offY >= pixelHieght) {
            newImageHeight = newImageHeight - (newImageHeight + offY - pixelHieght);
        }

        for (int x = newX; x < newImageWidth; x++) {
            for (int y = newY; y < newImageHeight; y++) {
                setPixel(x + offX, y + offY, image.getPixels()[y * image.getWidth() + x]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {

        // don't render
        if (offX < -image.getTileWidth()) {
            return;
        }

        if (offX >= pixelWidth) {
            return;
        }

        if (offY < -image.getTileHeight()) {
            return;
        }

        if (offY >= pixelHieght) {
            return;
        }

        int newX = 0;
        int newY = 0;
        int newImageWidth = image.getTileWidth();
        int newImageHeight = image.getTileHeight();


        // clipping
        if (offY < 0) {
            newY -= offY;
        }

        if (offX < 0) {
            newX -= offX;
        }


        if (newImageWidth + offX >= pixelWidth) {
            newImageWidth = newImageWidth - (newImageWidth + offX - pixelWidth);
        }

        if (newImageHeight + offY >= pixelHieght) {
            newImageHeight = newImageHeight - (newImageHeight + offY - pixelHieght);
        }

        for (int x = newX; x < newImageWidth; x++) {
            for (int y = newY; y < newImageHeight; y++) {
                setPixel(
                        x + offX,
                        y + offY,
                        image.getPixels()[(y + tileY * image.getTileHeight()) * image.getWidth() + (x + tileX * image.getTileWidth())]
                );
            }
        }
    }

    public void drawRect(int offX, int offY, int width, int height, int color) {
        for (int x = 0; x <= width; x++) {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }

        for (int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }
    }

    public void drawFillRect(int offX, int offY, int width, int height, int color) {
         // don't render
        if (offX < -width) {
            return;
        }

        if (offX >= pixelWidth) {
            return;
        }

        if (offY < -height) {
            return;
        }

        if (offY >= pixelHieght) {
            return;
        }

        int newX = 0;
        int newY = 0;
        int newImageWidth = width;
        int newImageHeight = height;


        // clipping
        if (offY < 0) {
            newY -= offY;
        }

        if (offX < 0) {
            newX -= offX;
        }


        if (newImageWidth + offX >= pixelWidth) {
            newImageWidth = newImageWidth - (newImageWidth + offX - pixelWidth);
        }

        if (newImageHeight + offY >= pixelHieght) {
            newImageHeight = newImageHeight - (newImageHeight + offY - pixelHieght);
        }

        System.out.println("W " + newImageWidth);
        System.out.println("H " + newImageHeight);

        for (int x = newX; x <= newImageWidth; x++) {
            for (int y = newY; y <= newImageHeight; y++) {
                setPixel(x + offX, y + offY, color);
            }
        }
    }
}
