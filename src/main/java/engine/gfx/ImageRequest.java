package engine.gfx;

import lombok.Data;

@Data
public class ImageRequest {

    private Image image;
    private int zDepth;
    private int offX;
    private int offY;

    public ImageRequest(Image image, int zDepth, int offX, int offY) {
        this.image = image;
        this.zDepth = zDepth;
        this.offX = offX;
        this.offY = offY;
    }

}
