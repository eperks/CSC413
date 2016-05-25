package lazarus;

import java.awt.*;
import static java.lang.Thread.sleep;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import wingman.GameSounds;
import wingman.game.PlayerShip;
import wingman.modifiers.motions.InputController;

public class Lazarus
        extends PlayerShip {

    int oldRight = 0, oldLeft = 0;

    public Lazarus(Point location, Image img, int controls[], String name) {
        super(location, new Point(0, 0), img, controls, name);
        height = 40;
        width = 40;
        this.name = name;
        motion = new InputController(this, controls, LazarusWorld.getLazarus());
        this.location = new Rectangle(location.x, location.y, width, height);
    }

    private boolean isPlayerStopCollision() {
        for (ListIterator stopButtons = LazarusWorld.getLazarus().stopButtons.listIterator(); stopButtons.hasNext();) {
            StopButton newStopButton = (StopButton) stopButtons.next();
            if (newStopButton.collision(this)) {
                GameSounds.play("Resources/Button.wav");
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerWallCollision() {
        for (ListIterator wallList = LazarusWorld.getLazarus().getWallItems(); wallList.hasNext();) {
            Box newWallBox = (Box) wallList.next();
            if (newWallBox.collision(this)) {
                if (right == 1) {
                    if (location.x + 40 >= newWallBox.getX()) {
                        return true;
                    }
                } else if (location.x - 40 <= newWallBox.getX()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPlayerBoxCollision() {
        int col = location.x / 40;
        for (ListIterator boxlist = LazarusWorld.getLazarus().findBoxAtY(col); boxlist.hasNext();) {
            Box fallingBox = (Box) boxlist.next();
            if (fallingBox.collision(this)) {
                if (LazarusWorld.getLazarus().numberOfFallenBoxes(col, fallingBox.getY()) > 0) {
                    return true;
                } else {
                    location.y -= 40;
                    return false;
                }
            }
        }
        if (location.y < 360) {
            location.y += 40;
            return isPlayerBoxCollision();
        } else {
            return false;
        }
    }

    @Override
    public void update(int w, int h) {
        if (oldRight != right || oldLeft != left) {
            oldRight = right;
            oldLeft = left;
            if (right == 1 || left == 1) {
                location.x += (right - left) * 40;

                if (location.y > 220) {
                    if (isPlayerBoxCollision() || isPlayerWallCollision()) {
                        GameSounds.play("Resources/Wall.wav");
                        location.x -= (right - left) * 40;
                    } else {
                        GameSounds.play("Resources/Move.wav");
                    }
                }else {
                    location.y = 160;
                    GameSounds.play("Resources/Move.wav");
                    if (isPlayerStopCollision()) {
                        LazarusWorld.gameOver = true;
                        LazarusWorld.winner = true;
                    }

                }
            }
        }
    }

    @Override
    public void die() {
        LazarusWorld.explosions.add(new Movement(location.x,location.y,LazarusWorld.squished));
        LazarusWorld.gameOver = true; 
    }
}
