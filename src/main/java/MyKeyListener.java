import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MyKeyListener implements KeyListener {

    private Obj player;

   public MyKeyListener()  {}


    public MyKeyListener(Obj player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        System.out.println(keyEvent.getKeyCode());
       if (keyEvent.getKeyCode() == 68) {
           player.setVx(1);
       }
        if (keyEvent.getKeyCode() == 65) {
            player.setVx(-1);
        }
        if (keyEvent.getKeyCode() == 87) {
            player.setVy(-5);
        }
//        83
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 68) {
            player.setVx(0);
        }
        if (keyEvent.getKeyCode() == 65) {
            player.setVx(0);
        }
        if (keyEvent.getKeyCode() == 87) {
            player.setVy(0);
        }

    }

    public Obj getPlayer() {
        return player;
    }

    public void setPlayer(Obj player) {
        this.player = player;
    }
}
