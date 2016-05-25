package lazarus;

import java.awt.Image;
import java.awt.Point;
import wingman.game.BackgroundObject;

/**
 *
 * @author noslide
 */
public class StopButton extends BackgroundObject {

    static int buttom1, buttom2;

    /**
     *
     * @param x
     * @param y
     */
    public StopButton(int x, int y) {
        super(new Point(x * 40, y * 40), new Point(0, 0), (Image) LazarusWorld.sprites.get("StopButton"));
            buttom1 = x * 40;
            buttom2 = x * 40;
    }
}
