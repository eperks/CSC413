package lazarus;

import java.awt.Image;
import java.awt.Point;
import wingman.game.BackgroundObject;
import wingman.game.GameObject;

/**
 *
 * @author noslide
 */
public class LazarusBackground extends BackgroundObject {

    int w, h;

    /**
     *
     * @param w
     * @param h
     * @param speed
     * @param img
     */
    public LazarusBackground(int w, int h, Point speed, Image img) {
        super(new Point(0, 0), speed, img);
        this.w = w;
        this.h = h;
        setImage(img);
        this.img = img;
    }

    /**
     *
     * @param otherObject
     * @return
     */
    public boolean collision(GameObject otherObject) {
        return false;
    }

    /**
     *
     * @param w
     * @param h
     */
    public void update(int w, int h) {
    }
}
