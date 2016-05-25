package tankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class BreakableWall extends BackgoundObject {
    int timer = 500;
    public BreakableWall(BufferedImage img, int x, int y) {
        super(img, x, y);
        setPassby(false);
        visible = true;
    }

    public void draw(Graphics g, ImageObserver obs) {
        if (!visible) {
            this.timer--;
            if (this.timer < 0) {
                this.timer = 500;
                this.visible = true;
            }
        } else {
            super.draw(g, obs);
        }
    }
}
