package v02.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import v02.engine.GameContainer;
import v02.engine.Renderer;

@AllArgsConstructor
@Data
public class Game {

    private GameMap map;

    private Camera camera;

    private Renderer renderer;

    private GameContainer gameContainer;

}
