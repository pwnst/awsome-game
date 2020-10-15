package engine;

import gfx.Image;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int pixelWidth;

    private int pixelHieght;

    private int[] pixels;

    public Renderer(GameContainer gc) {
        this.pixelWidth = gc.getWidth();
        this.pixelHieght = gc.getHeight();
        pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value) {
        if (x < 0 || x >= pixelWidth || y < 0 || y >= pixelHieght || value == 0xffff00ff) {
            return;
        }

//        System.out.println("X is " + x);

        pixels[y * pixelWidth + x] = value;
    }

    public void drawImage(Image image, int offX, int offY) {
        int newX = 0;
        int newY = 0;
        int newImageWidth = image.getWidth();
        int newImageHeight = image.getHeight();

        if (image.getWidth() + offX > pixelWidth) {
            newImageWidth = newImageWidth - (newImageWidth + offX - pixelWidth);
        }

        if (image.getHeight() + offY > pixelHieght) {
            newImageHeight = newImageHeight - (newImageHeight + offY - pixelHieght);
        }

        if (offX < 0) {
            newX -= offX;
        }

        if (offY < 0) {
            newY -= offY;
        }

        for (int x = newX; x < newImageWidth; x++) {
            for (int y = newY; y < newImageHeight; y++) {
                setPixel(x + offX, y + offY, image.getPixels()[y * image.getWidth() + x]);
            }
        }
    }
}
