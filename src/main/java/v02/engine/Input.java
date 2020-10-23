package v02.engine;

import lombok.Getter;

import java.awt.event.*;

@Getter
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private GameContainer gc;

    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] lastKeys = new boolean[NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] lastButtons = new boolean[NUM_BUTTONS];

    private int mouseX;
    private int mouseY;

    private int scroll;

    public Input(GameContainer gameContainer) {
        this.gc = gameContainer;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        this.gc.getWindow().getCanvas().addKeyListener(this);
        this.gc.getWindow().getCanvas().addMouseListener(this);
        this.gc.getWindow().getCanvas().addMouseMotionListener(this);
        this.gc.getWindow().getCanvas().addMouseWheelListener(this);
    }

    public boolean isKey(int code) {
        return keys[code];
    }

    public boolean isKeyUp(int code) {
        return lastKeys[code] && !keys[code];
    }

    public boolean isKeyDown(int code) {
        return keys[code] && !lastKeys[code];
    }

    public boolean isButton(int code) {
        return buttons[code];
    }

    public boolean isButtonUp(int code) {
        return lastButtons[code] && !buttons[code];
    }

    public boolean isButtonDown(int code) {
        return buttons[code] && !lastButtons[code];
    }

    public void update() {
        scroll = 0;
        System.arraycopy(keys, 0, lastKeys, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, lastButtons, 0, NUM_BUTTONS);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        buttons[mouseEvent.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        buttons[mouseEvent.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX() / gc.getScale();
        mouseY = mouseEvent.getY() / gc.getScale();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX() / gc.getScale();
        mouseY = mouseEvent.getY() / gc.getScale();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        scroll = mouseWheelEvent.getWheelRotation();
    }
}
