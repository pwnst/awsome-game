package gfx;

import lombok.Data;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Data
public class Image {

    private int width;

    private int height;

    private int[] pixels;

    @SneakyThrows
    public Image(String path) {
        BufferedImage image = ImageIO.read(Image.class.getResourceAsStream(path));

        width = image.getWidth();
        height = image.getHeight();
        pixels = image.getRGB(0, 0, width, height, null, 0, width);

        image.flush();
    }
}
