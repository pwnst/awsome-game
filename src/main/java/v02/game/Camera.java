package v02.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import v02.engine.GameContainer;
import v02.engine.Renderer;

@Data
@AllArgsConstructor
public class Camera {
    private int x;
    private int y;
    private int xV;
    private int yV;
    private GameMap gameMap;
    private GameContainer gameContainer;

    public void display() {
        gameMap.draw(x, y);
    }

    public int getMapXCoord(int cursorX) {
        return x + cursorX;
    }

    public int getMapYCoord(int cursorY) {
        return y + cursorY;
    }

    public void update() {
        int centerX = gameContainer.getWidth() / 2;
        int centerY = gameContainer.getHeight() / 2;

        int mouseX = gameContainer.getInput().getMouseX();
        int mouseY = gameContainer.getInput().getMouseY();

        if (mouseX > centerX + 120) {
            xV = 4;
        } else if (mouseX < centerX - 120) {
            xV = -4;
        } else {
            xV = 0;
        }

        if (mouseY > centerY + 90) {
            yV = 4;
        } else if (mouseY < centerY - 90) {
            yV = -4;
        } else {
            yV = 0;
        }

        if (x + xV > 0 && x + xV < gameMap.getTileSize() * gameMap.getMap().length - gameContainer.getWidth()) {
            x += xV;
        }

        if (y + yV > 0 && y + yV < gameMap.getTileSize() * gameMap.getMap()[0].length - gameContainer.getHeight()) {
            y += yV;
        }

        gameMap.update();
    }


}
