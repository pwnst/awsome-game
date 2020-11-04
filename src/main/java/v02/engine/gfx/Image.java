package v02.engine.gfx;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@NoArgsConstructor
@Data
public class Image {

    private int width;
    private int height;
    private int frames;
    private int[] pixels;

    @SneakyThrows
    public Image(String path) {
        BufferedImage image = ImageIO.read(Image.class.getResourceAsStream(path));
        width = image.getWidth();
        height = image.getHeight();
        frames = 1;
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
        image.flush();
    }

    public Image(String path, int frames) {
        this(path);
        this.frames = frames;
    }

    public boolean isAnimated() {
        return frames > 1;
    }
}
